package resources;

public class StatementResource {
	
	
	public StatementResource(){
		
	}	
	
	public int returnMax10(Integer a) {
		if(a < 10){
			return a;
		}
		else{
			return 10;
		}
	}
	
	public void throwException() throws Exception{
		throw new Exception();
	}
	
	public int returnTotalFromFor(Integer total){
		int i = 0;
		for(i=0; i<total; i++){
		}
		return i;
	}
	
	public int returnTotalFromWhile(Integer total){
		int i = 0;
		while(i<total){
			i++;
		}
		return i;
	}
	
	public int returnTotalFromDo(Integer total){
		int i = 0;
		do{
			i++;
		}
		while(i < total);
		return i;
	}
	
	public int returntotalFromForEachFromArray(int[] array){
		int total = 0;
		for(int i : array){
			total = total + i;
		}
		return total;
	}
	
	
	public char returnLetterFromSwitchCase(Integer i){
		switch(i){
		case 1 :
			return 'A';
		case 2 :
			return 'B';
		case 3 :
			return 'C';
		case 4 :
			return 'D';
		default :
			return 'E';
		}
	}
	
	public int addWithoutNegative(int[] array){
		int total = 0;
		for(int i : array){
			if(i < 0 ) continue;
			total = total + i;
		}
		return total;
	}


	public String BonjourOrHello(Boolean french){
		String hello;
		if(french){
			hello = "Bonjour";
		}
		else{
			hello = "Hello";
		}
		return hello;
	}
	
	/*public String LocalVariableNotReachableInElse(){
		if()
	}*/
}
