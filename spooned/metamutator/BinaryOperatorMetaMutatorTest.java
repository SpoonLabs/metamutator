package metamutator;


public class BinaryOperatorMetaMutatorTest {
    @org.junit.Test
    public void testBinaryOperatorMetaMutator() throws java.lang.Exception {
        int intialSelector = metamutator.Selector.getAllSelectors().size();
        spoon.Launcher l = new spoon.Launcher();
        l.addInputResource("src/test/java");
        l.addProcessor(new metamutator.BinaryOperatorMetaMutator());
        l.run();
        spoon.reflect.declaration.CtClass c = ((spoon.reflect.declaration.CtClass)(l.getFactory().Package().getRootPackage().getElements(new spoon.reflect.visitor.filter.NameFilter("Foo")).get(0)));
        java.lang.System.out.println("// Metaprogram: ");
        java.lang.System.out.println(c.toString());
        bsh.Interpreter bsh = new bsh.Interpreter();
        org.junit.Assert.assertEquals(intialSelector, metamutator.Selector.getAllSelectors().size());
        java.lang.Object o = ((java.lang.Class)(bsh.eval(c.toString()))).newInstance();
        org.junit.Assert.assertEquals(true, ((_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.EQ) && (((metamutator.Selector.getAllSelectors().size()) == 3))) || (_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.NE) && (((metamutator.Selector.getAllSelectors().size()) != 3))) || (_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.LT) && (((metamutator.Selector.getAllSelectors().size()) < 3))) || (_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.GT) && (((metamutator.Selector.getAllSelectors().size()) > 3))) || (_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.LE) && (((metamutator.Selector.getAllSelectors().size()) <= 3))) || (_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.GE) && (((metamutator.Selector.getAllSelectors().size()) >= 3)))));
        metamutator.Selector sel = metamutator.Selector.getSelectorByName(((metamutator.BinaryOperatorMetaMutator.PREFIX) + 2));
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op", new java.lang.Object[]{ java.lang.Boolean.TRUE , java.lang.Boolean.FALSE }));
        sel.choose(0);
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op", new java.lang.Object[]{ java.lang.Boolean.TRUE , java.lang.Boolean.FALSE }));
        sel.choose(1);
        org.junit.Assert.assertEquals(false, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op", new java.lang.Object[]{ java.lang.Boolean.TRUE , java.lang.Boolean.FALSE }));
        try {
            sel.choose(2);
            org.junit.Assert.fail();
        } catch (java.lang.IllegalArgumentException expected) {
        }
        metamutator.Selector sel1 = metamutator.Selector.getSelectorByName(((metamutator.BinaryOperatorMetaMutator.PREFIX) + "3"));
        sel1.choose(0);
        org.junit.Assert.assertEquals(false, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 3 , 3 }));
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 5 , 4 }));
        sel1.choose(1);
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 3 , 3 }));
        org.junit.Assert.assertEquals(false, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 4 , 3 }));
        sel1.choose(2);
        org.junit.Assert.assertEquals(false, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 3 , 3 }));
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 4 , 3 }));
        sel1.choose(3);
        org.junit.Assert.assertEquals(false, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 3 , 3 }));
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op2", new java.lang.Object[]{ 3 , 4 }));
    }

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot1 = metamutator.Selector.of(11,new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.GT,spoon.reflect.code.BinaryOperatorKind.EQ,spoon.reflect.code.BinaryOperatorKind.NE,spoon.reflect.code.BinaryOperatorKind.LT,spoon.reflect.code.BinaryOperatorKind.LE,spoon.reflect.code.BinaryOperatorKind.GE}).in(metamutator.BinaryOperatorMetaMutatorTest.class).id("_binaryLogicalOperatorHotSpot1");
}

