package metamutator;


public class ConstantReplacementMetaMutatorTest {
    @org.junit.Test
    public void testConstantReplacementMetaMutator() throws java.lang.Exception {
        spoon.Launcher l = new spoon.Launcher();
        l.addInputResource("src/test/java/resources/Bar.java");
        l.addProcessor(new metamutator.ConstantReplacementMetaMutator());
        l.run();
        spoon.reflect.declaration.CtClass c = ((spoon.reflect.declaration.CtClass)(l.getFactory().Package().getRootPackage().getElements(new spoon.reflect.visitor.filter.NameFilter("Bar")).get(0)));
        java.lang.System.out.println("// Metaprogram: ");
        java.lang.System.out.println(c.toString());
        bsh.Interpreter bsh = new bsh.Interpreter();
        java.lang.Object o = ((java.lang.Class)(bsh.eval(c.toString()))).newInstance();
        metamutator.Selector sel = metamutator.Selector.getSelectorByName(((metamutator.ConstantReplacementMetaMutator.PREFIX) + "1"));
        sel.choose(0);
        org.junit.Assert.assertEquals(0, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op_constant", new java.lang.Object[]{  }));
        sel.choose(1);
        org.junit.Assert.assertEquals(((java.lang.Integer.MAX_VALUE) - 1), org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op_constant", new java.lang.Object[]{  }));
        sel.choose(2);
        org.junit.Assert.assertEquals(((java.lang.Integer.MIN_VALUE) + 1), org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op_constant", new java.lang.Object[]{  }));
    }
}

