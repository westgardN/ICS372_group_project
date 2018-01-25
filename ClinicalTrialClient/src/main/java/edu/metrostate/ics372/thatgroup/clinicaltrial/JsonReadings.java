/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class JsonReadings {
	private List<JsonReading> patient_readings;
	
	/**
	 * Returns the imported readings as a List.
	 * 
	 * @return the imported readings as a List.
	 */
	public List<Reading> getPatientReadings() {
		List<Reading> answer = new LinkedList<>();
		
		for (JsonReading reading : patient_readings) {
			answer.add(reading.toReading());
		}
		
		return answer;
	}

	private class JsonReading {
		private String patient_id;
		private String reading_type;
		private String reading_id;
		private String reading_value;
		private long reading_date;
		
		/**
		 * Returns the ID of the patient this reading belongs to
		 * @return the ID of the patient this reading belongs to
		 */
		public String getPatientId() {
			return patient_id;
		}
		
		/**
		 * Returns the type of reading this reading is for.
		 * @return the type of reading this reading is for.
		 */
		public ReadingType getReadingType() {
			ReadingType answer = ReadingType.WEIGHT; // Default to a Weight type if none is specied.
			
			switch (reading_type) {
				case "steps":
					answer = ReadingType.STEPS;
					break;
				case "temp":
					answer = ReadingType.TEMP;
					break;
				case "blood_press":
					answer = ReadingType.BLOOD_PRESSURE;
					break;
			}
			return answer;
		}
		
		/**
		 * Returns the ID of this reading
		 * @return the ID of this reading
		 */
		public String getReadingId() {
			return reading_id;
		}
		
		/**
		 * Returns the value of this reading as a String.
		 * @return the value of this reading as a String.
		 */
		public Object getReadingValue() {
			Object answer = reading_value;
			
			if (getReadingType() == ReadingType.TEMP || getReadingType() == ReadingType.WEIGHT) {
				try {
					answer = Double.parseDouble(reading_value);
				} catch (NumberFormatException ex) {
					
				}
			} else if (getReadingType() == ReadingType.STEPS) {
				try {
					answer = Integer.parseInt(reading_value);
				} catch (NumberFormatException ex) {
					
				}
			}
			
			return answer;
		}
		
		/**
		 * Returns the date and time this reading was taken as a LocalDateTime.
		 * @return the date and time this reading was taken as a LocalDateTime.
		 */
		public LocalDateTime getReadingDate() {
			LocalDateTime answer = null;
			
			try {
				answer = Instant.ofEpochMilli(reading_date).atZone(ZoneId.systemDefault()).toLocalDateTime();
			} catch (DateTimeException ex) {
				
			}
			
			return answer; 
		}
		
		public Reading toReading() {
			return new Reading(getPatientId(), getReadingId(), getReadingType(), getReadingDate(), getReadingValue());
		}
	}
}
