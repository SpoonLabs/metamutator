package resources;
public class Bar {
	
	public boolean op(Boolean a, Boolean b) {
		return a || b;
	}
	
	public boolean op2(Integer a, Integer b) {
		return a > b;
	}

	public boolean op3(Class c) {
		return Foo.class == c;
	}

	public Integer op_add(Integer a, Integer b){
		return a + b;
	}
	
	public Integer op_minus(Integer a, Integer b){
		return a - b;
	}
	
	public String op_concat_ss(String a, String b){
		return a + b;
	}
	
	public String op_concat_is(Integer a, String b){
		return a + b;
	}
	
	public String op_get_s(String a){
		return a;
	}
	
	public boolean op_get_b(Boolean a){
		return a;
	}
		
	public int op_constant() {
		  int i = 42;
		  return i;
	}
}