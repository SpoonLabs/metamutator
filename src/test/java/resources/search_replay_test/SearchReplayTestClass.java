package resources.search_replay_test;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import resources.search_replay_spoon.search_replay_src.SearchReplayClass;

public class SearchReplayTestClass {
	
	static  SearchReplayClass sRC = new SearchReplayClass();
	  @Test
	  public void op(){
		
		  assertTrue(sRC.op());
	  }

}