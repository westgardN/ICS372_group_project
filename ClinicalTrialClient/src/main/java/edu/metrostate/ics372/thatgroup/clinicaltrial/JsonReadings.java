/**
 * File: JsonReadings.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ReadingFactory;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility collection class that assists in the automation of importing and exporting of Reading
 * objects to and from a JSON file. Used by the JsonProcessor.
 * 
 * @author That Group
 */
public class JsonReadings {
	private List<JsonReading> patient_readings;
	
	/**
	 * Returns the imported readings as a List of Reading object references.
	 * Called by the JsonProcessor after importing a JSON file to return.
	 * 
	 * @return the imported readings as a List of Reading object references.
	 */
	public List<Reading> getPatientReadings() {
		List<Reading> answer = new LinkedList<>();
		
		for (JsonReading reading : patient_readings) {
			answer.add(reading.toReading());
		}
		
		return answer;
	}
	
	/**
	 * Used by the JsonProcessor to preparing the Reading objects for exporting to a JSON file.
	 * 
	 * @param readings The list of Reading object references to export.
	 */
	public void setPatientReadings(List<Reading> readings) {
		patient_readings = new LinkedList<>();
		
		for (Reading reading : readings) {
			patient_readings.add(new JsonReading(reading));
		}
	}

	/**
	 * Used internally to facilitate automatic conversion from JSON to a Java object without having to muddy up the
	 * domain objects with JSON specific information.
	 * 
	 * @author That Group
	 *
	 */
	private class JsonReading {
		private String patient_id;
		private String reading_type;
		private String reading_id;
		private Object reading_value;
		private long reading_date;
		
		/**
		 * A convenience constructor to convert a Reading object reference in to a 
		 * JsonReading that is suitable for exporting to a JSON file.
		 * 
		 * @param patient_id
		 * @param reading_type
		 * @param reading_id
		 * @param reading_value
		 * @param reading_date
		 */
		public JsonReading(Reading reading) {
			this.patient_id = reading.getPatientId();
			this.reading_type = ReadingFactory.getReadingType(reading);
			this.reading_id = reading.getId();
			this.reading_value = reading.getValue() != null ? reading.getValue() instanceof Number ? reading.getValue() : reading.getValue().toString() : "";
			this.reading_date = reading.getDate() != null ? reading.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : 0;
		}

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
		public String getReadingType() {
			return reading_type;
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
			return reading_value;
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
		
		/**
		 * Used to convert this JsonReading in to a Reading object reference.
		 * 
		 * @return this reading converted to the correct Reading object reference.
		 */
		public Reading toReading() {
			Reading answer = ReadingFactory.getReading(getReadingType());
			answer.setPatientId(getPatientId());
			answer.setId(getReadingId());
			answer.setDate(getReadingDate());
			answer.setValue(getReadingValue());
			return answer;
		}
	}
}
