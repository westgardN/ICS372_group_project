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
public abstract class Reading implements Serializable {
	private static final long serialVersionUID = 8166141304679594433L;
	protected String patientId;
	protected String id;
	protected LocalDateTime date;
	
	/**
	 * Initializes an empty reading.
	 */
	public Reading() {
		this(null, null, null, null);
	}
	
	/**
	 * Initializes a reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. 
	 */
	public Reading(String patientId, String id, LocalDateTime date, Object value) {
		this.patientId = patientId;
		this.id = id;
		this.date = date;
		this.setValue(value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
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
		if (!(obj instanceof Reading))
			return false;
		Reading other = (Reading) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		if (getValue() == null) {
			if (other.getValue() != null)
				return false;
		} else if (!getValue().equals(other.getValue()))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reading");
		builder.append(" [patientId=");
		builder.append(patientId);
		builder.append(", id=");
		builder.append(id);
		builder.append(", date=");
		builder.append(date);
		builder.append(", value=");
		builder.append(getValue());
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the value
	 */
	public abstract Object getValue();

	/**
	 * @param value the value to set
	 */
	public abstract void setValue(Object value);
	
}
