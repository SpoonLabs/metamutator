package metamutator;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtRHSReceiver;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtConstructorCallImpl;

/**
 * inserts a mutation hotspot for each binary operator
 */
public class VariabletoNullMetaMutator extends
		AbstractProcessor<CtStatement> {

	public static final String PREFIX =  "_variableNullHotSpot";
	private static int index = 0;
	private static final int procId = 2;
	
	private static final EnumSet<Null> NULLORNOTNULL = EnumSet
			.of(Null.NO, Null.YES);

	private Set<CtElement> hostSpots = Sets.newHashSet();

	@Override
	public boolean isToBeProcessed(CtStatement element) {
		// if (element.getParent(CtAnonymousExecutable.class)!=null) {
				// System.out.println(element.getParent(CtAnonymousExecutable.class));
				// }
		
				if(!(element instanceof CtRHSReceiver))
					return false;
				try {
					Selector.getTopLevelClass(element);
				} catch (Exception e) {
					return false;
				}

				// not in constructors because we use static fields
				if (element.getParent(CtConstructor.class) != null) {
					return false;
				}
				
				if (((CtRHSReceiver)element).getAssignment() == null)
					return false;
				
				CtTypeReference type = ((CtRHSReceiver)element).getAssignment().getType();
				
				if (type == null)
					return false;
				
				if (element.toString().contains("java.lang.String.format"))
					return false;

				return !((CtRHSReceiver)element).getAssignment().getType().isPrimitive() 
						&& (element.getParent(CtAnonymousExecutable.class) == null);
	}

	public void process(CtStatement assignment) {
		
		mutateOperator(((CtRHSReceiver)assignment).getAssignment(), NULLORNOTNULL);

	}

/**
	 *
	 * com.medallia.codefixer
	 * @param expression
	 * @param operators
	 */
	private void mutateOperator(final CtExpression expression, EnumSet<Null> operators) {
				
		if (alreadyInHotsSpot(expression)
				|| expression.toString().contains(".is(\"")) {
			System.out
			.println(String
					.format("Expression '%s' ignored because it is included in previous hot spot",
							expression));
			return;
		}		

		int thisIndex = ++index;
		
		String actualExpression = expression.toString();
		
		String newExpression = String.format("(%s%s.is(%s))?"+actualExpression+":null",PREFIX,thisIndex,"metamutator.Null.NO");
		
		CtCodeSnippetExpression codeSnippet = getFactory().Core()
				.createCodeSnippetExpression();
		codeSnippet.setValue('(' + newExpression + ')');
		
		expression.replace(codeSnippet);
		expression.replace(expression);
		
		Selector.generateSelector(expression, Null.NO, thisIndex, procId, operators, PREFIX);

		hostSpots.add(expression);

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