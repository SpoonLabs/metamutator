package resources.search_replay_test;


public class SearchReplayTestClass {
    static resources.search_replay_spoon.search_replay_src.SearchReplayClass sRC = new resources.search_replay_spoon.search_replay_src.SearchReplayClass();

    @org.junit.Test
    public void op() {
        org.junit.Assert.assertTrue(resources.search_replay_test.SearchReplayTestClass.sRC.op());
    }
}

