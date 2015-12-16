package resources;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ClassSleeping {
	
	@Test
	public void testwait10s() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail();
		}
	}
}