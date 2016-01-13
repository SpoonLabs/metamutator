package metamutator;


public class LoopExpressionMetaMutatorTest {
    @org.junit.Test
    public void testLoopExpressionMetaMutator() throws java.lang.Exception {
        spoon.Launcher l = new spoon.Launcher();
        l.addInputResource("src/test/java/resources/Foo.java");
        l.addProcessor(new metamutator.LoopExpressionMetaMutator());
        l.run();
        spoon.reflect.declaration.CtClass c = ((spoon.reflect.declaration.CtClass)(l.getFactory().Package().getRootPackage().getElements(new spoon.reflect.visitor.filter.NameFilter("Foo")).get(0)));
        java.lang.System.out.println("// Metaprogram: ");
        java.lang.System.out.println(c.toString());
        bsh.Interpreter bsh = new bsh.Interpreter();
        java.lang.Object o = ((java.lang.Class)(bsh.eval(c.toString()))).newInstance();
        metamutator.Selector sel1 = metamutator.Selector.getSelectorByName(((metamutator.LoopExpressionMetaMutator.PREFIX) + "1"));
        sel1.choose(1);
        org.junit.Assert.assertEquals(3, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "sum", new java.lang.Object[]{ 15 }));
        sel1.choose(2);
        org.junit.Assert.assertEquals(100, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "sum", new java.lang.Object[]{ 150 }));
    }
}

