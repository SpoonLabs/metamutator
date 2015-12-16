import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import metamutator.MutantSearchSpaceExplorator;
import metamutator.RunnerThreaded;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class MutantSearchSpaceExploratorTest {

    @Test
    public void testRunnerThreaded() throws Exception {
    	JUnitCore core = new JUnitCore();
    	RunnerThreaded runner = new RunnerThreaded(core, ClassSleeping.class);
    	assertNull(runner.getResult());
    	runner.run();
    	assertEquals(true, runner.getResult() instanceof Result);
    }
    
    
    @Test
    public void testMutantSearchSpaceExplorator() throws Exception {
    	JUnitCore core = new JUnitCore();
    	Result result  = MutantSearchSpaceExplorator.runWithThread(ClassSleeping2.class, core);
    	assertNull(result);
    	
    	result  = MutantSearchSpaceExplorator.runWithThread(ClassSleeping.class, core);
    	assertNotNull(result);
    }
}