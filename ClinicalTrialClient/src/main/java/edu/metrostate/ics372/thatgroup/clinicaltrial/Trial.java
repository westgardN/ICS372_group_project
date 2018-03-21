package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;

/**
 * The Trial bean is used to hold information about a clinical trial. The trial
 * contains a list of all the patients that have and that currently are, a part
 * of it.
 * 
 * The trial class also supports property change notification.
 * 
 * @author That Group
 *
 */
public class Trial implements Serializable, Cloneable {
	private static final String DEFAULT_TRIAL_ID = "";

	/**
	 * The patients property is used to receive notifications about changes to the
	 * patient list within a trial.
	 */
	public static final String PROP_PATIENTS = "patients";

	/**
	 * The id property of the trial. Used to uniquely identify a trial
	 */
	public static final String PROP_ID = "id";

	private static final long serialVersionUID = 4128763071142480689L;
	private transient PropertyChangeSupport pcs;
	private String id;
	private LocalDate startDate;
	private LocalDate endDate;
	

	/**
	 * Initializes this trial with no id.
	 */
	public Trial() {
		this(DEFAULT_TRIAL_ID);
	}

	/**
	 * Initializes this trial with the specified id.
	 * 
	 * @param trialId
	 *            the id of this trial. Cannot be null.
	 */
	public Trial(String trialId) {
		this.id = trialId;
		this.startDate = LocalDate.now();
		pcs = new PropertyChangeSupport(this);
	}

	/**
	 * Trials have an id. Each trial has its own unique Id.
	 * 
	 * @return the id of this trial.
	 */
	public String getId() {
		return id;
	}

	private PropertyChangeSupport getPcs() {
		if (pcs == null) {
			pcs = new PropertyChangeSupport(this);
		}
		
		return pcs;
	}
	/**
	 * Sets the trailId Id of a trial. If the id is different from what was
	 * currently set, a change notification is fired.
	 * 
	 * @param trialId
	 *            the new id for this trial. Cannot be null.
	 */
	public void setId(String trialId) {
		if (!Objects.equals(id, trialId)) {
			String oldId = this.id;
			this.id = trialId;
			getPcs().firePropertyChange(PROP_ID, oldId, this.id);
		}
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
	 * Adds the specified patient to this trial if it doesn't already exist. The
	 * trial id of the patient is updated with this trial's id. Returns true if the
	 * patient was successfully added to this trial; otherwise false.
	 * 
	 * @param patient
	 *            the patient to add to this trial. Cannot be null.
	 * @return true if the patient was added to this trial; otherwise false.
	 */
	public boolean addPatient(Patient patient) {
		boolean answer = false;
		if (patient != null) {
			patient.setTrialId(id);
			int oldValue = patients.size();
			answer = patients.add(patient);
			if (answer) {
				getPcs().firePropertyChange(PROP_PATIENTS, oldValue, patients.size());
			}
		}
		return answer;
	}

	/**
	 * Returns true is Patient was successfully added to a trial.
	 * 
	 * @param patientId
	 *            the id of the patient to add to this trial. Cannot be null.
	 * @return true if the patient was added to this trial; otherwise false.
	 */
	public boolean addPatient(String patientId) {
		return addPatient(patientId, null);
	}

	/**
	 * Returns true if the specified patient has started this trial
	 * 
	 * @param patient
	 *            The patient to check. Cannot be null.
	 * @return answer true if the specified patient has started this trial
	 */
	public boolean hasPatientStartedTrial(Patient patient) {
		boolean answer = false;

		if (patient.getTrialId() == id && patient.getTrialStartDate() != null && patient.getTrialEndDate() == null) {
			answer = true;
		}

		return answer;
	}

	/**
	 * Returns true if the patient is considered to be in the trial. Please note
	 * that a return value of true does not mean the patient is currently active in
	 * the trial, it just means that the patient is considered to be or have been a
	 * part of this trial.
	 * 
	 * @param patient
	 *            The patient to check. Cannot be null.
	 * @return true if the patient is considered to be in the trial;
	 *         otherwise false.
	 */
	public boolean isPatientInTrial(Patient patient) {
		boolean answer = false;

		if (patients.contains(patient) && patient.getTrialId() == id && hasPatientStartedTrial(patient)) {
			answer = true;
		}

		return answer;
	}

	/**
	 * Returns true if the specified patient was added to this trial.
	 * 
	 * @param patientId The id of the patient to add. Cannot be null
	 * @param startDate The date the patient started the trial. May be null
	 * if the patient hasn't started the trial yet.
	 * 
	 * @return answer true if the specified patient was added to this trial; otherwise false.
	 */
	public boolean addPatient(String patientId, LocalDate startDate) {
		boolean answer = false;
		Patient patient = PatientFactory.getPatient("clinical");
		patient.setId(patientId);

		if (startDate != null) {
			patient.setTrialStartDate(startDate);
		}

		answer = addPatient(patient);
		return answer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.toUpperCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Trial))
			return false;
		Trial other = (Trial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else {
			if (other.id != null) {
				if (id.compareToIgnoreCase(other.id) != 0) {
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
		StringBuilder builder = new StringBuilder();
		builder.append("Trial ");
		builder.append(id);
		builder.append(" has ");
		builder.append(patients.size());
		builder.append(" patient");
		if (patients.size() != 1) {
			builder.append("s");
		}
		return builder.toString();
	}

	/**
	 * Returns a reference to the specified patient id if it is in this trial;
	 * otherwise a null reference is returned.
	 * 
	 * @param patientId The patient to retrieve
	 * @return The patient with the specified Id or the null reference if the id is not
	 * found in this trial.
	 * 
	 */
	public Patient getPatient(String patientId) {
		Optional<Patient> answer = patients.stream().filter(patient -> patientId.equals(patient.getId())).findAny();
		return answer != null && answer.isPresent() ? answer.get() : null;
	}

	/**
	 * The number of patients in the list that are considered to be or have been a part of this trial.
	 * @return the number of patients in the list that are considered to be or have been a part of this trial. 
	 */
	public int getNumPatients() {
		return patients.size();
	}

	/**
	 * Returns true if the specified patient exists in this trial's list of patients.
	 * 
	 * @param patient The patient to search for.
	 * 
	 * @return true if the specified patient exists in this trial's list of patients; otherwise false.
	 */
	public boolean hasPatientInList(Patient patient) {
		return patients.contains(patient);
	}
	
    @Override
    public Trial clone() {
    	Trial answer;
        
        try {
            answer = (Trial) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Trial.clone(): This class doe not implement Cloneable.");
        }
        
        answer.id = this.id;
        answer.patients = new HashSet<>(this.patients);        
        
        return answer;
    }

	public void setStartDate(LocalDate localDate) {
		this.startDate = localDate;
	}
	
	public void setEndDate(LocalDate localDate) {
		this.endDate = localDate;
	}
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	public LocalDate getEndDate() {
		return this.endDate;
	}
}
