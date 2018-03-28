/**
 * File: UnitValue.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.io.Serializable;

import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The UnitValue class represents a Long or Double value that includes
 * a unit of measurement.
 * 
 * @author That Group
 *
 */
public class UnitValue implements Serializable, Cloneable {
	/**
	 * The DEFAULT_VALUE for a UnitValue is Long.MIN_VALUE
	 */
	public static final Number DEFAULT_VALUE = Long.MIN_VALUE;

	/**
	 * The DEFAULT_UNIT for a UnitValue is the empty string
	 */
	public static final String DEFAULT_UNIT = Strings.EMPTY;
	
	/**
	 * The DELIM is the delimiter used when splitting a String
	 * in to its value and unit. The delimiter is a space and so
	 * the unit cannot contain any space characters.
	 */
	public static final String DELIM = " ";
	
	/**
	 * The format string used with String.format(). All three parameters
	 * for the format string are string values themselves.
	 */
	public static final String VALUE_FORMAT = "%s%s%s";
	
	private static final long serialVersionUID = 5850081309762972449L;
	private static final int INDEX_VALUE = 0;
	private static final int INDEX_UNIT = 1;
	private static final String PERIOD = ".";
	private Number value;
	private String unit;

	/**
	 * Initializes a new value with both value and unit values set to their defaults
	 */
	public UnitValue() {
		this(DEFAULT_VALUE, DEFAULT_UNIT);
	}
	
	/**
	 * Initializes a new UnitValue with the specified value and option unit as specified
	 * in the string. The value string needs to be in the Value + DELIM + Unit format. 
	 * 
	 * @param value a String in number unit format where number is a Long or Double and
	 * unit is an optional string that denotes the measurement.
	 * 
	 * @throws NumberFormatException indicates that value is not a Long or Double
	 * 
	 * @throws IllegalArgumentException indicates that the string doesn't represent a
	 * number unit value.
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
			throw new IllegalArgumentException(Strings.ERR_UNIT_VALUE_INVALID_FORMAT_STRING_VALUE);
		}
	}
	
	/**
	 * Initializes a new UnitValue with the specified Long or Double value and uses the
	 * DEFAULT_UNIT as the unit of measurement.
	 * 
	 * @param numValue the numeric value to use. Must be an instance of a Long or Double.
	 * 
	 * @throws IllegalArgumentException indicates that numValue is null.
	 */
	public UnitValue(Number numValue) {
		this(numValue, DEFAULT_UNIT);
	}
	
	/**
	 * Initializes a new UnitValue with the specified Long or Double value and uses the
	 * specified unit as the unit of measurement.
	 * 
	 * @param numValue the numeric value to use. Must be an instance of a Long or Double.
	 * @param strUnit the unit of measurement to use for this value, which may be null.
	 * 
	 * @throws IllegalArgumentException indicates that numValue is null.
	 */
	public UnitValue(Number numValue, String strUnit) {
		if (numValue == null) {
			throw new IllegalArgumentException(Strings.ERR_UNIT_VALUE_NULL_VALUE);
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
	 * @param value the new number value for this value, which
	 * must be a Double or Long
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
