/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.reading;

import java.time.LocalDateTime;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class Temp extends Reading {
	private static final long serialVersionUID = -1060997244536301933L;
	protected double value;

	/**
	 * Initializes an empty temperature reading.
	 */
	public Temp() {
		this(null, null, null, Double.NEGATIVE_INFINITY);
	}

	/**
	 * Initializes a temperature reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. Must be a Number
	 */
	public Temp(String patientId, String id, LocalDateTime date, Object value) {
		super(patientId, id, date, value);
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (value instanceof Number == false && value instanceof String == false) {
			throw new IllegalArgumentException("value must be a number.");
		}
		
		if (value instanceof Number) {
			Number num = (Number) value;
			
			this.value = num.doubleValue();
		} else if (value instanceof String) {
			this.value = Double.parseDouble((String)value);
		}
	}
}
