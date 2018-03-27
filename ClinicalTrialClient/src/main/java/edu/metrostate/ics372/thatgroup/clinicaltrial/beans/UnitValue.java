/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.io.Serializable;

import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * @author vpalo
 *
 */
public class UnitValue implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5850081309762972449L;
	public static final Number DEFAULT_VALUE = Long.MIN_VALUE;
	public static final String DEFAULT_UNIT = Strings.EMPTY;
	private static final int INDEX_VALUE = 0;
	private static final int INDEX_UNIT = 1;
	public static final String DELIM = " ";
	public static final String VALUE_FORMAT = "%s%s%s";
	private static final String PERIOD = ".";
	private Number value;
	private String unit;

	/**
	 * Initializes a new value with both value and unit values set to nothing
	 */
	public UnitValue() {
		this(DEFAULT_VALUE, DEFAULT_UNIT);
	}
	
	/**
	 * Initializes a new value with systolic and diastolic values set to the specified values.
	 * 
	 * @param value a String in systolic/diastolic format where systolic and diastolic are both
	 * integer values both must be greater than or equal to 0.
	 * @throws NumberFormatException indicates that value is not in systolic/diastolic format or
	 * that systolic and diastolic are not integers
	 * @throws IllegalArgumentException indicates that the string doesn't represent a systolic/diastolic value
	 * or that the values are less than 0.
	 */
	public UnitValue(String value) throws NumberFormatException {
		
		String[] vals = value.split(DELIM);
		
		if (vals.length >= 1) {
			try {
				String strVal = vals[INDEX_VALUE];
				Number val = DEFAULT_VALUE;
				
				if (strVal.contains(PERIOD)) {
					val = Double.parseDouble(strVal);
				} else {
					val = Long.parseLong(strVal);
				}
				
				String strUnit = vals.length > 1 ? vals[INDEX_UNIT] : DEFAULT_UNIT;
				
				this.value = val;
				this.unit = strUnit;
			} catch (NumberFormatException ex) {
				throw ex;
			}
		} else {
			throw new IllegalArgumentException("Invalid formatted string. value must be a string in number value format");
		}
	}
	
	/**
	 * Initializes a new value with systolic and diastolic values set to the specified values.
	 * 
	 * @param systolic the systolic pressure for this value. Must be greater than or equal to 0
	 * @param diastolic the diastolic pressure for this value. Must be greater than or equal to 0
	 * 
	 * @throws IllegalArgumentException indicates that systolic or diastolic are less than 0
	 * that systolic and diastolic are not integers
	 */
	public UnitValue(Number numValue) {
		this(numValue, DEFAULT_UNIT);
	}
	
	/**
	 * Initializes a new value with systolic and diastolic values set to the specified values.
	 * 
	 * @param systolic the systolic pressure for this value. Must be greater than or equal to 0
	 * @param diastolic the diastolic pressure for this value. Must be greater than or equal to 0
	 * 
	 * @throws IllegalArgumentException indicates that systolic or diastolic are less than 0
	 * that systolic and diastolic are not integers
	 */
	public UnitValue(Number numValue, String strUnit) {
		if (numValue == null) {
			throw new IllegalArgumentException("numValue cannot be null.");
		}
		
		this.value = numValue;
		this.unit = strUnit;
	}
	
	/**
	 * @return the number of this value.
	 */
	public Number getNumberValue() {
		return value;
	}
	
	/**
	 * @param value the new number value for this value.
	 */
	public void setNumberValue(Number value) {
		this.value = value;
	}
	
	/**
	 * @return the unit of measurement for this value.
	 */
	public String getUnit() {
		return unit;
	}
	
	/**
	 * @param unit the new unit of measurement for this value.
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(value);
		
		if (unit != null && !unit.trim().isEmpty()) {
			sb.append(DELIM);
			sb.append(unit);
		}
		
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public UnitValue clone() {
		// Clone a BloodPressureValue object
		UnitValue answer;

		try {
			answer = (UnitValue) super.clone();
		} catch (CloneNotSupportedException exception) {
			// This exception should not occur. But if it does, it would
			// indicate a programming
			// error that made super.clone unavailable. The most common cause
			// would be
			// forgetting the "implements Cloneable" clause at the start of the
			// class.
			throw new RuntimeException("UnitValue.clone(): This class does not implement Cloneable.");
		}
		
		return answer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UnitValue)) {
			return false;
		}
		UnitValue other = (UnitValue) obj;
		if (unit == null) {
			if (other.unit != null) {
				return false;
			}
		} else if (!unit.equals(other.unit)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (value.doubleValue() != other.value.doubleValue()) {
			return false;
		}
		return true;
	}
	
}
