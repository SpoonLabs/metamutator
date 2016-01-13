package metamutator;


@org.junit.Ignore
public class ReplayMutantTest {
    @org.junit.Test
    public void TestMutantReplayFile() throws java.lang.Exception {
        metamutator.MutantReplay.replayMetaProgramWith(resources.footest.FooTest.class);
        java.io.File f = new java.io.File("results/fail.replay/resources/footest/FooTest");
        java.io.File s = new java.io.File("results/success.replay/resources/footest/FooTest");
        org.junit.Assert.assertTrue(f.exists());
        org.junit.Assert.assertTrue(s.exists());
        java.io.File fm02 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot1_Op2.txt");
        java.io.File fm04 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op2.txt");
        java.io.File fm05 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op3.txt");
        java.io.File fm06 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op4.txt");
        java.io.File fm07 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op5.txt");
        java.io.File fm08 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot2_Op6.txt");
        java.io.File fm10 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot3_Op2.txt");
        java.io.File fm12 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_binaryLogicalOperatorHotSpot4_Op2.txt");
        java.io.File fm14 = new java.io.File("results/fail.replay/resources/footest/FooTest/mutant_variableNullHotSpot1_Op2.txt");
        org.junit.Assert.assertEquals(9, f.listFiles().length);
        org.junit.Assert.assertEquals(0, s.listFiles().length);
        org.junit.Assert.assertTrue(fm02.exists());
        org.junit.Assert.assertTrue(fm04.exists());
        org.junit.Assert.assertTrue(fm05.exists());
        org.junit.Assert.assertTrue(fm06.exists());
        org.junit.Assert.assertTrue(fm07.exists());
        org.junit.Assert.assertTrue(fm08.exists());
        org.junit.Assert.assertTrue(fm10.exists());
        org.junit.Assert.assertTrue(fm12.exists());
        org.junit.Assert.assertTrue(fm14.exists());
        metamutator.Selector.reset();
    }

    @org.junit.Test
    public void TestMutantReplayDir() throws java.lang.Exception {
        java.io.File f;
        java.io.File s;
        java.io.File fb;
        java.io.File sb;
        metamutator.MutantReplay.replayMetaProgramIn("target/test-classes/", "resources/search_replay_test");
        f = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass");
        s = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass");
        fb = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis");
        sb = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis");
        org.junit.Assert.assertTrue(f.exists());
        org.junit.Assert.assertTrue(s.exists());
        org.junit.Assert.assertTrue(fb.exists());
        org.junit.Assert.assertTrue(sb.exists());
        java.io.File fm01 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op2.txt");
        java.io.File fm11 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op3.txt");
        java.io.File fm12 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op4.txt");
        java.io.File fm13 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op2.txt");
        java.io.File fm00 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op1.txt");
        java.io.File fm10 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op5.txt");
        java.io.File fm14 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op6.txt");
        java.io.File fm15 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op1.txt");
        java.io.File sm01 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op2.txt");
        java.io.File sm11 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op3.txt");
        java.io.File sm12 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op4.txt");
        java.io.File sm13 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op2.txt");
        java.io.File sm00 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op1.txt");
        java.io.File sm10 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op5.txt");
        java.io.File sm14 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_binaryLogicalOperatorHotSpot3_Op6.txt");
        java.io.File sm15 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClass/mutant_variableNullHotSpot3_Op1.txt");
        org.junit.Assert.assertTrue(fm01.exists());
        org.junit.Assert.assertTrue(fm11.exists());
        org.junit.Assert.assertTrue(fm12.exists());
        org.junit.Assert.assertTrue(fm13.exists());
        org.junit.Assert.assertFalse(fm00.exists());
        org.junit.Assert.assertFalse(fm10.exists());
        org.junit.Assert.assertFalse(fm14.exists());
        org.junit.Assert.assertFalse(fm15.exists());
        org.junit.Assert.assertFalse(sm01.exists());
        org.junit.Assert.assertFalse(sm11.exists());
        org.junit.Assert.assertFalse(sm12.exists());
        org.junit.Assert.assertFalse(sm13.exists());
        org.junit.Assert.assertFalse(sm00.exists());
        org.junit.Assert.assertFalse(sm10.exists());
        org.junit.Assert.assertFalse(sm14.exists());
        org.junit.Assert.assertFalse(sm15.exists());
        java.io.File fm31 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op2.txt");
        java.io.File fm41 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op3.txt");
        java.io.File fm42 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op4.txt");
        java.io.File fm43 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op2.txt");
        java.io.File fm30 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op1.txt");
        java.io.File fm40 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op5.txt");
        java.io.File fm44 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op6.txt");
        java.io.File fm45 = new java.io.File("results/fail.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op1.txt");
        java.io.File sm31 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op2.txt");
        java.io.File sm41 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op3.txt");
        java.io.File sm42 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op4.txt");
        java.io.File sm43 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op2.txt");
        java.io.File sm30 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op1.txt");
        java.io.File sm40 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op5.txt");
        java.io.File sm44 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_binaryLogicalOperatorHotSpot4_Op6.txt");
        java.io.File sm45 = new java.io.File("results/success.replay/resources/search_replay_test/SearchReplayTestClassBis/mutant_variableNullHotSpot4_Op1.txt");
        org.junit.Assert.assertTrue(fm31.exists());
        org.junit.Assert.assertTrue(fm41.exists());
        org.junit.Assert.assertTrue(fm42.exists());
        org.junit.Assert.assertTrue(fm43.exists());
        org.junit.Assert.assertFalse(fm30.exists());
        org.junit.Assert.assertFalse(fm40.exists());
        org.junit.Assert.assertFalse(fm44.exists());
        org.junit.Assert.assertFalse(fm45.exists());
        org.junit.Assert.assertFalse(sm31.exists());
        org.junit.Assert.assertFalse(sm41.exists());
        org.junit.Assert.assertFalse(sm42.exists());
        org.junit.Assert.assertFalse(sm43.exists());
        org.junit.Assert.assertFalse(sm30.exists());
        org.junit.Assert.assertFalse(sm40.exists());
        org.junit.Assert.assertFalse(sm44.exists());
        org.junit.Assert.assertFalse(sm45.exists());
    }

    @org.junit.AfterClass
    public static void after() throws java.io.IOException {
        metamutator.Selector.reset();
        java.io.File results = new java.io.File("results");
        org.apache.commons.io.FileUtils.deleteDirectory(results);
    }
}

