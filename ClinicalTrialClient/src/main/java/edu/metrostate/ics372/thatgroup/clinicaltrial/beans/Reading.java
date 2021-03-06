/**
 * File: Reading.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

/**
 * Represents a medical reading for a specific patient. The Reading class
 * provides the base for all other reading types. All Reading base types share
 * the interface provided by this class.
 * 
 * Classes that derive from Reading should override the methods setValue and getValue
 * that provide the differentiation among reading types.
 * 
 * Clients that use Reading should also use the ReadingFactory to instantiate new readings based on a
 * specific type. If a specific type is not needed, then an instance of the base Reading class can be
 * used.
 * 
 * Two Reading objects are considered equal if they have the same id and are associated with the same clinic and patient.
 * That means that two readings that have identical ids but have different clinic or patient ids are considered to be two
 * different readings.
 * 
 * @author That Group
 *
 */
public class Reading implements Serializable {
	public static final String PROP_ID = "id";
	public static final String PROP_PATIENT_ID = "patientId";
	public static final String PROP_CLINIC_ID = "clinicId";
	public static final String PROP_DATE = "date";
	public static final String PROP_VALUE = "value";
	private static final String MSG_READING_HAS_A_VALUE_OF = " has a value of ";
	private static final String MSG_READING_TAKEN_AT = " taken at ";
	private static final String MSG_READING_TAKEN_ON = " taken on ";
	private static final String MSG_READING_FOR_PATIENT = " for patient ";
	private static final String MSG_READING_TITLE = "Reading ";
	private static final long serialVersionUID = 8166141304679594433L;
	private transient PropertyChangeSupport pcs;
	protected String patientId;
	protected String id;
	protected LocalDateTime date;
	protected String clinicId;
	private Object value;
	
	/**
	 * Initializes an empty reading.
	 */
	public Reading() {
		this(null, null, null, null, null);
	}
	
	/**
	 * Initializes a reading based on the specified parameters.
	 * 
	 * @param patientId The ID of the patient this reading is for.
	 * @param id The ID of this reading.
	 * @param date The date and time the reading was taken.
	 * @param value The value of the reading. 
	 * @param clinicId The ID of the clinic associated with this object.
	 */
	public Reading(String patientId, String id, LocalDateTime date, Object value, String clinicId) {
		this.patientId = patientId;
		this.id = id;
		this.date = date;
		this.clinicId = clinicId;
		this.setValue(value);
		pcs = new PropertyChangeSupport(this);
	}

	/**
	 * Add a PropertyChangeListener to the listener list. The listener is registered
	 * for all properties. The same listener object may be added more than once, and
	 * will be called as many times as it is added. If listener is null, no
	 * exception is thrown and no action is taken.
	 * 
	 * @param listener
	 *            - The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		getPcs().addPropertyChangeListener(listener);
    }
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		getPcs().removePropertyChangeListener(listener);
    }
	
	protected PropertyChangeSupport getPcs() {
		if (pcs == null) {
			pcs = new PropertyChangeSupport(this);
		}
		
		return pcs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.toUpperCase().hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.toUpperCase().hashCode());
		result = prime * result + ((clinicId == null) ? 0 : clinicId.toUpperCase().hashCode());
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
		if (!(obj instanceof Reading)) {
			return false;
		}
		Reading other = (Reading) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else {
			if (other.id != null) {
				if (!id.equalsIgnoreCase(other.id)) {
					return false; 
				}
			} else {
				return false;
			}
		}
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else {
			if (other.patientId != null) {
				if (!patientId.equalsIgnoreCase(other.patientId)) {
					return false; 
				}
			} else {
				return false;
			}
		}
		if (clinicId == null) {
			if (other.clinicId != null)
				return false;
		} else {
			if (other.clinicId != null) {
				if (!clinicId.equalsIgnoreCase(other.clinicId)) {
					return false; 
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		StringBuilder builder = new StringBuilder();
		builder.append(MSG_READING_TITLE);
		builder.append(id);
		builder.append(MSG_READING_FOR_PATIENT);
		builder.append(patientId);
		builder.append(MSG_READING_TAKEN_ON);
		builder.append(date != null ? date.format(formatter) : null);
		builder.append(MSG_READING_TAKEN_AT);
		builder.append(clinicId);
		builder.append(MSG_READING_HAS_A_VALUE_OF);
		builder.append(getValue());
		return builder.toString();
	}

	/**
	 * @return the id of the patient associated with this reading
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId Associate this reading with the specified patientId
	 */
	public void setPatientId(String patientId) {
		if (!Objects.equals(this.patientId, patientId)) {
			String oldValue = this.patientId;
			this.patientId = patientId;
			getPcs().firePropertyChange(PROP_PATIENT_ID, oldValue, this.patientId);
		}
	}

	/**
	 * @return the id of this reading.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the new id of this reading. Needs to be unique of the other readings that are associated with the
	 * same patient as this reading.
	 */
	public void setId(String id) {
		if (!Objects.equals(this.id, id)) {
			String oldValue = this.id;
			this.id = id;
			getPcs().firePropertyChange(PROP_ID, oldValue, this.id);
		}
	}

	/**
	 * @return the date and time this reading was taken.
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the new date and time this reading was taken.
	 */
	public void setDate(LocalDateTime date) {
		if (!Objects.equals(this.date, date)) {
			String oldValue = this.clinicId;
			if (date != null) {
				this.date = LocalDateTime.from(date);
			}
			else {
				this.date = null;
			}
			getPcs().firePropertyChange(PROP_DATE, oldValue, this.date);
		}
	}

	/**
	 * @return the value of this reading. Meaning of the value is dependent
	 * on the specific type of reading this is for.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the new value for this reading. Meaning of the value is dependent
	 * on the specific type of reading this is for.
	 */
	public void setValue(Object value) {
		if (!Objects.equals(this.value, value)) {
			Object oldValue = this.value;
			this.value = value;
			getPcs().firePropertyChange(PROP_VALUE, oldValue, this.value);
		}
	}

	/**
	 * @return the clinicId
	 */
	public String getClinicId() {
		return clinicId;
	}

	/**
	 * @param clinicId the clinicId to set
	 */
	public void setClinicId(String clinicId) {
		if (!Objects.equals(this.clinicId, clinicId)) {
			String oldValue = this.clinicId;
			this.clinicId = clinicId;
			getPcs().firePropertyChange(PROP_CLINIC_ID, oldValue, this.clinicId);
		}
	}
}
