/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.reading;

import java.time.LocalDateTime;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class Weight extends Reading {
	private static final long serialVersionUID = 5373498553571356119L;
	private int value;

	/**
	 * Initializes an empty weight reading.
	 */
	public Weight() {
		this(null, null, null, Integer.MIN_VALUE);
	}
	
	/**
	 * Initializes a weight reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. Must be a Number
	 */
	public Weight(String patientId, String id, LocalDateTime date, Object value) {
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
			
			if (num.intValue() < 0) {
				throw new IllegalArgumentException("value must be greater than or equal to zero.");
			}
			
			this.value = num.intValue();
		} else if (value instanceof String) {
			this.value = Integer.parseInt((String)value);
		}
	}
}
