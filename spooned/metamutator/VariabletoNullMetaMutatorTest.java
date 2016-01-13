package metamutator;


public class VariabletoNullMetaMutatorTest {
    @org.junit.BeforeClass
    public static void before() {
        metamutator.Selector.reset();
    }

    @org.junit.Test
    public void testChangetoNullMetaMutator() throws java.lang.Exception {
        spoon.Launcher l = new spoon.Launcher();
        l.addInputResource("src/test/java/resources/foo");
        l.addProcessor(new metamutator.VariabletoNullMetaMutator());
        l.run();
        spoon.reflect.declaration.CtClass c = ((spoon.reflect.declaration.CtClass)(l.getFactory().Package().getRootPackage().getElements(new spoon.reflect.visitor.filter.NameFilter("Foo")).get(0)));
        java.lang.System.out.println("// Metaprogram: ");
        java.lang.System.out.println(c.toString());
        bsh.Interpreter bsh = new bsh.Interpreter();
        org.junit.Assert.assertEquals(0, metamutator.Selector.getAllSelectors().size());
        java.lang.Object o = ((java.lang.Class)(bsh.eval(c.toString()))).newInstance();
        org.junit.Assert.assertEquals(1, metamutator.Selector.getAllSelectors().size());
        metamutator.Selector sel = metamutator.Selector.getSelectorByName("_variableNullHotSpot1");
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op4", new java.lang.Object[]{  }));
        sel.choose(0);
        org.junit.Assert.assertEquals(true, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op4", new java.lang.Object[]{  }));
        sel.choose(1);
        org.junit.Assert.assertEquals(false, org.apache.commons.lang.reflect.MethodUtils.invokeExactMethod(o, "op4", new java.lang.Object[]{  }));
        try {
            sel.choose(2);
            org.junit.Assert.fail();
        } catch (java.lang.IllegalArgumentException expected) {
        }
    }
}

