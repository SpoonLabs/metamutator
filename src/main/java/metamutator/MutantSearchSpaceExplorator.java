package metamutator;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import configuration.Config;

public class MutantSearchSpaceExplorator {
		
	static URL url;
	static URL[] urls;
	static ClassLoader cl;
	
	static int failures;
	static int successes;
	
	/**
	 * this function launch a parallel class
	 * @param classes
	 * @param core
	 * @return Core Result or null on blocking
	 */
	public static Result runWithThread(Class<?> classes, JUnitCore core) {
		int i = 0;
		// Define Runner
		RunnerThreaded runner = new RunnerThreaded(core,classes);
		
		// Launch Class during 8s max 
		try {
			runner.start();
			// check the results every 0,1s during 8s Max
			while(runner.getResult() == null && i < 80) {
				Thread.sleep(100);
				i++;
			}
			runner.interrupt();
		} catch (InterruptedException e) {
			
		}
		return runner.getResult();
	}
	
	public static void runMetaProgramIn(String target, String repertory) throws Exception {
		
		File filep = new File(target);
		File file = new File(target+"/"+repertory);
		
		// this function doesn't work for a file and for inexistant file
		if (!file.exists() || !filep.exists()) {
			throw new Exception("no such directory");
		}
		if (file.isFile() || filep.isFile())
			throw new Exception("not a directory");
		
		failures = 0;
		successes = 0;
		
		// make urls for classloader
		url = filep.toURI().toURL();
		urls = new URL[]{url};
		// create classloader
		cl = new URLClassLoader(urls);
		
		// finally run program
		runMetaProgramWith(file, getPackage(repertory));
		
		System.out.println("******************"+file.getName()+"******************");
		System.out.println("total killed "+failures);
		System.out.println("total alive "+successes);
	}
	
	public static void runMetaProgramWith(File target, String _package) throws Exception {
	
		
		// if the target is a file, so load the class and apply the initial function
		if (target.isFile()) {
			Class<?> clazz = cl.loadClass(_package+"."+target.getName().replace(".class", ""));
			
			int[] val = runMetaProgramWith(clazz);

			failures += val[0];
			successes += val[1];
			
		}
		// if the target is a directory, do stuff for each under file
		else if (target.isDirectory()) {
			for (File file : target.listFiles()) {
				if (!_package.isEmpty())
					runMetaProgramWith(file, _package+"."+target.getName());
				else
					runMetaProgramWith(file, target.getName());
			}
			
		}

	}

	public static int[] runMetaProgramWith(Class<?> TEST_CLASS) throws Exception {
		System.out.println("******************"+TEST_CLASS.getName()+"******************");
		boolean debug = false;

		JUnitCore core = new JUnitCore();

		//output folder
		File fail = new File("results/fail/"+TEST_CLASS.getName().replace(".", "/"));
		fail.mkdirs();
		File success = new File("results/success/"+TEST_CLASS.getName().replace(".", "/"));
		success.mkdirs();

		// we first run the test suite once to load all classes and their static
		// fields
		// this registers only the executed ifs, so indirectly
		// it collects a kind of trace
		Result result = core.run(TEST_CLASS);

		if (debug) { core.addListener(new TextListener(System.out)); }

		List<Selector> selectors = Selector.getAllSelectors();

		// if (selectors.isEmpty())
		// // There's no hot spot in program. Add one to run it at least once
		// selectors = ImmutableList.of(Selector.of(0, "n/a"));

		List<String> successes = Lists.newArrayList();
		List<String> failures = Lists.newArrayList();
		Multimap<Integer, String> failures2 = Multimaps.newListMultimap(
				Maps.newHashMap(), Lists::newArrayList);

		String[] strOptions = new String[selectors.size()];
		// Execute the test for each hot spot permutation
		// for (int[] options :
		// permutations(selectors.stream().map(Selector::getOptionCount).collect(Collectors.toList())))
		// {

		int nattempts=0;

		for (int sel = 0; sel < selectors.size(); sel++) {

			//int k=0;
			System.out.println(selectors.get(sel).getOptionCount());
			for (int k = 0; k < selectors.get(sel).getOptionCount(); k++) 
			{
				Config conf = Config.getInitInstance();


				int[] options = new int[selectors.size()];
				// System.out.println(Arrays.toString(options));
				for (int i = options.length - 1; i >= 0; i--) {
					selectors.get(i).choose(0);
					selectors.get(i).setStopTime(
							System.currentTimeMillis() + 300000);
					strOptions[i] = selectors.get(i)
							.getChosenOptionDescription();
					for(int o = 0; o < selectors.get(i).getOptionCount();o++ ){

						boolean value =(o == 0)?true:false;
						if(i == sel && o ==k){
							conf.write(selectors.get(sel).getLocationClass().getName()+":"+selectors.get(sel).getId()+":"+selectors.get(sel).getOption()[k]+":true");
						}else{
							if(i == sel)
								value = false;
						
							conf.write(selectors.get(i).getLocationClass().getName()+":"+selectors.get(i).getId()+":"+selectors.get(i).getOption()[o]+":"+value);
						}

					}
				}
				selectors.get(sel).choose(k);

				if (debug)
					System.out.println("Checking options: "
							+ Arrays.toString(options));

				result = runWithThread(TEST_CLASS,core);

				if (result.wasSuccessful()) {
					successes.add("   Worked !!!  -> "
							+ Arrays.toString(options) + " / "
							+ Arrays.toString(strOptions));

					// On essaye avec renameTo
					File dest = new File(success.getPath()+"/mutant"+selectors.get(sel).getId()+"_Op"+(k+1)+".txt");
					new File("config.txt").renameTo(dest);
				} else {
					String txt = String
							.format("%s / %s -> It has %s failures out of %s runs in %s ms",
									Arrays.toString(options),
									Arrays.toString(strOptions),
									result.getFailureCount(),
									result.getRunCount(), result.getRunTime());
					String txt_trace = String.format("%s",
							Arrays.toString(strOptions));

					failures.add(txt);
					failures2.put(result.getFailureCount(), txt);
					System.out.println(result.getFailures().get(0).getException());
					File dest = new File(fail.getPath()+"/mutant"+selectors.get(sel).getId()+"_Op"+(k+1)+".txt");
					new File("config.txt").renameTo(dest);
				}
			}

		}

		System.out.println("killed "+failures.size());
		System.out.println("alive "+successes.size());
		
		Selector.reset();
		
		int[] val = {failures.size(),successes.size()};
		
		return val;
	
	}

	/**
	 * Computes an iterable though all the permutations or the values in the
	 * ranges provided
	 * 
	 * @param sizes
	 *            the number of elements in each range (from 0 to size - 1)
	 * @return an Iterable
	 */
	private static Iterable<int[]> permutations(List<Integer> sizes) {
		int limits[] = new int[sizes.size()];

		int last = sizes.size() - 1;
		for (int i = last; i >= 0; i--)
			limits[i] = sizes.get(i) - 1;

		return () -> new Iterator<int[]>() {
			int current[] = new int[last + 1];

			{
				current[last]--; // Force the first element
			}

			@Override
			public boolean hasNext() {
				return !Arrays.equals(limits, current);
			}

			@Override
			public int[] next() {
				for (int i = last; i >= 0; i--) {
					if (current[i] < limits[i]) {
						current[i]++;

						return current.clone();
					}
					current[i] = 0;
				}

				return new int[0];
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub

			}
		};
	}
	
	
	public static String  getPackage(String path){
		
		int endIndex = path.lastIndexOf("/");
		if(endIndex == -1)
			return path;
			
		path = path.substring(0, endIndex);
		path = path.replace("/", ".");
		
		return path;
	}
}
