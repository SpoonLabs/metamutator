package metamutator;
import static org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;

import metamutator.Selector;
import metamutator.VariabletoNullMetaMutator;

import org.junit.Test;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.NameFilter;
import bsh.Interpreter;

public class VariabletoNullMetaMutatorTest {
	
    @BeforeClass
    public static void before() {
    	Selector.reset();
    }


    @Test
    public void testChangetoNullMetaMutator() throws Exception {
    	
        // build the model and apply the transformation
        Launcher l = new Launcher();
        l.addInputResource("src/test/java/resources/foo");
        l.addProcessor(new VariabletoNullMetaMutator());
        l.run();
        // now we get the code of Foo
        CtClass c = (CtClass) l.getFactory().Package().getRootPackage().getElements(new NameFilter("Foo")).get(0);
        
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
        Selector sel=Selector.getSelectorByName("_variableNullHotSpot1");
                        
        // the initial version normaly, don't affect anythings
        assertEquals(true, invokeExactMethod(o, "op4", new Object[] {}));

        // now we activate the first metamutation (we dont touch the affectation)
        sel.choose(0);
        assertEquals(true, invokeExactMethod(o, "op4", new Object[] {}));
        
        // now we activate the second metamutation (Null)
        // and the expected result is false
        sel.choose(1);
        assertEquals(false, invokeExactMethod(o, "op4", new Object[] {}));
        
        // impossible option
        try {
            sel.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}

       
    }
    
    
}