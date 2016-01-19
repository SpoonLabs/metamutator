package metamutator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtGenericElementReference;

import spoon.reflect.reference.CtTypeReference;

/**
 * A selector selects one of the variants for a given hot spot
 */
public class Selector<E> {
	private static final Map<Long, Selector> selectors = new HashMap<Long, Selector>();

	private long hotSpot;
	private long locationHashCode;
	private String identifier;
	private Class locationClass;
	private E[] variants;
	private int chosenVariant = 0; 

	private Selector() {
	}

	public static Selector of(long hotSpot, Object ... variants) {
		// defensive copy
		Selector selector = new Selector();
		selector.hotSpot = hotSpot;
		
		// defensive copy
		selector.variants = variants.clone();
		//selector.locationHashCode = locationHashCode;

		selectors.put(hotSpot, selector);

		return selector;
	}

	public Selector in(Class locationClass) {
		this.locationClass= locationClass;
		return this;
	}
	
	public Selector id(String identifier) {
		this.identifier= identifier;
		return this;
	}

	public void choose(int option) {
		if (option<0 || option>=variants.length) {
			throw new IllegalArgumentException();
		}
		chosenVariant = option;
	}

	public boolean is(E variant) {
//		if (System.currentTimeMillis() > stopTime)
//			throw new StopTimeExceededError("In selector " + hotSpot + " with option " + chosenVariant + " checking for " + variant);

		return chosenVariant >= 0 && variants[chosenVariant].equals(variant);
	}

	@Override public String toString() {
		return "chosenVariant " +  chosenVariant +"\n"
			+"hotSpot "+ hotSpot+"\n"
			+"variants "+ variants+"\n"
			;
	}

	public static List<Selector> getAllSelectors() {
		return new ArrayList<Selector>(selectors.values());
	}

	public static Selector getSelectorByName(String name) {
		for (Selector s : selectors.values()) {
			if (name.equals(s.identifier)) {
				return s;
		}
		}
		throw new NoSuchElementException("no such selector");
	}
	
	public int getOptionCount() {
		return variants.length;
	}


	public String getIdentifier() {
		return "id:"+hotSpot+",h:"+locationHashCode;
	}

	public String getChosenOptionDescription() {

		return getIdentifier()+",v:"+variants[chosenVariant];
//		if ((chosenVariant >= 0) && (chosenVariant < variants.length)) {
//			return variants[chosenVariant];
//		} else {
//			return "n/a";
//		}
	}

	public static class StopTimeExceededError extends RuntimeException {   // TODO THis can be cached !!

		public StopTimeExceededError(String message) {
			super(message);
		}
	}
	/** Generates a field containing a new selector for this element and adds it to the current class 
	 * 
	 */
	public static <E> void generateSelector(CtElement element, E initialChoice, int selectorId, int procId, EnumSet<?> possibleChoices, String prefix ) {
		
		Class<?> choiceClass = possibleChoices.iterator().next().getClass();
		
		long hashCode = (element.getPosition().toString() + element.getParent()
		.toString()).hashCode();

		CtTypeReference<Object> fieldType = element.getFactory().Type().createTypeParameterReference(Selector.class.getCanonicalName());
		
		//doesn't work with spoon for the moment
		//CtTypeReference<Object> genericRefs = element.getFactory().Type().createTypeParameterReference(choiceClass.getCanonicalName());
		//fieldType.addActualTypeArgument(genericRefs);
		
		String selectorFieldName = prefix + selectorId;
		
		CtCodeSnippetExpression<Object> codeSnippet = element.getFactory().Core()
				.createCodeSnippetExpression();
		StringBuilder sb = new StringBuilder(Selector.class.getCanonicalName() + ".of(")
				.append(procId+""+selectorId);
		
		sb.append(',');

		// now the options
		sb.append("new "+choiceClass.getCanonicalName()+"[]{");
		
		// the original operator, always the first one
		sb.append(initialChoice.getClass().getCanonicalName()+"."+initialChoice.toString());
		
		// the other alternatives
		for (Object choose : possibleChoices) {
			if (choose.equals(initialChoice)) {
				continue;
			}
			sb.append(',').append(choose.getClass().getCanonicalName()+"."+choose.toString());
		}
		
		sb.append("})");
		
		// adding location
		if (element.getParent(CtType.class).isTopLevel()) {
			sb.append(".in("
					+ element.getParent(CtType.class).getQualifiedName()
					+ ".class)");
		}
		
		// adding identifier
		sb.append(".id(\"" + selectorFieldName + "\")");
		
		codeSnippet.setValue(sb.toString());
		
		CtClass<?> type = getTopLevelClass(element);
		
		CtField<Object> field = element.getFactory().Field().create(
				type,
				EnumSet.of(ModifierKind.FINAL, ModifierKind.PRIVATE,
						ModifierKind.STATIC), fieldType, selectorFieldName,
				codeSnippet);
		
		type.addField(field);
	}
	
	public static CtClass<?> getTopLevelClass(CtElement element) {
		CtClass parent = element.getParent(CtClass.class);
		while (!parent.isTopLevel()) {
			parent = parent.getParent(CtClass.class);
		}
		return parent;
	}
	
	public Class getLocationClass(){
		if(locationClass == null)
			locationClass = Object.class;
		return locationClass;
	}
	
	public String getId() {
		return this.identifier;
	}
	
	public E[] getOption() {
		return variants;
	}
	
	public static void reset() {
		selectors.clear();
	}
}
