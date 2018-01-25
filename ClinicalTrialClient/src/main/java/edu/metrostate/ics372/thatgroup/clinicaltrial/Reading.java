/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class Reading implements Serializable {
	private static final long serialVersionUID = 8166141304679594433L;
	private String patientId;
	private String id;
	private ReadingType type;
	private LocalDateTime date;
	private Object value;
	
	/**
	 * Initializes an empty reading.
	 */
	public Reading() {
		
	}
	
	/**
	 * Initializes a reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param type The type of reading this is.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. 
	 */
	public Reading(String patientId, String id, ReadingType type, LocalDateTime date, Object value) {
		this.patientId = patientId;
		this.id = id;
		this.type = type;
		this.date = date;
		this.value = value;
	}
}
