package metamutator;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;

/**
 * inserts a mutation hotspot for each binary operator
 */
public class BinaryOperatorMetaMutator extends
		AbstractProcessor<CtBinaryOperator<Boolean>> {

	public static final String SELECTOR_CLASS = Selector.class.getName();

	private static int index = 0;

	private static final EnumSet<BinaryOperatorKind> LOGICAL_OPERATORS = EnumSet
			.of(BinaryOperatorKind.AND, BinaryOperatorKind.OR);
	private static final EnumSet<BinaryOperatorKind> COMPARISON_OPERATORS = EnumSet
			.of(BinaryOperatorKind.EQ, BinaryOperatorKind.GE,
					BinaryOperatorKind.GT, BinaryOperatorKind.LE,
					BinaryOperatorKind.LT, BinaryOperatorKind.NE);
	private static final EnumSet<BinaryOperatorKind> REDUCED_COMPARISON_OPERATORS = EnumSet
			.of(BinaryOperatorKind.EQ, BinaryOperatorKind.NE);

	private Set<CtElement> hostSpots = Sets.newHashSet();

	@Override
	public boolean isToBeProcessed(CtBinaryOperator<Boolean> element) {
		// if (element.getParent(CtAnonymousExecutable.class)!=null) {
		// System.out.println(element.getParent(CtAnonymousExecutable.class));
		// }
		try {
			getTopLevelClass(element);
		} catch (NullPointerException e) {
			return false;
		}

		// not in constructors because we use static fields
		if (element.getParent(CtConstructor.class) != null) {
			return false;
		}

		// not in fields declaration because we use static fields
		if (element.getParent(CtField.class) != null) {
			return false;
		}

		return (LOGICAL_OPERATORS.contains(element.getKind()) || COMPARISON_OPERATORS
				.contains(element.getKind()))
				&& (element.getParent(CtAnonymousExecutable.class) == null) // not
																			// in
																			// static
																			// block
		;
	}

	public void process(CtBinaryOperator<Boolean> binaryOperator) {
		BinaryOperatorKind kind = binaryOperator.getKind();

		if (LOGICAL_OPERATORS.contains(kind)) {
			mutateOperator(binaryOperator, LOGICAL_OPERATORS);
		} else if (COMPARISON_OPERATORS.contains(kind)) {
			if (isNumber(binaryOperator.getLeftHandOperand())
			 || isNumber(binaryOperator.getRightHandOperand()))
			{
				mutateOperator(binaryOperator, COMPARISON_OPERATORS);
			}
			 else {
			 mutateOperator(binaryOperator, REDUCED_COMPARISON_OPERATORS);
			 }
		}
	}

	private boolean isNumber(CtExpression<?> operand) {
		return operand.getType().getSimpleName().equals("int")
			|| operand.getType().getSimpleName().equals("long")
			|| operand.getType().getSimpleName().equals("byte")
			|| operand.getType().getSimpleName().equals("char")
		|| operand.getType().getSimpleName().equals("float")
		|| operand.getType().getSimpleName().equals("double")
		|| Number.class.isAssignableFrom(operand.getType().getActualClass());
	}

/**
	 * Converts "a op b" bean op one of "<", "<=", "==", ">=", "!=" to:
	 *    (  (op(1, 0, "<")  && (a < b))
	 *    || (op(1, 1, "<=") && (a <= b))
	 *    || (op(1, 2, "==") && (a == b))
	 *    || (op(1, 3, ">=") && (a >= b))
	 *    || (op(1, 4, ">")  && (a > b))
	 *    )
	 *
	 * com.medallia.codefixer
	 * @param expression
	 * @param operators
	 */
	private void mutateOperator(final CtBinaryOperator<Boolean> expression,
			EnumSet<BinaryOperatorKind> operators) {
		if (!operators.contains(expression.getKind())) {
			throw new IllegalArgumentException("not consistent");
		}

		if (alreadyInHotsSpot(expression)
				|| expression.toString().contains(".is(\"")) {
			System.out
					.println(String
							.format("Expression '%s' ignored because it is included in previous hot spot",
									expression));
			return;
		}

		int thisIndex = ++index;

		String originalKind = expression.getKind().toString();
		String newExpression = operators
				.stream()
				.map(kind -> {
					expression.setKind(kind);
					return String.format("(_s%s.is(\"%s\") && (%s))",
							thisIndex, kind, expression);
				}).collect(Collectors.joining(" || "));

		CtCodeSnippetExpression<Boolean> codeSnippet = getFactory().Core()
				.createCodeSnippetExpression();
		codeSnippet.setValue('(' + newExpression + ')');

		expression.replace(codeSnippet);
		expression.replace(expression);
		addVariableToClass(expression, originalKind, thisIndex, operators);

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

	private void addVariableToClass(CtBinaryOperator element,
			String originalKind, int index,
			EnumSet<BinaryOperatorKind> operators) {

		long hashCode = (element.getPosition().toString() + element.getParent()
				.toString()).hashCode();

		CtTypeReference<Object> fieldType = getFactory().Type()
				.createTypeParameterReference(SELECTOR_CLASS);
		String selectorId = "_s" + index;

		CtCodeSnippetExpression<Object> codeSnippet = getFactory().Core()
				.createCodeSnippetExpression();

		StringBuilder sb = new StringBuilder(SELECTOR_CLASS + ".of(")
				.append(index);

		sb.append(',');

		// now the options
		sb.append("new String[]{");

		// the original operator, always the first one
		sb.append('"').append(originalKind).append('"');

		// the other alternatives
		for (BinaryOperatorKind kind : operators) {
			if (kind.toString().equals(originalKind)) {
				continue;
			}
			sb.append(',').append('"').append(kind).append('"');
		}

		sb.append("})");

		// adding location
		if (element.getParent(CtType.class).isTopLevel()) {
			sb.append(".in("
					+ element.getParent(CtType.class).getQualifiedName()
					+ ".class)");
		}

		// adding identifier
		sb.append(".id(\"" + selectorId + "\")");

		codeSnippet.setValue(sb.toString());

		CtClass<?> type = getTopLevelClass(element);

		CtField<Object> field = getFactory().Field().create(
				type,
				EnumSet.of(ModifierKind.FINAL, ModifierKind.PRIVATE,
						ModifierKind.STATIC), fieldType, selectorId,
				codeSnippet);

		type.addField(field);
	}

	private CtClass<?> getTopLevelClass(CtElement element) {
		CtClass parent = element.getParent(CtClass.class);
		while (!parent.isTopLevel()) {
			parent = parent.getParent(CtClass.class);
		}
		return parent;
	}
}
