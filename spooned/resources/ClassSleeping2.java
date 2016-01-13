package resources;


public class ClassSleeping2 {
    @org.junit.Test
    public void testwait10s() {
        try {
            java.lang.Thread.sleep(10000);
        } catch (java.lang.InterruptedException e) {
            org.junit.Assert.fail();
        }
    }
}

