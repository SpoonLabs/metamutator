package metamutator;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import configuration.Config;


public class MutantReplay {
	
	static URL url;
	static URL[] urls;
	static ClassLoader cl;
	
	static int failures;
	static int successes;
	
	public static void replayMetaProgramIn(String target, String repertory) throws Exception {
		
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
		replayMetaProgramWith(file, getPackage(repertory));
		
		System.out.println("******************"+file.getName()+"******************");
		System.out.println("total killed "+failures);
		System.out.println("total alive "+successes);
	}
	
	public static void replayMetaProgramWith(File target, String _package) throws Exception {
		// if the target is a file, so load the class and apply the initial function
		if (target.isFile()) {
			Class<?> clazz = cl.loadClass(_package+"."+target.getName().replace(".class", ""));
			
			int[] val = replayMetaProgramWith(clazz);
			
			failures += val[0];
			successes += val[1];
						
		}
		// if the target is a directory, do stuff for each under file
		else if (target.isDirectory()) {
			for (File file : target.listFiles()) {
				if (!_package.isEmpty())
					replayMetaProgramWith(file, _package+"."+target.getName());
				else
					replayMetaProgramWith(file, target.getName());
			}
		}
				
	}

	public static int[] replayMetaProgramWith(Class<?> TEST_CLASS) throws Exception {
		System.out.println("******************"+TEST_CLASS.getName()+"******************");

		boolean debug = false;

		JUnitCore core = new JUnitCore();
		
		//output folder
		File fail = new File("results/fail.replay/"+TEST_CLASS.getName().replace(".", "/"));
		fail.mkdirs();
		File success = new File("results/success.replay/"+TEST_CLASS.getName().replace(".", "/"));
		success.mkdirs();
		
		String path = "results/fail/"+TEST_CLASS.getName().replace(".", "/");
		File source =new File(path);
		File[] mutants ;
		if(source.exists() && source.isDirectory()){
		mutants= source.listFiles();}
		else{
			throw new Exception("The directory fail or success dosen't exist, or is not a directory, please execute MutantSearchSpaceExplorator.runMetaProgramWith(Class<?> TEST_CLASS) first.");
		}
		
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
				
		for(int mut = 0; mut < mutants.length;mut++ ){
				Config conf = Config.getInitInstance();
								
				Map<String,Integer> mapedConf = conf.getConfig(mutants[mut].getPath()).get(selectors.get(0).getLocationClass().getName());
	
				Set<String> cles = mapedConf.keySet();
				
				int[] options = new int[selectors.size()];
				int sel = 0;
				for (String cle : cles) {

					Selector.getSelectorByName(cle).choose(mapedConf.get(cle));
					Selector.getSelectorByName(cle).setStopTime(
							System.currentTimeMillis() + 300000);
					strOptions[sel] = Selector.getSelectorByName(cle)
							.getChosenOptionDescription();
					for(int o = 0; o < Selector.getSelectorByName(cle).getOptionCount();o++ ){
						
					boolean value =(o == mapedConf.get(cle))?true:false;
					
					conf.write(	Selector.getSelectorByName(cle).getLocationClass().getName()+":"+Selector.getSelectorByName(cle).getId()+":"+Selector.getSelectorByName(cle).getOption()[o]+":"+value);
					
					}
					sel ++;
				}
				
				if (debug)
					System.out.println("Checking options: "
							+ Arrays.toString(options));

				result = core.run(TEST_CLASS);

				if (result.wasSuccessful()) {
					successes.add("   Worked !!!  -> "
							+ Arrays.toString(options) + " / "
							+ Arrays.toString(strOptions));
					
			                // On essaye avec renameTo
					File dest = new File(success.getPath()+"/"+mutants[mut].getName());
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
					File dest = new File(fail.getPath()+"/"+mutants[mut].getName());
	                new File("config.txt").renameTo(dest);
				}
			}

		

		System.out.println("killed "+failures.size());
		System.out.println("alive "+successes.size());
		
		Selector.reset();
		
		int[] val = {failures.size(),successes.size()};
		
		return val;
		
		// Show result summary
		// Sets.newHashSet(failures2.keys()).forEach(k -> {
		// System.out.println(String.format("\n-- Cases with %s", k));
		// Collection<String> texts = failures2.get(k);
		// if (k <= 2 || texts.size() < 10)
		// texts.forEach(System.out::println);
		// else
		// System.out.println("There are " + texts.size());
		// });

		// failures.forEach(System.out::println);

		// System.out.println();
		//
		// if (successes.isEmpty())
		// System.out.println("Oops, sorry, we could find a successful option");
		// else
		// successes.forEach(System.out::println);

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
