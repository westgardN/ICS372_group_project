/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.reading;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class BloodPressure extends Reading {
	private static final long serialVersionUID = -7058161175665447094L;
	private BloodPressureValue value;
	
	/**
	 * 
	 */
	public BloodPressure() {
		this(null, null, null, null);
	}

	/**
	 * @param patientId
	 * @param id
	 * @param date
	 * @param value
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

	/* (non-Javadoc)
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
	
	public class BloodPressureValue implements Serializable {
		private static final long serialVersionUID = -6450411049437598250L;
		private static final String DELIM = "/";
		private int systolic;
		private int diastolic;
		
		public BloodPressureValue() {
			this(Integer.MIN_VALUE, Integer.MIN_VALUE);
		}
		
		public BloodPressureValue(String value) throws NumberFormatException {
			
			String[] vals = value.split(DELIM);
			
			if (vals.length == 2) {
				try {
					systolic = Integer.parseInt(vals[0]);
					diastolic = Integer.parseInt(vals[1]);
				} catch (NumberFormatException ex) {
					throw ex;
				}
			} else {
				throw new IllegalArgumentException("Invalid formatted string. value must be a value must be a string in systolic/diastolic format");
			}
		}
		
		public BloodPressureValue(int systolic, int diastolic) {
			if (systolic < 0 || diastolic < 0) {
				throw new IllegalArgumentException("systolic and diastolic values must be greater than or equal to zero.");
			}
			
			this.systolic = systolic;
			this.diastolic = diastolic;
		}
		
		/**
		 * @return the diastolic
		 */
		public int getDiastolic() {
			return diastolic;
		}
		
		/**
		 * @param diastolic the diastolic to set
		 */
		public void setDiastolic(int diastolic) {
			this.diastolic = diastolic;
		}
		
		/**
		 * @return the systolic
		 */
		public int getSystolic() {
			return systolic;
		}
		
		/**
		 * @param systolic the systolic to set
		 */
		public void setSystolic(int systolic) {
			this.systolic = systolic;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
		
		private BloodPressure getOuterType() {
			return BloodPressure.this;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("%d%s%d", systolic, DELIM, diastolic);
		}
		
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
