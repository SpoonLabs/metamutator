package metamutator;

import static org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import bsh.Interpreter;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.NamedElementFilter;

public class ConstantReplacementMetaMutatorTest {

	@Test
	public void testConstantReplacementMetaMutator() throws Exception{
		//clean selector
		Selector.reset();
		
		Launcher l = new Launcher();
        l.addInputResource("src/test/java/resources/Bar.java");
        l.addProcessor(new IntegerConstantReplacementMetaMutator());
        l.run();
        // now we get the code of Foo
        CtClass c = l.getFactory().Package().getRootPackage().getElements(new NamedElementFilter<>(CtClass.class, "Bar")).get(0);
        
        // printing the metaprogram
        System.out.println("// Metaprogram: ");
        System.out.println(c.toString());

        // we prepare an interpreter for the transformed code
        Interpreter bsh = new Interpreter();
        // there is no selector before loading the class
        assertEquals(0,Selector.getAllSelectors().size());
        // creating a new instance of the class
        Object o = ((Class)bsh.eval(c.toString())).newInstance();
        assertEquals(1,Selector.getAllSelectors().size());
        
        // test with the first
        Selector sel=Selector.getSelectorByName(IntegerConstantReplacementMetaMutator.PREFIX + "1");
        
        sel.choose(0);//NULL
        assertEquals(0, invokeExactMethod(o, "op_constant", new Object[] {}));
        
        sel.choose(1);
        assertEquals(Integer.MAX_VALUE-1, invokeExactMethod(o, "op_constant", new Object[] {}));
        
        sel.choose(2);
        assertEquals(Integer.MIN_VALUE+1, invokeExactMethod(o, "op_constant", new Object[] {}));
	}
}