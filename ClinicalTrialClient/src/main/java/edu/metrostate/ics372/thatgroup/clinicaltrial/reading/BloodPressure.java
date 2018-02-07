/**
 * File BloodPressure.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.reading;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * A Blood Pressure specific reading for collecting systolic and diastolic pressure
 * values.
 * 
 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading
 * 
 * @author That Group
 *
 */
public class BloodPressure extends Reading {
	private static final long serialVersionUID = -7058161175665447094L;
	private BloodPressureValue value;
	
	/**
	 * Initializes a new empty reading.
	 */
	public BloodPressure() {
		this(null, null, null, null);
	}

	/**
	 * Initializes a new reading with the specified values
	 * 
	 * @param patientId the id of the patient this reading is associated with
	 * @param id the id of this reading. If adding to a Set, this needs to be unique
	 * @param date the date and time the reading was taken
	 * @param value the blood pressure reading in this string format: sys/dia
	 */
	public BloodPressure(String patientId, String id, LocalDateTime date, Object value) {
		super(patientId, id, date, value);
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value of this reading. The value can either be a String in systolic/diastolic format
	 * and both systolic and diastolic are integer values or the value can be an instance
	 * of a BloodPressureValue. 
	 * 
	 * @param value the new value for this reading. Cannot be null.
	 * 
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (value != null) {
			if (value instanceof BloodPressureValue == false && value instanceof String == false) {
				throw new IllegalArgumentException("value must be a string in systolic/diastolic format or it must be a BloodPressureValue");
			}
			
			if (value instanceof BloodPressureValue) {
				this.value = ((BloodPressureValue)value).clone();
			} else if (value instanceof String) {
				this.value = new BloodPressureValue((String) value);
			} else {
				throw new IllegalArgumentException("Unknown bloodpressure value.");
			}
		} else {
			this.value = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Blood Pressure taken ");
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		String formattedDateTime = date.format(formatter);
		builder.append(formattedDateTime);
		builder.append(" is: ");
		builder.append(((BloodPressureValue)getValue()).getSystolic());
		builder.append(" / ");
		builder.append(((BloodPressureValue)getValue()).getDiastolic());
		return builder.toString();
	}
	
	/**
	 * A simple bean to represent a blood pressure reading of systolic and diastolic values.
	 * 
	 * @author That Group
	 *
	 */
	public class BloodPressureValue implements Serializable {
		private static final int INDEX_DIASTOLIC = 1;
		private static final int INDEX_SYSTOLIC = 0;
		private static final long serialVersionUID = -6450411049437598250L;
		private static final String DELIM = "/";
		private int systolic;
		private int diastolic;
		
		/**
		 * Initializes a new value with both systolic and diastolic values set to Integer.MIN_VALUE
		 */
		public BloodPressureValue() {
			this(Integer.MIN_VALUE, Integer.MIN_VALUE);
		}
		
		/**
		 * Initializes a new value with systolic and diastolic values set to the specified values.
		 * 
		 * @param value a String in systolic/diastolic format where systolic and diastolic are both
		 * integer values both must be greater than or equal to 0.
		 * @throws NumberFormatException indicates that value is not in systolic/diastolic format or
		 * that systolic and diastolic are not integers
		 * @throw IllegalArgumentException indicates that the string doesn't represent a systolic/diastolic value
		 * or that the values are less than 0.
		 */
		public BloodPressureValue(String value) throws NumberFormatException {
			
			String[] vals = value.split(DELIM);
			
			if (vals.length == 2) {
				try {
					int s = Integer.parseInt(vals[INDEX_SYSTOLIC]);
					int d = Integer.parseInt(vals[INDEX_DIASTOLIC]);
					
					if (s < 0 || d < 0) {
						throw new IllegalArgumentException("systolic and diastolic values must be greater than or equal to zero.");
					}
					systolic = s;
					diastolic = d;
				} catch (NumberFormatException ex) {
					throw ex;
				}
			} else {
				throw new IllegalArgumentException("Invalid formatted string. value must be a value must be a string in systolic/diastolic format");
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
		public BloodPressureValue(int systolic, int diastolic) {
			if (systolic < 0 || diastolic < 0) {
				throw new IllegalArgumentException("systolic and diastolic values must be greater than or equal to zero.");
			}
			
			this.systolic = systolic;
			this.diastolic = diastolic;
		}
		
		/**
		 * @return the diastolic pressure for this value.
		 */
		public int getDiastolic() {
			return diastolic;
		}
		
		/**
		 * @param diastolic the new diastolic pressure for this value. Must be greater than or equal to 0
		 */
		public void setDiastolic(int diastolic) {
			if (diastolic < 0) {
				throw new IllegalArgumentException("diastolic value must be greater than or equal to zero.");
			}
			
			this.diastolic = diastolic;
		}
		
		/**
		 * @return the systolic pressure for this value.
		 */
		public int getSystolic() {
			return systolic;
		}
		
		/**
		 * @param systolic the new systolic pressure for this value. Must be greater than or equal to 0
		 */
		public void setSystolic(int systolic) {
			if (systolic < 0) {
				throw new IllegalArgumentException("systolic value must be greater than or equal to zero.");
			}
			
			this.systolic = systolic;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = INDEX_DIASTOLIC;
			result = prime * result + diastolic;
			result = prime * result + systolic;
			return result;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof BloodPressureValue))
				return false;
			BloodPressureValue other = (BloodPressureValue) obj;
			if (diastolic != other.diastolic)
				return false;
			if (systolic != other.systolic)
				return false;
			return true;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("%d%s%d", systolic, DELIM, diastolic);
		}
		
		/* (non-Javadoc)
		 * 
		 * @see java.lang.Object#clone()
		 */
		@Override
		public BloodPressureValue clone() {
			// Clone a BloodPressureValue object
			BloodPressureValue answer;

			try {
				answer = (BloodPressureValue) super.clone();
			} catch (CloneNotSupportedException exception) {
				// This exception should not occur. But if it does, it would
				// indicate a programming
				// error that made super.clone unavailable. The most common cause
				// would be
				// forgetting the "implements Cloneable" clause at the start of the
				// class.
				throw new RuntimeException("BloodPressureValue.clone(): This class does not implement Cloneable.");
			}
			
			return answer;
		}
	}

}
