package metamutator;


import java.util.ArrayList;
import java.util.EnumSet;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.support.reflect.code.CtBinaryOperatorImpl;
import spoon.support.reflect.code.CtUnaryOperatorImpl;

/**
 * inserts a mutation hotspot for each Numeric Variable
 * @author abdelrhamanebenhammou
 *
 */
public class NumericVariableMetaMutator 
				extends AbstractProcessor<CtVariableRead> {

	public static final String PREFIX = "_numericExpressionMetaMutator";
	
	/**
	 * 
	 */
	public enum UNARY {
		// NO CHANGE
		INIT,
		// Absolute Value	
		ABS,
		// Unary minus
		MINUS,
		// Increment
		INC,
		// Decrement
		DEC
	};
	private static final EnumSet<UNARY> absSet = EnumSet
			.of(UNARY.ABS, UNARY.MINUS, UNARY.INC, UNARY.DEC);
	
	/**
	 * 
	 */
	public static int thisIndex = 0;
	
	/**
	 * Accept Numeric Variable
	 */
	@Override
	public boolean isToBeProcessed(CtVariableRead candidate) {
		
		// SKIP not declared variable and Finale variable
		if(candidate.getVariable() == null) return false;
		if(candidate.getVariable().getModifiers().contains(ModifierKind.FINAL)) return false;
		
		
		// SKIP IF VARIABLE IS CASTED
		if(candidate.getTypeCasts().size() > 0) return false;
		for(CtTypeReference type : candidate.getReferencedTypes()) {
			if(!this.isNumber(type)) return false;
		}
		
		if( candidate.getParent().getClass().equals(CtUnaryOperatorImpl.class)) return false;
		
		if(this.isNumber(candidate.getVariable().getType())){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param ctVariableReference
	 * @return
	 */
	public boolean isNumber(CtTypeReference type) {
		return type.getSimpleName().equals("int")
			|| type.getSimpleName().equals("long")
			|| type.getSimpleName().equals("byte")
		|| type.getSimpleName().equals("float")
		|| type.getSimpleName().equals("double");
	}
	
	/**
	 * Add AbsoluteValue, Plus, Minus, Increment or Decrement Unary Operator on Numeric Variable 
	 */
	@Override
	public void process(CtVariableRead candidate) {
		thisIndex++;
		
		String expression = "(";
		for(UNARY unary : absSet){
			if(unary.equals(UNARY.INIT)) continue;
			/*expression += PREFIX+thisIndex + ".is(\"" + unary.toString() + "\")?( " + UnaryEquivalent(unary)  + candidate.toString() + ")):";*/
			expression += PREFIX+thisIndex + ".is(" + unary.getClass().getCanonicalName()+'.'+unary.toString()+ ")?( " + UnaryEquivalent(unary)  + candidate.getVariable().getSimpleName() + ")):";
		}
		expression += "(" + candidate.toString() + "))";
		CtCodeSnippetExpression<Boolean> codeSnippet = getFactory().Core()
				.createCodeSnippetExpression();
		codeSnippet.setValue(expression);
		candidate.replace(codeSnippet);
		
		Selector.generateSelector(candidate, UNARY.INIT, thisIndex, absSet, PREFIX);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private String UnaryEquivalent(UNARY value) {
		switch(value) {
		case ABS : return "Math.abs(";
		case MINUS : return "-(";
		case INC : return "(++";
		case DEC : return "(--";
		default : return "(";
		}
	}
}
