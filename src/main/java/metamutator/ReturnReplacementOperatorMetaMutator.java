package metamutator;

import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.Sets;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;

/** metamutates return statements that returns Boolean */
public class ReturnReplacementOperatorMetaMutator extends AbstractProcessor<CtReturn> {

	public static final String PREFIX = "_returnReplacementOperatorHotSpot";
	private static int index = 0;	
	private static final EnumSet<RETURN_REPLACEMENT_INT> int_replacement = EnumSet.of(RETURN_REPLACEMENT_INT.INT_MIN, RETURN_REPLACEMENT_INT.INT_MAX, RETURN_REPLACEMENT_INT.ZERO);
	private static final EnumSet<RETURN_REPLACEMENT_OBJECT> object_replacement = EnumSet.of(RETURN_REPLACEMENT_OBJECT.NULL);
	private static final int procId = 6;

	
	public enum RETURN_REPLACEMENT_INT {
		INIT, // INIT VALUE
		INT_MIN, // CHANGE BY MIN INT
		INT_MAX, // CHANGE BY MAX INT,
		ZERO // CHANGE BY 0
	};
	
	public enum RETURN_REPLACEMENT_OBJECT {
		INIT, // INIT VALUE
		NULL
	}
	
	
	private String permutations(RETURN_REPLACEMENT_INT value) {
		switch(value) {
			case INT_MIN : return Integer.toString(Integer.MIN_VALUE + 1);
			case INT_MAX : return Integer.toString(Integer.MAX_VALUE - 1);
			case ZERO : return Integer.toString(0);
			default : return "";
		}
	}
	
	private Set<CtElement> hostSpots = Sets.newHashSet();

	private boolean isInteger(CtExpression<?> expression) {
		return expression.getType().getSimpleName().equals("int");
	}

	private boolean isBoolean(CtExpression<?> expression) {
		return expression.getType().getSimpleName().equals("Boolean");
	}
	
	@Override
	public boolean isToBeProcessed(CtReturn element) {
		try {
			Selector.getTopLevelClass(element);
		} catch (NullPointerException e) {
			return false;
		}
		return (element.getReturnedExpression() != null && !element.getReturnedExpression().getType().getSimpleName().equals("Boolean"));
	}
	
	
	public void process(CtReturn returnStatement) {
		CtExpression returnValue = returnStatement.getReturnedExpression();
		// test if the returned value is not null
		if(returnValue != null){
						
			index++;
			String expression = "(";
			int cpt = 0;
			if (isInteger(returnValue)) {
				for(RETURN_REPLACEMENT_INT replacement : int_replacement){
					if(cpt < RETURN_REPLACEMENT_INT.values().length){
						if(replacement.equals(RETURN_REPLACEMENT_INT.INIT)) continue;
						if(returnValue.getTypeCasts().size() != 0){
							expression += "(" + PREFIX + index + ".is(" + replacement.getClass().getCanonicalName()+"."+replacement.toString() + ")) ? (("+ returnValue.getTypeCasts().get(0) +")( " + permutations(replacement) + " ))";
							expression += " : ";
						}else{
							expression += "(" + PREFIX + index + ".is(" + replacement.getClass().getCanonicalName()+"."+replacement.toString() + ")) ? ( " + permutations(replacement) + " )";
							expression += " : ";
						}
					}else{
						expression += " (" + permutations(replacement) + ")";
					}
				}	
				
				expression += "(" + returnValue + "))";
				CtCodeSnippetExpression<Boolean> codeSnippet = getFactory().Core()
						.createCodeSnippetExpression();
				codeSnippet.setValue(expression);
				
				CtReturn newReturn = getFactory().Core().createReturn();
				newReturn.setReturnedExpression(codeSnippet);
				returnStatement.replace(newReturn);
				Selector.generateSelector(returnStatement, RETURN_REPLACEMENT_INT.INIT, index ,int_replacement ,PREFIX);
				
				hostSpots.add(returnStatement);
				
			}else if (!isBoolean(returnValue)){
				expression += "(" + PREFIX + index + ".is(" + RETURN_REPLACEMENT_OBJECT.NULL.getClass().getCanonicalName()+"."+RETURN_REPLACEMENT_OBJECT.NULL.toString() + ")) ? ( null )";
				expression += " : ";
				expression += "(" + returnValue + "))";
				CtCodeSnippetExpression<Boolean> codeSnippet = getFactory().Core()
						.createCodeSnippetExpression();
				codeSnippet.setValue(expression);
				
				CtReturn newReturn = getFactory().Core().createReturn();
				newReturn.setReturnedExpression(codeSnippet);
				returnStatement.replace(newReturn);
				Selector.generateSelector(returnStatement, RETURN_REPLACEMENT_OBJECT.INIT, index ,object_replacement ,PREFIX);
				hostSpots.add(returnStatement);
			}
		}
	}
	
	
	
	/**
	 * Check if this sub expression was already inside an uppermost expression
	 * that was processed has a hot spot. This version does not allowed
	 * conflicting hot spots
	 * 
	 * @param element
	 *            the current expression to test
	 * @return true if this expression is descendant of an already processed
	 *         expression
	 */
	private boolean alreadyInHotsSpot(CtElement element) {
		CtElement parent = element.getParent();
		while (!isTopLevel(parent) && parent != null) {
			if (hostSpots.contains(parent))
				return true;

			parent = parent.getParent();
		}

		return false;
	}

	private boolean isTopLevel(CtElement parent) {
		return parent instanceof CtClass && ((CtClass) parent).isTopLevel();
	}
}