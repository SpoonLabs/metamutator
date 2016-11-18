package metamutator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import resources.footest.FooTest;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


public class MutantSearchSpaceExploratorTest {
	
	
	 @BeforeClass
	    public static void before() {
	    	Selector.reset();
	    }
	 
	  @AfterClass
	    public static void after() throws IOException {
		    Selector.reset();
	    }

	static public class failingTest {
		@Test
		public void test() {

		}
	};


    @Test
    public void testMutantSearchSpaceExplorator() throws Exception {

    	JUnitCore core = new JUnitCore();
    	Result result  = MutantSearchSpaceExplorator.runWithThread(Object.class, core);
    	assertEquals(1,result.getFailureCount()); // no runnable methods, no test classes, message change between Junit'4.12 and Junit4.13

        Result result2  = MutantSearchSpaceExplorator.runWithThread(failingTest.class, core);
        assertEquals(0,result2.getFailureCount());
        assertEquals(1,result2.getRunCount());
    }
    
   

	/**
	 * We launch the search of selector and try them
	 * @throws Exception
	 */
	
	@Test
	public void TestMutantSearchFile () throws Exception
	{
		
		
		File f = new File("results/fail/resources/footest/FooTest");
		File successFile = new File("results/success/resources/footest/FooTest");

		f.mkdirs();
		successFile.mkdirs();
		
		// cleaning
		for (File i :f.listFiles()) { i.delete(); }
		for (File i :successFile.listFiles()) { i.delete(); }

		MutantSearchSpaceExplorator.runMetaProgramWith(FooTest.class);

		//Then we check the if the wanted file are created.
		
		assertTrue(f.exists());
		assertTrue(successFile.exists());
		
		File fm02 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot1_Op2.txt");

		File fm04 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op2.txt");
		File fm05 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op3.txt");
		File fm06 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op4.txt");
		File fm07 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op5.txt");
		File fm08 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op6.txt");
		
		File fm10 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot3_Op2.txt");
		
		File fm12 = new File("results/fail/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot4_Op2.txt");
		
		File fm14 = new File("results/fail/resources/footest/FooTest/mutant_variableNullHotSpot1_Op2.txt");
				

		assertEquals(9,f.listFiles().length);
		assertEquals(0,successFile.listFiles().length);

		assertTrue(fm02.exists());
		assertTrue(fm04.exists());
		assertTrue(fm05.exists());
		assertTrue(fm06.exists());
		assertTrue(fm07.exists());
		assertTrue(fm08.exists());
		assertTrue(fm10.exists());
		assertTrue(fm12.exists());
		assertTrue(fm14.exists());
		
	}
	
	
	@Test
	public void TestMutantSearchDir () throws Exception
	{
		
		
   
		File f = new File("results/fail/resources/search_replay_test/SearchReplayTestClass");
		File s = new File("results/success/resources/search_replay_test/SearchReplayTestClass");

		f.mkdirs();
		s.mkdirs();
		
		// cleaning
		for (File i :f.listFiles()) { i.delete(); }
		for (File i :s.listFiles()) { i.delete(); }

		MutantSearchSpaceExplorator.runMetaProgramIn("target/test-classes","resources/search_replay_test");
		

		assertTrue(f.exists());
		assertTrue(s.exists());
		

		File fm01 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op2.txt");
		File fm11 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op3.txt");
		File fm12 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op4.txt");
		File fm13 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op2.txt");
		
		File fm00 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op1.txt");
		File fm10 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op5.txt");
		File fm14 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op6.txt");
		File fm15 = new File("results/fail/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op1.txt");
		
		File sm01 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op2.txt");
		File sm11 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op3.txt");
		File sm12 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op4.txt");
		File sm13 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op2.txt");


		
		File sm00 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op1.txt");
		File sm10 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op5.txt");
		File sm14 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op6.txt");
		File sm15 = new File("results/success/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op1.txt");

		assertTrue(fm01.exists());
		assertTrue(fm11.exists());
		assertTrue(fm12.exists());
		assertTrue(fm13.exists());
		
		assertFalse(fm00.exists());
		assertFalse(fm10.exists());
		assertFalse(fm14.exists());
		assertFalse(fm15.exists());

		
		
		assertFalse(sm01.exists());
		assertFalse(sm11.exists());
		assertFalse(sm12.exists());
		assertFalse(sm13.exists());

		
		//assertTrue(sm00.exists());
		assertTrue(sm10.exists());
		assertTrue(sm14.exists());
		//assertTrue(sm15.exists());
		
		File fm31 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op2.txt");
		File fm41 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op3.txt");
		File fm42 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op4.txt");
		File fm43 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op2.txt");
		
		File fm30 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op1.txt");
		File fm40 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op5.txt");
		File fm44 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op6.txt");
		File fm45 = new File("results/fail/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op1.txt");
		
		File sm31 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op2.txt");
		File sm41 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op3.txt");
		File sm42 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op4.txt");
		File sm43 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op2.txt");


		
		File sm30 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op1.txt");
		File sm40 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op5.txt");
		File sm44 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op6.txt");
		File sm45 = new File("results/success/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op1.txt");

		assertTrue(fm31.exists());
		assertTrue(fm41.exists());
		assertTrue(fm42.exists());
		assertTrue(fm43.exists());
		
		assertFalse(fm30.exists());
		assertFalse(fm40.exists());
		assertFalse(fm44.exists());
		assertFalse(fm45.exists());

		
		
		assertFalse(sm31.exists());
		assertFalse(sm41.exists());
		assertFalse(sm42.exists());
		assertFalse(sm43.exists());

		
		//assertTrue(sm30.getAbsolutePath(),sm30.exists());
		assertTrue(sm40.exists());
		assertTrue(sm44.exists());
		//assertTrue(sm45.exists());


	}
	
	@Test
	public void testGetPackage(){
		
		assertTrue(MutantSearchSpaceExplorator.getPackage("toto").equals(""));
		assertTrue(MutantSearchSpaceExplorator.getPackage("toto/titi").equals("toto"));
		assertTrue(MutantSearchSpaceExplorator.getPackage("toto/titi/tata").equals("toto.titi"));
		assertTrue(MutantSearchSpaceExplorator.getPackage("toto/titi/tata/tutu").equals("toto.titi.tata"));
		
	}
	
	 
  
}
