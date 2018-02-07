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
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.PatientFactory;

/**
 * 
 * @author That Group
 *
 */
public class Trial implements Serializable {
	public static final String PROP_PATIENTS = "patients";
	public static final String PROP_ID = "id";
	private static final long serialVersionUID = 4128763071142480689L;
	private transient final PropertyChangeSupport pcs;
	private String id;
	private Set<Patient> patients;
	
	public Trial() {
		this("");
	}
	
	/**
	 * Creates a new Trial object with the specified values.
	 * 
	 * @param trialId
	 *          the id of this trial.
	 */
	public Trial(String trialId) {
		this.id = trialId;
		patients = new HashSet<>();
		pcs = new PropertyChangeSupport(this);
	}
	
	/**
	 * Trials have an id. Each trial has its own unique Id.
	 * @return the id of this trial.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the trailId Id of a trial. Reports a bound property update to 
	 * listeners that have been registered to track updates of all properties 
	 * or a property with the specified name. No event is fired if old and new trialIds are equal and non-null.
	 * @param trialId the new id of this trial.
	 */
	public void setId(String trialId) {
		if (!Objects.equals(id, trialId)) {
			String oldId = this.id;
			this.id = trialId;
			pcs.firePropertyChange(PROP_ID, oldId, this.id);
		}
	}

	/**
	 * Adds a listener to the list of patients. 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
	
	/**
	 * Constructs a new set containing the Patients in the specified collection. 
	 * The HashMap is created with default load factor (0.75) and an initial capacity sufficient to contain the elements in the specified collection.
	 * @return a reference to the patients in this trial as a Set
	 */
	public Set<Patient> getPatients() {
		//return patients;
		return new HashSet<>(patients);
	}

	/**
	 * Sets the Patients in the patient list. If there is a change:
	 * Reports a bound property update to listeners that have been registered to track updates 
	 * of all properties or a property with the specified name.
	 * No event is fired if old and new values are equal and non-null.
	 * @param patients the new set of patients for this trial.
	 */
	protected void setPatients(Set<Patient> patients) {
		if (!Objects.equals(this.patients, patients)) {
			Set<Patient> oldValue = this.patients;
			this.patients = patients;
			pcs.firePropertyChange(PROP_PATIENTS, oldValue, this.patients);
		}
	}

	/**
	 * Adds the specified patient to this trial if it doesn't already exist. The 
	 * trial id of the patient is updated with this trial's id. Returns
	 * true if the patient was successfully added to this trial; otherwise false.
	 * 
	 * @param patient the patient to add to this trial
	 * @return true if the patient was added to this trial; otherwise false.
	 */
	public boolean addPatient(Patient patient) {
		boolean answer = false;
		if (patient != null) {
			patient.setTrialId(id);
			int oldValue = patients.size();
			answer = patients.add(patient);
			if (answer) {
				pcs.firePropertyChange(PROP_PATIENTS, oldValue, patients.size());
			}
		}
		return answer;
	}
	
	/**
	 * Retruns true is Patient was successfully added to a trial.
	 * @param patientId
	 * @return patientId that was added
	 */
	public boolean addPatient(String patientId) {
		return addPatient(patientId, null);
	}
	
	/**
	 *Returns true, If a Patient has a trial Id and a trial start date, 
	 *and start date is not null and the patient has no end date, else false.
	 * @param patient
	 * @return answer 
	 */
	public boolean hasPatientStartedTrial(Patient patient) {
		boolean answer = false;
		
		if (patient.getTrialId() == id && patient.getTrialStartDate() != null && patient.getTrialEndDate() == null) {
			answer = true;
		}
		
		return answer;
	}
	
	/**
	 * Returns true if the patient is in the patient list and has a trialId  and has the
	 * same Id of patient that has a trial start date. Else patient is not in a trial.
	 * @param patient
	 * @return answer
	 */
	public boolean isPatientInTrial(Patient patient) {
		boolean answer = false;
		
		if (patients.contains(patient) && patient.getTrialId() == id && hasPatientStartedTrial(patient)) {
			answer = true;
		}
		
		return answer;
	}

		/**
		 * Returns true if:  the patient was added to a "clinical trial and sets the trial start date. The start date 
		 * cannot be false
		 * else false.
		 * @param patientId
		 * @param startDate
		 * @return answer
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
	
	/*
	 * Returns a hash code value for the object. This method is supported for the benefit of 
	 * hash tables such as those provided by java.util.HashMap.
	 * @returns result
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.toUpperCase().hashCode());
		return result;
	}


	/* 
	 * Indicates whether some other object is "equal to" this one. In the case that a trialId is
	 * equal to a trialId it returns true. If the trialId is a different instance of a trial it returns false.
	 * If a trialId has differences in case it compares two strings lexicographically, ignoring case differences. 
	 * This method returns an integer whose sign is that of calling compareTo with normalized versions of the strings 
	 * where case differences have been eliminated by calling Character.toLowerCase(Character.toUpperCase(character)) on each character.
	 * 
	 * @returns boolean
	 */
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
				if (id.compareToIgnoreCase(other.id) != 0 ) {
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
		StringBuilder builder = new StringBuilder();
		builder.append("Trial ");
		builder.append(id);
		builder.append(" has ");
		builder.append(patients.size());
		builder.append(" patient" );
		if (patients.size() != 1) {
			builder.append("s" );
		}
		return builder.toString();
	}

	public Patient getPatient(String patientId) {
		Optional<Patient> answer = patients.stream().filter(patient -> patientId.equals(patient.getId())).findAny();
		return answer != null && answer.isPresent() ? answer.get() : null;
	}

	public int getNumPatients() {
		return patients.size();
	}

	public boolean hasPatientInList(Patient patient) {
		return patients.contains(patient);
	}
	
}
