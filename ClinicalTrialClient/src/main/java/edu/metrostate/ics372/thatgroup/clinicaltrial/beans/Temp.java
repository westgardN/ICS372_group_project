/**
 * File: Temp.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * A Temp reading consists of a single double as its value
 * 
 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading
 * 
 * @author That Group
 *
 */
public class Temp extends Reading {
	private static final long serialVersionUID = -1060997244536301933L;
	protected UnitValue value;

	/**
	 * Initializes an empty temperature reading with the temperature initialized
	 * to Double.NEGATIVE_INFINITY.
	 */
	public Temp() {
		this(null, null, null, Double.NEGATIVE_INFINITY, null);
	}

	/**
	 * Initializes a temperature reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. Must be a Number
	 */
	public Temp(String patientId, String id, LocalDateTime date, Object value, String clinicId) {
		super(patientId, id, date, value, clinicId);
	}

	/**
	 * Returns the temperature of this reading as a unit value.
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/**
	 * @throws IllegalArgumentException indicates that value is not a double value greater than or equal to 0
	 */
	@Override
	public void setValue(Object value) {
		if (value != null) {
			if (value instanceof UnitValue == false && value instanceof Number == false && value instanceof String == false) {
				throw new IllegalArgumentException("value must be a number.");
			}
			
			if (value instanceof UnitValue) {
				this.value = ((UnitValue)value).clone();
			} else if (value instanceof String) {
				this.value = new UnitValue((String) value);
			} else if (value instanceof Number) {
				this.value = new UnitValue((Number) value);
			}
		} else {
			this.value = new UnitValue(Double.NEGATIVE_INFINITY);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Temp taken");
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		if (date != null) {
			String formattedDateTime = date.format(formatter);
			builder.append(" on ");
			builder.append(formattedDateTime);
		}
		builder.append(" is: ");
		builder.append(getValue());
		return builder.toString();
	}
}
