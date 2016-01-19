package metamutator;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import metamutator.LogicalExpressionMetaMutator;
import metamutator.Selector;
import metamutator.NumericVariableMetaMutator;

import org.junit.Test;

import spoon.Launcher;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.filter.NameFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import bsh.Interpreter;
import static org.apache.commons.lang.reflect.MethodUtils.*;

public class NumericExpressionMetaMutatorTest {

    @Test
    public void testNumericExpressionMetaMutator() throws Exception {
        // build the model and apply the transformation
        Launcher l = new Launcher();
        l.addInputResource("src/test/java/resources/Foo.java");
        l.addProcessor(new NumericVariableMetaMutator());
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
        
        // test with the second mutation hotspot
        Selector sel1=Selector.getSelectorByName(NumericVariableMetaMutator.PREFIX + "1");
        
        sel1.choose(0);// INIT B
        assertEquals(-1, invokeExactMethod(o, "add", new Object[] {3, -4}));   
        sel1.choose(1);// ABS B
        assertEquals(1, invokeExactMethod(o, "add", new Object[] {3, -4}));  
        sel1.choose(2);// MINUS B
        assertEquals(-6, invokeExactMethod(o, "add", new Object[] {3, 3})); 
        sel1.choose(3);// INC B
        assertEquals(0, invokeExactMethod(o, "add", new Object[] {3, -4}));  
        sel1.choose(4);// DEC B
        assertEquals(-2, invokeExactMethod(o, "add", new Object[] {3, -4}));
        
        NumericVariableMetaMutator numericPROC = new NumericVariableMetaMutator();
       
        CtVariableRead candidate = l.getFactory().Core().createVariableRead();
        // Fail On NOT declared variable
        assertEquals(false,numericPROC.isToBeProcessed(candidate));
        
        // TEST IsNumeric() on typeReference
        // TEST GOOD TYPE
        CtTypeReference type =  l.getFactory().Core().createTypeReference().setSimpleName(int.class.getName());
        assertEquals(true,numericPROC.isNumber(type));
        type =  l.getFactory().Core().createTypeReference().setSimpleName(long.class.getName());
        assertEquals(true,numericPROC.isNumber(type));
        type =  l.getFactory().Core().createTypeReference().setSimpleName(byte.class.getName());
        assertEquals(true,numericPROC.isNumber(type));
        type =  l.getFactory().Core().createTypeReference().setSimpleName(float.class.getName());
        assertEquals(true,numericPROC.isNumber(type));
        type =  l.getFactory().Core().createTypeReference().setSimpleName(double.class.getName());
        assertEquals(true,numericPROC.isNumber(type));
        
        // TEST NOT ALLOW TYPE
        type =  l.getFactory().Core().createTypeReference().setSimpleName(String.class.getName());
        assertEquals(false,numericPROC.isNumber(type));
        type =  l.getFactory().Core().createTypeReference().setSimpleName(boolean.class.getName());
        assertEquals(false,numericPROC.isNumber(type));
        type =  l.getFactory().Core().createTypeReference().setSimpleName(Object.class.getName());
        assertEquals(false,numericPROC.isNumber(type));
        type =  l.getFactory().Core().createTypeReference().setSimpleName(List.class.getName());
        assertEquals(false,numericPROC.isNumber(type));
    }
}