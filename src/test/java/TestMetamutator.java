import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import metamutator.BinaryOperatorMetaMutator;
import metamutator.Selector;

import org.junit.Test;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;
import bsh.Interpreter;
import static org.apache.commons.lang.reflect.MethodUtils.*;

public class TestMetamutator {

    @Test
    public void testBinaryOperatorMetaMutator() throws Exception {
        // build the model and apply the transformation
        Launcher l = new Launcher();
        l.addInputResource("src/test/java");
        l.addProcessor(new BinaryOperatorMetaMutator());
        l.run();

        // now we get the 
        List<CtClass> classes = l.getFactory().Package().getRootPackage().getElements(new TypeFilter(CtClass.class));
        CtClass c = classes.get(0);
        System.out.println(c.toString());

        // we prepare an interpreter for the transformed code
        Interpreter bsh = new Interpreter();

        // there is no selector before loading the class
        assertEquals(0,Selector.getAllSelectors().size());

        // creating a new instance of the class
        Object o = ((Class)bsh.eval(c.toString())).newInstance();        
        assertEquals(2,Selector.getAllSelectors().size());
        
        // test with the first
        Selector sel=Selector.getSelectorsByName("_s1");
        
        // the initial version is OR
        assertEquals(true, invokeExactMethod(o, "op", new Object[] {Boolean.TRUE, Boolean.FALSE}));

        // now we activate the first metamutation (the initial OR)
        sel.choose(0);
        assertEquals(true, invokeExactMethod(o, "op", new Object[] {Boolean.TRUE, Boolean.FALSE}));
        
        // now we activate the second metamutation (AND)
        // and the expected result is false
        sel.choose(1);
        assertEquals(false, invokeExactMethod(o, "op", new Object[] {Boolean.TRUE, Boolean.FALSE}));
        
        // impossible option
        try {
            sel.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}

        // test with the first mutation
        Selector sel1=Selector.getSelectorsByName("_s2");
        sel1.choose(0);// GT
        assertEquals(false, invokeExactMethod(o, "op2", new Object[] {3, 3}));
        assertEquals(true, invokeExactMethod(o, "op2", new Object[] {5, 4}));
        sel1.choose(1); // EQ
        assertEquals(true, invokeExactMethod(o, "op2", new Object[] {3, 3}));
        assertEquals(false, invokeExactMethod(o, "op2", new Object[] {4, 3}));
        sel1.choose(2); // NE
        assertEquals(false, invokeExactMethod(o, "op2", new Object[] {3, 3}));
        assertEquals(true, invokeExactMethod(o, "op2", new Object[] {4, 3}));
        sel1.choose(3); // LT
        assertEquals(false, invokeExactMethod(o, "op2", new Object[] {3, 3}));
        assertEquals(true, invokeExactMethod(o, "op2", new Object[] {3, 4}));        
        
    }
}