package metamutator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;

/**
 * A selector selects one of the variants for a given hot spot
 */
public class Selector<E> implements ISelector<E> {
	private static final Map<String, Selector> selectors = new HashMap<String, Selector>();

	private long locationHashCode;
	private String _identifier;
	private Class locationClass;
	private E[] variants;
	private int chosenVariant = 0; 

	private Selector() {
	}

	public static Selector of(Object ... variants) {
		// defensive copy
		Selector selector = new Selector();
		
		// defensive copy
		selector.variants = variants.clone();
		//selector.locationHashCode = locationHashCode;

		selector._identifier=UUID.randomUUID().toString();
		selectors.put(selector._identifier, selector);

		return selector;
	}

	@Override
	public Selector in(Class locationClass) {
		this.locationClass= locationClass;
		return this;
	}
	
	@Override
	public Selector id(String identifier) {
		selectors.remove(this._identifier);		
		this._identifier= identifier;
		if (selectors.get(identifier)!=null) {
			throw new IllegalArgumentException("identifier already used");
		}
		selectors.put(identifier, this);
		return this;
	}

	@Override
	public void choose(int option) {
		if (option<0 || option>=variants.length) {
			throw new IllegalArgumentException();
		}
		chosenVariant = option;
	}

	@Override
	public boolean is(E variant) {
//		if (System.currentTimeMillis() > stopTime)
//			throw new StopTimeExceededError("In selector " + hotSpot + " with option " + chosenVariant + " checking for " + variant);

		return chosenVariant >= 0 && variants[chosenVariant].equals(variant);
	}

	@Override public String toString() {
		return "chosenVariant " +  chosenVariant +"\n"
			+"variants "+ variants+"\n"
			;
	}

	public static List<Selector> getAllSelectors() {
		return new ArrayList<Selector>(selectors.values());
	}

	public static Selector getSelectorByName(String name) {
		for (Selector s : selectors.values()) {
			if (name.equals(s._identifier)) {
				return s;
		}
		}
		throw new NoSuchElementException("no such selector");
	}
	
	@Override
	public int getAlternativeCount() {
		return variants.length;
	}


	@Override
	public String getIdentifier() {
		return _identifier;
	}

	@Override
	public String getChosenAlternativeDescription() {

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
	public static <E> void generateSelector(CtElement element, E initialChoice, int selectorId, EnumSet<?> possibleChoices, String prefix ) {
		
		Class<?> choiceClass = possibleChoices.iterator().next().getClass();
		
		long hashCode = (element.getPosition().toString() + element.getParent()
		.toString()).hashCode();

		CtTypeReference<Object> fieldType = element.getFactory().Type().createTypeParameterReference(ISelector.class.getCanonicalName());
		
		//doesn't work with spoon for the moment
		//CtTypeReference<Object> genericRefs = element.getFactory().Type().createTypeParameterReference(choiceClass.getCanonicalName());
		//fieldType.addActualTypeArgument(genericRefs);
		
		String selectorFieldName = prefix + selectorId;
		
		CtCodeSnippetExpression<Object> codeSnippet = element.getFactory().Core()
				.createCodeSnippetExpression();
		StringBuilder sb = new StringBuilder(Selector.class.getCanonicalName() + ".of(");
		
		// we disable the ids
		// sb.append(procId+""+selectorId);
		// sb.append(',');

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
	
	@Override
	public Class getLocationClass(){
		if(locationClass == null)
			locationClass = Object.class;
		return locationClass;
	}
		
	@Override
	public E[] getAlternatives() {
		// defensive copy
		return Arrays.copyOf(variants, variants.length);
	}
	
	public static void reset() {
		selectors.clear();
	}
}
