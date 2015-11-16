package metamutator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A selector selects one of the variants for a given hot spot
 */
public class Selector {
	private static final Map<Long, Selector> selectors = new HashMap<Long, Selector>();

	private long hotSpot;
	private long locationHashCode;
	private String identifier;
	private Class locationClass;
	private String[] variants;
	private int chosenVariant = 0; 
	private long stopTime;

	private Selector() {
	}

	public static Selector of(long hotSpot, String ... variants) {
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

	public boolean is(String variant) {
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

	public static Selector getSelectorsByName(String name) {
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

	public void setStopTime(long stopTime) {

		this.stopTime = stopTime;
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
}
