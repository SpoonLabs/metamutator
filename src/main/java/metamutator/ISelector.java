package metamutator;

/** represents a meta-mutation point, for which one can select alternatives (one alternative is a mutation) */
public interface ISelector<E> {

	/** activate the i-th alternative (by convention #0 is the original one) */
	public void choose(int option);

	/** stats that the selector is in class */
	public Selector in(Class locationClass);

	/** sets the id */
	public Selector id(String identifier);

	/** returns true is the currently chosen option is "variant" */
	public boolean is(E variant);

	/** returns the number of alternatives */
	public int getAlternativeCount();

	/** returns the identifier (setted is #id(String)) */
	public String getIdentifier();

	/** returns the readable name of the current alternative */
	public String getChosenAlternativeDescription();

	/** returns the class where the selector is */
	public Class getLocationClass();

	/** returns the set of opssible alternatives */
	public E[] getAlternatives();

}