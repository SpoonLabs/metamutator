import static org.junit.Assert.assertNotNull;
import metamutator.UniqueTestGenerator;

import org.junit.Test;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;


public class UniqueTestGeneratorTest {

	@Test
	public void test() throws Exception {
		Launcher spoon = new Launcher();
		spoon.addInputResource("src/test/java");
		spoon.addProcessor(new UniqueTestGenerator());
		spoon.run();
		CtClass<Object> testClass = spoon.getFactory().Class().get("test.TestSuite");
		System.out.println(testClass);
		assertNotNull(testClass);
	}
}
