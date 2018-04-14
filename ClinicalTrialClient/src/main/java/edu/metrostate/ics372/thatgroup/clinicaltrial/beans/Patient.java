package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

/**
 * The patient contains an id a trial start and end date to track when they
 * enter and leave the associated clinical trial.
 * 
 * @author That Group 
 *
 */
public class Patient implements Serializable {
	/**
	 * The default ID used for a new Patient if one isn't provided.
	 */
	public static final String DEFAULT_ID = "default";
	
	/**
	 * The PROP_ID event is fired whenever the id of this patient is changed.
	 */
	public static final String PROP_ID = "id";
	
	/**
	 * The PROP_TRIAL_ID event is fired whenever the trial id of this patient is changed.
	 */
	public static final String PROP_TRIAL_ID = "trialId";
	
	/**
	 * The PROP_TRIAL_START_DATE even is fired whenever the start date of this patient is changed.
	 */
	public static final String PROP_TRIAL_START_DATE = "trialStartDate";
	
	/**
	 * The PROP_TRIAL_END_DATE even is fired whenever the end date of this patient is changed.
	 */
	public static final String PROP_TRIAL_END_DATE = "trialEndDate";
	
	/**
	 * The PROP_STATUS_ID event is fired whenever the status id of this patient is changed.
	 */
	public static final String PROP_STATUS_ID = "propStatus";
	
	private static final String DEFAULT_PATIENT_STATUS_ID = "INACTIVE";
	private static final String MSG_PATIENT_HAS_NOT_STARTED_THE_TRIAL = " has not started the trial";
	private static final String MSG_PATIENT_CLOSE_PAREN = ")";
	private static final String MSG_PATIENT_DASH = " - ";
	private static final String MSG_PATIENT_OPEN_PAREN = " (";
	private static final String MSG_PATIENT_STATUS = ": %s ";
	private static final long serialVersionUID = 8450664877127813850L;
	protected transient PropertyChangeSupport pcs;
	protected String id;
	protected String trialId;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected String statusId;

	/**
	 * @return the statusId
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(String statusId) {
		if (!Objects.equals(this.statusId, statusId)) {
			String oldValue = this.id;
			this.statusId = statusId;
			getPcs().firePropertyChange(PROP_STATUS_ID, oldValue, this.statusId);
		}
	}

	/**
	 * Initializes an empty patient.
	 */
	public Patient() {
		this(null, null, null, null, DEFAULT_PATIENT_STATUS_ID);
	}

	/**
	 * Initializes a patient with the specified values.
	 * 
	 * @param id the id of this patient
	 * @param trialId the Trial this patient belongs to
	 * @param start the date the patient started the trial
	 * @param end the date the patient left the trial
	 */
	public Patient(String id, String trialId, LocalDate start, LocalDate end) {
		this(id, trialId, start, end, DEFAULT_PATIENT_STATUS_ID);
	}

	/**
	 * Initializes a patient with the specified values.
	 * 
	 * @param id the id of this patient
	 * @param trialId the Trial this patient belongs to
	 * @param start the date the patient started the trial
	 * @param end the date the patient left the trial
	 */
	public Patient(String id, String trialId, LocalDate start, LocalDate end, String statusId) {
		super();
		this.id = id;
		this.trialId = trialId;
		this.startDate = start;
		this.endDate = end;
		this.statusId = statusId;
		pcs = new PropertyChangeSupport(this);
	}

	protected PropertyChangeSupport getPcs() {
		if (pcs == null) {
			pcs = new PropertyChangeSupport(this);
		}
		
		return pcs;
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
	
	/**
	 * @return the id of this patient.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the new id for this patient. Cannot be null.
	 */
	public void setId(String id) {
		if (!Objects.equals(this.id, id)) {
			String oldValue = this.id;
			this.id = id;
			getPcs().firePropertyChange(PROP_ID, oldValue, this.id);
		}
	}

	/**
	 * @return the date this patient entered the trial.
	 */
	public LocalDate getTrialStartDate() {
		return startDate;
	}

	/**
	 * @param trialStartDate the new trial start date for this patient.
	 */
	public void setTrialStartDate(LocalDate trialStartDate) {
		if (!Objects.equals(this.startDate, trialStartDate)) {
			LocalDate oldValue = this.startDate;
			this.startDate = trialStartDate;
			getPcs().firePropertyChange(PROP_TRIAL_START_DATE, oldValue, this.startDate);
		}
	}

	/**
	 * @return the date this patient left the trial.
	 */
	public LocalDate getTrialEndDate() {
		return endDate;
	}

	/**
	 * @param trialEndDate the date this patient left the clinical trial. 
	 */
	public void setTrialEndDate(LocalDate trialEndDate) {
		if (!Objects.equals(this.endDate, trialEndDate)) {
			LocalDate oldValue = this.endDate;
			this.endDate = trialEndDate;
			getPcs().firePropertyChange(PROP_TRIAL_END_DATE, oldValue, this.endDate);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.toUpperCase().hashCode());
		result = prime * result + ((trialId == null) ? 0 : trialId.toUpperCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Patient)) {
			return false;
		}
		Patient other = (Patient) obj;
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
		if (trialId == null) {
			if (other.trialId != null)
				return false;
		} else {
			if (other.trialId != null) {
				if (!trialId.equalsIgnoreCase(other.trialId)) {
					return false; 
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		if (startDate != null) {
			if (endDate == null) {
				builder.append(String.format(MSG_PATIENT_STATUS, this.getStatusId().toLowerCase()));
				builder.append(startDate.format(formatter));
			} else {
				builder.append(String.format(MSG_PATIENT_STATUS, this.getStatusId().toLowerCase()));
				builder.append(MSG_PATIENT_OPEN_PAREN);
				builder.append(startDate.format(formatter));
				builder.append(MSG_PATIENT_DASH);
				builder.append(endDate.format(formatter));
				builder.append(MSG_PATIENT_CLOSE_PAREN);
			}
		} else {
			builder.append(MSG_PATIENT_HAS_NOT_STARTED_THE_TRIAL);
		}
		
		return builder.toString();
	}

	/**
	 * @return the id of the trial that this patient belongs to.
	 */
	public String getTrialId() {
		return trialId;
	}

	/**
	 * @param trialId the new id of the trial that this patient belongs to.
	 * May be null is this patient doesn't belong to any trial.
	 */
	public void setTrialId(String trialId) {
		if (!Objects.equals(this.trialId, trialId)) {
			String oldValue = this.trialId;
			this.trialId = trialId;
			getPcs().firePropertyChange(PROP_TRIAL_ID, oldValue, this.trialId);
		}
	}
}