package metamutator;
import static org.junit.Assert.*;


import metamutator.LoopExpressionMetaMutator;
import metamutator.Selector;

import org.junit.Test;

import spoon.Launcher;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.NameFilter;
import bsh.Interpreter;
import static org.apache.commons.lang.reflect.MethodUtils.*;

public class LoopExpressionMetaMutatorTest {

    @Test
    public void testLoopExpressionMetaMutator() throws Exception {
        // build the model and apply the transformation
        Launcher l = new Launcher();
        l.addInputResource("src/test/java/resources/Foo.java");
        l.addProcessor(new LoopExpressionMetaMutator());
        l.run();

        // now we get the code of Foo
        CtClass c = (CtClass) l.getFactory().Package().getRootPackage().getElements(new NameFilter("Foo")).get(0);
        
        // printing the metaprogram
        System.out.println("// Metaprogram: ");
        System.out.println(c.toString());
        
        // we prepare an interpreter for the transformed code
        Interpreter bsh = new Interpreter();
        // creating a new instance of the class
        Object o = ((Class)bsh.eval(c.toString())).newInstance();
        
        Selector sel1=Selector.getSelectorByName( LoopExpressionMetaMutator.PREFIX + "1");
        //sel1.choose(0);// NO ROUND
       // assertEquals(1, invokeExactMethod(o, "sum", new Object[] {15}));  
        sel1.choose(1);// ROUNDS 3
        assertEquals(3, invokeExactMethod(o, "sum", new Object[] {15}));  
        sel1.choose(2);// ROUNDS 100
        assertEquals(100, invokeExactMethod(o, "sum", new Object[] {150}));
    }
}