/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.reading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ReadingFactory is utility Factory class for creating Reading objects of various types.
 * 
 * You can use ReadingFactory.
 * @author That Group
 *
 */
public class ReadingFactory {
	private static List<String> readingTypes;
	
	{
		readingTypes = new ArrayList<>();
		
		readingTypes.add("blood_press");
		readingTypes.add("steps");
		readingTypes.add("temp");
		readingTypes.add("weight");
	}
	
	/**
	 * Returns an unmodifiable list of the types of readings
	 * that can be created by this factory.
	 * 
	 * @return an unmodifiable list of the types of readings this factory can create.
	 */
	public static List<String> getReadingTypes() {
		return Collections.unmodifiableList(readingTypes);
	}
	
	/**
	 * Returns a new reading based on the type specified. If the type is
	 * unknown, an IllegalArgumentException is thrown.
	 * 
	 * @param type the type of Reading to create. type must be one of the types
	 * found in the list returned from getReadingTypes
	 * 
	 * @return a new Reading of the specified type
	 * @throws IllegalArgumentException indicates an unknown type was specified
	 */
	public static Reading getReading(String type) {
		Reading answer = null;
		
		switch(type.toLowerCase()) {
		case "blood_press":
			answer = new BloodPressure();
			break;
		case "steps":
			answer = new Steps();
			break;
		case "temp":
			answer = new Temp();
			break;
		case "weight":
			answer = new Weight();
			break;
		default:
			throw new IllegalArgumentException("Unknown reading type: " + type);
		}
		
		return answer;
	}

	/**
	 * Returns the type of Reading the specified reading is a type of.
	 * 
	 * @param reading the reading to determine the type of. Cannot be null.
	 * 
	 * @return the reading type returned will be one of the values in the list
	 * of supported reading types.
	 * @throws IllegalArgumentException indicates that reading is null.
	 */
	public static String getReadingType(Reading reading) {
		String answer = null;
		
		if (reading instanceof BloodPressure) {
			answer = "blood_press";
		} else if (reading instanceof Steps) {
			answer = "steps";
		} else if (reading instanceof Temp) {
			answer = "temp";
		} else if (reading instanceof Weight) {
			answer = "weight";
		} else if (reading != null) {
			answer = reading.getClass().getName();
		} else {
			throw new IllegalArgumentException("reading cannot be null.");
		}
		
		return answer;
	}

	/**
	 * Returns a string representation of the type for the specified reading that is suitable for use in a UI
	 * 
	 * @param reading the reading to get the type of. Cannot be null.
	 * 
	 * @return a string representation of the type for the specified reading that is suitable for use in a UI
	 * @throws IllegalArgumentException indicates that reading is null.
	 */
	public static String getPrettyReadingType(Reading reading) {
		String answer = null;
		
		if (reading instanceof BloodPressure) {
			answer = "Blood Pressure";
		} else if (reading instanceof Steps) {
			answer = "Steps";
		} else if (reading instanceof Temp) {
			answer = "Temperature";
		} else if (reading instanceof Weight) {
			answer = "Weight";
		} else if (reading != null) {
			answer = reading.getClass().getName();
		} else {
			throw new IllegalArgumentException("reading cannot be null.");
		}
		
		return answer;
	}
}
