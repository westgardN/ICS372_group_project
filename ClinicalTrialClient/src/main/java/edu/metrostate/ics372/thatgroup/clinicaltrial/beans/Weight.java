/**
 * File: Weight.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * A Weight reading consists of a single integer as its value
 * along with a unit of measurement
 * 
 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading
 * 
 * @author That Group
 *
 */
public class Weight extends Reading {
	private static final String MSG_WEIGHT_TAKEN = "Weight taken";
	private static final String MSG_WEIGHT_IS = " is: ";
	private static final String MSG_WEIGHT_ON = " on ";
	private static final long serialVersionUID = 5373498553571356119L;
	private UnitValue value;

	/**
	 * Initializes an empty weight reading with the weight set to Integer.MIN_VALUE
	 * and using the default unit of measurement.
	 */
	public Weight() {
		this(null, null, null, Long.MIN_VALUE, null);
	}
	
	/**
	 * Initializes a weight reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. Must be a Number
	 * @param clinicId The ID of the clinic associated with this object.
	 */
	public Weight(String patientId, String id, LocalDateTime date, Object value, String clinicId) {
		super(patientId, id, date, value, clinicId);
	}
	
	/**
	 * Sets the value of this reading. The value can either be a String in Temp Unit format, it can be just 
	 * be a Long if no unit of measurement is needed, or it can be an instance of an UnitValue.
	 * 
	 * @param value the new value for this reading. If value is null, then the value of this unit value is
	 * set to Long.MIN_VALUE.
	 * 
	 * @throws IllegalArgumentException indicates that value is not a UnitValue, String, or Number
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/**
	 * @param value sets the weight of this reading to the specified value.
	 * @throws IllegalArgumentException indicates that value is not a number or value
	 * is less than 0.
	 */
	@Override
	public void setValue(Object value) {
		UnitValue oldValue = this.value;
		
		if (value != null) {
			if (value instanceof UnitValue == false && value instanceof Number == false && value instanceof String == false) {
				throw new IllegalArgumentException(Strings.ERR_WEIGHT_NAN);
			}
			
			UnitValue newValue = null;
			
			if (value instanceof UnitValue) {
				newValue = ((UnitValue)value).clone();
			} else if (value instanceof String) {
				newValue = new UnitValue((String) value);
			} else if (value instanceof Number) {
				newValue = new UnitValue((Number) value);
			}
			
			if (!Objects.equals(oldValue, newValue)) {
				this.value = newValue;
				getPcs().firePropertyChange(PROP_VALUE, oldValue, this.value);
			}
		} else {
			UnitValue newValue = new UnitValue(Long.MIN_VALUE);
			if (!Objects.equals(oldValue, newValue)) {
				this.value = newValue; 
				getPcs().firePropertyChange(PROP_VALUE, oldValue, this.value);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(MSG_WEIGHT_TAKEN);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		if (date != null) {
			String formattedDateTime = date.format(formatter);
			builder.append(MSG_WEIGHT_ON);
			builder.append(formattedDateTime);
		}
		builder.append(" at clinic with id: " + clinicId + " for patient with id: " + patientId);
		builder.append(MSG_WEIGHT_IS);
		builder.append(getValue());
		return builder.toString();
	}
}
