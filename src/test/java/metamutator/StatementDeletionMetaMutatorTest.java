package metamutator;

import static org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bsh.Interpreter;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.NameFilter;

public class StatementDeletionMetaMutatorTest {
	
	public Object createStatementResourceObjectTransformed() throws Exception{
        
        Launcher l = new Launcher();
        l.addInputResource("src/test/java/resources/StatementResource.java");
        l.addProcessor(new StatementDeletionMetaMutator());
        l.run();

        // now we get the code of StatementResource
        CtClass c = (CtClass) l.getFactory().Package().getRootPackage().getElements(new NameFilter("StatementResource")).get(0);
        
        // printing the metaprogram
        System.out.println("// Metaprogram: ");
        System.out.println(c.toString());

        // we prepare an interpreter for the transformed code
        Interpreter bsh = new Interpreter();

        // creating a new instance of the class
        Object o = ((Class)bsh.eval(c.toString())).newInstance();
        
        return o;

	}
	
	
    @Test
    public void testSwitchDeletionMetaMutator() throws Exception {  
    	Object o = createStatementResourceObjectTransformed();
        // test with the first (SWITCH DELETION)
        Selector sel1=Selector.getSelectorByName(StatementDeletionMetaMutator.PREFIX + "1");
        assertEquals('C', invokeExactMethod(o, "returnLetterFromSwitchCase", new Object[] {3}));
        sel1.choose(0);
        assertEquals('C', invokeExactMethod(o, "returnLetterFromSwitchCase", new Object[] {3}));
        sel1.choose(1);
        assertEquals('\u0000', invokeExactMethod(o, "returnLetterFromSwitchCase", new Object[] {3}));
        
        try {
            sel1.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}
    }
	
    @Test
    public void testIfDeletionMetaMutator() throws Exception {  
    	Object o = createStatementResourceObjectTransformed();
        //IF DELETION
        Selector sel5=Selector.getSelectorByName(StatementDeletionMetaMutator.PREFIX + "5");
        assertEquals(10, invokeExactMethod(o, "returnMax10", new Object[] {13}));
        sel5.choose(0);
        assertEquals(10, invokeExactMethod(o, "returnMax10", new Object[] {13}));
        sel5.choose(1);
        assertEquals(0, invokeExactMethod(o, "returnMax10", new Object[] {13}));
        try {
            sel5.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){} 
    }
	
    @Test
    public void testDoDeletionMetaMutator() throws Exception {  
    	Object o = createStatementResourceObjectTransformed();
        //DO DELETION
        Selector sel6=Selector.getSelectorByName(StatementDeletionMetaMutator.PREFIX + "6");
        assertEquals(18, invokeExactMethod(o, "returnTotalFromDo", new Object[] {18}));
        sel6.choose(0);
        assertEquals(18, invokeExactMethod(o, "returnTotalFromDo", new Object[] {18}));
        sel6.choose(1);
        assertEquals(0, invokeExactMethod(o, "returnTotalFromDo", new Object[] {18}));
        try {
            sel6.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}
    }
	
    @Test
    public void testForDeletionMetaMutator() throws Exception {  
    	Object o = createStatementResourceObjectTransformed();
        //FOR DELETION
        Selector sel7=Selector.getSelectorByName(StatementDeletionMetaMutator.PREFIX + "7");
        assertEquals(18, invokeExactMethod(o, "returnTotalFromFor", new Object[] {18}));
        sel7.choose(0);
        assertEquals(18, invokeExactMethod(o, "returnTotalFromFor", new Object[] {18}));
        sel7.choose(1);
        assertEquals(0, invokeExactMethod(o, "returnTotalFromFor", new Object[] {18}));
        try {
            sel7.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}
        
    }
	
    @Test
    public void testWhileDeletionMetaMutator() throws Exception {  
    	Object o = createStatementResourceObjectTransformed();
        Selector sel8=Selector.getSelectorByName(StatementDeletionMetaMutator.PREFIX + "8");
        assertEquals(18, invokeExactMethod(o, "returnTotalFromWhile", new Object[] {18}));
        sel8.choose(0);
        assertEquals(18, invokeExactMethod(o, "returnTotalFromWhile", new Object[] {18}));
        sel8.choose(1);
        assertEquals(0, invokeExactMethod(o, "returnTotalFromWhile", new Object[] {18}));
        try {
            sel8.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}
                
    }
	
    @Test
    public void testForEachDeletionMetaMutator() throws Exception {  
    	Object o = createStatementResourceObjectTransformed();
      //FOREACH DELETION
        Selector sel9=Selector.getSelectorByName(StatementDeletionMetaMutator.PREFIX + "9");
        assertEquals(13, invokeExactMethod(o, "returntotalFromForEachFromArray", new Object[] {new int[]{2,5,6}}));
        sel9.choose(0);
        assertEquals(13, invokeExactMethod(o, "returntotalFromForEachFromArray", new Object[] {new int[]{2,5,6}}));
        sel9.choose(1);
        assertEquals(0, invokeExactMethod(o, "returntotalFromForEachFromArray", new Object[] {new int[]{2,5,6}}));
        try {
            sel9.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}
        
    }
    
    @Test
    public void testAssignmentInIf() throws Exception {  
    	Object o = createStatementResourceObjectTransformed();
      //FOREACH DELETION
        Selector sel10=Selector.getSelectorByName(StatementDeletionMetaMutator.PREFIX + "10");
        assertEquals("Bonjour", invokeExactMethod(o, "BonjourOrHello", new Object[] {true}));
        sel10.choose(0);
        assertEquals("Bonjour", invokeExactMethod(o, "BonjourOrHello", new Object[] {true}));
        sel10.choose(1);
        assertEquals(null, invokeExactMethod(o, "BonjourOrHello", new Object[] {true}));
        try {
            sel10.choose(2);
            fail();
        }
        catch (IllegalArgumentException expected){}
        
    }  

}