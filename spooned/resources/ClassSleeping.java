package resources;


public class ClassSleeping {
    @org.junit.Test
    public void testwait10s() {
        try {
            java.lang.Thread.sleep(1000);
        } catch (java.lang.InterruptedException e) {
            org.junit.Assert.fail();
        }
    }
}

