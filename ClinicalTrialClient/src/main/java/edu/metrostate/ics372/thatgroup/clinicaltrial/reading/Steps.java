/**
 * File: Steps.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.reading;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * A Step reading consists of a single integer as its value
 * 
 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading
 * 
 * @author That Group
 *
 */
public class Steps extends Reading {
	private static final long serialVersionUID = 125481244831517121L;
	protected int value;

	/**
	 * Initializes an empty steps reading with the number of steps set to Integer.MIN_VALUE.
	 */
	public Steps() {
		this(null, null, null, Integer.MIN_VALUE, null);
	}

	/**
	 * Initializes a steps reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. Must be a Number
	 */
	public Steps(String patientId, String id, LocalDateTime date, Object value, String clinicId) {
		super(patientId, id, date, value, clinicId);
	}

	/**
	 * Returns the number of steps for this reading as an integer
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value of this reading. Must be an integer value greater than or equal to 0
	 * @throws IllegalArgumentException indicates that value is not an integer value greater than or equal to 0
	 */
	@Override
	public void setValue(Object value) {
		if (value != null) {
			if (value instanceof Number == false && value instanceof String == false) {
				throw new IllegalArgumentException("value must be a number.");
			}
			
			if (value instanceof Number) {
				Number num = (Number) value;
				
				if (num.intValue() < 0 && num.intValue() != Integer.MIN_VALUE) {
					throw new IllegalArgumentException("value must be greater than or equal to zero.");
				}
				
				this.value = num.intValue();
			} else if (value instanceof String) {
				this.value = Integer.parseInt((String)value);
			}
		} else {
			this.value = Integer.MIN_VALUE;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Steps taken ");
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		String formattedDateTime = date.format(formatter);
		builder.append(formattedDateTime);
		builder.append(" is: ");
		builder.append(getValue());
		return builder.toString();
	}
}
