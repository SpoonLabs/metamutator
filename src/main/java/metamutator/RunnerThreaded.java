package metamutator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * this class launches a test class
 * @author abdelrhamanebenhammou
 *
 */
public class RunnerThreaded extends Thread{
	/**
	 *  myResult represents result of test class
	 */
	public Result myResult = null;
	private JUnitCore core;
	private Class<?> classes;
	
	public RunnerThreaded(JUnitCore core, Class<?> classes) {
		this.core = core;
		this.classes = classes;
	}
	
	/**
	 * Launch Test Class
	 */
	public void run() {
		myResult = core.run(classes);
	}
	
	public Result getResult() {
		return myResult;
	}
	
}
