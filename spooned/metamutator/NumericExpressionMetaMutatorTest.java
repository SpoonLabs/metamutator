package metamutator;


public class NumericExpressionMetaMutatorTest {
    @org.junit.Test
    public void testNumericExpressionMetaMutator() throws java.lang.Exception {
        spoon.Launcher l = new spoon.Launcher();
        l.addInputResource("src/test/java/resources/Foo.java");
        l.addProcessor(new metamutator.NumericVariableMetaMutator());
        l.run();
        spoon.reflect.declaration.CtClass c = ((spoon.reflect.declaration.CtClass)(l.getFactory().Package().getRootPackage().getElements(new spoon.reflect.visitor.filter.NameFilter("Foo")).get(0)));
        java.lang.System.out.println("// Metaprogram: ");
        java.lang.System.out.println(c.toString());
        bsh.Interpreter bsh = new bsh.Interpreter();
        java.lang.Object o = ((java.lang.Class)(bsh.eval(c.toString()))).newInstance();
        metamutator.Selector sel1 = metamutator.Selector.getSelectorByName(((metamutator.NumericVariableMetaMutator.PREFIX) + "1"));
        sel1.choose(0);
        org.junit.Assert.assertEquals(-1, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "add", new java.lang.Object[]{ 3 , -4 }));
        sel1.choose(1);
        org.junit.Assert.assertEquals(1, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "add", new java.lang.Object[]{ 3 , -4 }));
        sel1.choose(2);
        org.junit.Assert.assertEquals(-6, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "add", new java.lang.Object[]{ 3 , 3 }));
        sel1.choose(3);
        org.junit.Assert.assertEquals(0, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "add", new java.lang.Object[]{ 3 , -4 }));
        sel1.choose(4);
        org.junit.Assert.assertEquals(-2, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "add", new java.lang.Object[]{ 3 , -4 }));
        metamutator.NumericVariableMetaMutator numericPROC = new metamutator.NumericVariableMetaMutator();
        spoon.reflect.code.CtVariableRead candidate = l.getFactory().Core().createVariableRead();
        org.junit.Assert.assertEquals(false, numericPROC.isToBeProcessed(candidate));
        spoon.reflect.reference.CtTypeReference type = l.getFactory().Core().createTypeReference().setSimpleName(int.class.getName());
        org.junit.Assert.assertEquals(true, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(long.class.getName());
        org.junit.Assert.assertEquals(true, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(byte.class.getName());
        org.junit.Assert.assertEquals(true, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(float.class.getName());
        org.junit.Assert.assertEquals(true, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(double.class.getName());
        org.junit.Assert.assertEquals(true, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(java.lang.String.class.getName());
        org.junit.Assert.assertEquals(false, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(boolean.class.getName());
        org.junit.Assert.assertEquals(false, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(java.lang.Object.class.getName());
        org.junit.Assert.assertEquals(false, numericPROC.isNumber(type));
        type = l.getFactory().Core().createTypeReference().setSimpleName(java.util.List.class.getName());
        org.junit.Assert.assertEquals(false, numericPROC.isNumber(type));
    }
}

