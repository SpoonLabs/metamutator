
package resources;

public class Foo {
	final int varinchangeable = 1;
	public boolean op(Boolean a, Boolean b) {
		return a || b;
	}
	
	public boolean op2(Integer a, Integer b) {
		return a > b;
	}
	
	public boolean op3(Class c) {
		return Foo.class==c;
	}
	
	public int add(Integer a, Integer b) {
		int c = a + b;
		return c;
	}

	public int sum(Integer a) {
		int b = 0;
		do{
			b++;
			a--;
		}while(a > 1);
		return b;
	}
}