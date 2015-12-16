package resources.foo;

public class Foo {
	
	public boolean op(Boolean a, Boolean b) {
		return a || b;
	}
	
	public boolean op2(Integer a, Integer b) {
		return a > b;
	}

	public boolean op3(Class c) {
		return Foo.class==c;
	}
	
	public boolean op4(){
		String medt = "Theobaldie";
		return medt != null;
	}
}