package metamutator;

import static org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bsh.Interpreter;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.NamedElementFilter;

public class ArithmeticOperatorMetaMutatorTest {

	@Test
    public void testArithmeticOperatorMetaMutator() throws Exception {
        // build the model and apply the transformation
        Launcher l = new Launcher();
        l.addInputResource("src/test/java/resources/Bar.java");
        l.addProcessor(new ArithmeticOperatorMetaMutator());
        l.run();

        // now we get the code of Foo
        CtClass c = l.getFactory().Package().getRootPackage().getElements(new NamedElementFilter<>(CtClass.class, "Bar")).get(0);
        
        // printing the metaprogram
        System.out.println("// Metaprogram: ");
        System.out.println(c.toString());

        // we prepare an interpreter for the transformed code
        Interpreter bsh = new Interpreter();

        // creating a new instance of the class
        Object o = ((Class)bsh.eval(c.toString())).newInstance();        
              
        // test with the first
        Selector sel=Selector.getSelectorByName(ArithmeticOperatorMetaMutator.PREFIX + "1");
        
        // check PLUS
        sel.choose(0);
        assertEquals(5, invokeExactMethod(o, "op_add", new Object[] {2, 3}));

        // check MINUS
        sel.choose(1);
        assertEquals(5, invokeExactMethod(o, "op_add", new Object[] {10, 5}));
        
        // check MUL
        sel.choose(2);
        assertEquals(16, invokeExactMethod(o, "op_add", new Object[] {4, 4}));
        
        // check DIV
        sel.choose(3);
        assertEquals(4, invokeExactMethod(o, "op_add", new Object[] {20, 5}));
        
        // second selector 
        sel=Selector.getSelectorByName(ArithmeticOperatorMetaMutator.PREFIX + "2");

        
        // check MINUS
        sel.choose(0);
        assertEquals(5, invokeExactMethod(o, "op_minus", new Object[] {10, 5}));

        // check PLUS
        sel.choose(1);
        assertEquals(5, invokeExactMethod(o, "op_minus", new Object[] {2, 3}));
        
        // check MUL
        sel.choose(2);
        assertEquals(16, invokeExactMethod(o, "op_minus", new Object[] {4, 4}));
        
        // check DIV
        sel.choose(3);
        assertEquals(4, invokeExactMethod(o, "op_minus", new Object[] {20, 5}));
    }
}
