/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.reading;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class ReadingFactory {

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
