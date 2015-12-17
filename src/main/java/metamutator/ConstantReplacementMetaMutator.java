package metamutator;

import java.util.EnumSet;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.ModifierKind;

public class ConstantReplacementMetaMutator extends AbstractProcessor<CtVariableRead<?>> {

	public static final String PREFIX = "_constantOperatorMetaMutator";
	private static final int procId = 5;
	
	public enum CONSTANT_REP {
		ZERO,
		INT_MAX,
		MIN_MIN
	};
	
	private static int thisIndex = 0;
	
	private static final EnumSet<CONSTANT_REP> consRep = EnumSet.
			of(CONSTANT_REP.ZERO, CONSTANT_REP.INT_MAX, CONSTANT_REP.MIN_MIN);
	
	public boolean isToBeProcessed(CtVariableRead element){
		try {
			if((element.getType().toString().contains("int"))&&
					(!(element.getVariable().getDeclaration().getDefaultExpression() == null))){
						if(!(element.getVariable().getDeclaration().getDefaultExpression().toString().contains(PREFIX))
								&& (!element.getVariable().getDeclaration().getModifiers().contains(ModifierKind.STATIC)))
									return true;				
			}
		} catch (Exception e) {

		}
		return false;	
	}
	
	private String permutations(CONSTANT_REP value) {
		switch(value) {
		case ZERO : return "0";
		case INT_MAX : return Integer.toString(Integer.MAX_VALUE - 1);
		case MIN_MIN : return Integer.toString(Integer.MIN_VALUE + 1);
		default : return "";}
		}
	
	
	@Override
	public void process(CtVariableRead element) {
		
		thisIndex++;
		CtExpression valToChange= null;
		try {
			valToChange = element.getVariable().getDeclaration().getDefaultExpression();			
			String expression = "(";
			for(CONSTANT_REP c : consRep){
				expression += PREFIX+thisIndex + ".is(" + c.getClass().getCanonicalName() +"."+c.toString()+ ")?( "+permutations(c)+" ):(";
			}
			expression += valToChange + "))))";
	
			CtCodeSnippetExpression<String> codeSnippet = getFactory().Core()
					.createCodeSnippetExpression();
			codeSnippet.setValue(expression);
			
			try {
				valToChange.replace(codeSnippet);
				Selector.generateSelector(element, CONSTANT_REP.ZERO, thisIndex, procId, consRep, PREFIX);

			} catch (Exception e) {
				System.err.println("Element not changed");
			}
			
		} catch (Exception e) {

		} finally {
			System.out.println("nb mutants " +thisIndex);
		}
		
	}
	
}