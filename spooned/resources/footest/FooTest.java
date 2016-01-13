package resources.footest;


public class FooTest {
    static resources.foo_spooned.Foo foo = new resources.foo_spooned.Foo();

    @org.junit.Test
    public void testOp() {
        org.junit.Assert.assertEquals(true, resources.footest.FooTest.foo.op(false, true));
        org.junit.Assert.assertEquals(true, resources.footest.FooTest.foo.op(true, false));
        org.junit.Assert.assertEquals(true, resources.footest.FooTest.foo.op(true, true));
        org.junit.Assert.assertEquals(false, resources.footest.FooTest.foo.op(false, false));
    }

    @org.junit.Test
    public void testOp2() {
        org.junit.Assert.assertEquals(true, resources.footest.FooTest.foo.op2(7, 2));
        org.junit.Assert.assertEquals(false, resources.footest.FooTest.foo.op2(1, 2));
        org.junit.Assert.assertEquals(false, resources.footest.FooTest.foo.op2(2, 2));
    }

    @org.junit.Test
    public void testOp3() {
        org.junit.Assert.assertEquals(false, resources.footest.FooTest.foo.op3(new java.lang.String().getClass()));
    }

    @org.junit.Test
    public void testOp4() {
        org.junit.Assert.assertEquals(true, resources.footest.FooTest.foo.op4());
    }
}

