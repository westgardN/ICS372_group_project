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
 * @author 
 *
 */
public class Trial implements Serializable {
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
	 * @return the id of this trial.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param trialId the new id of this trial.
	 */
	public void setId(String trialId) {
		if (!Objects.equals(id, trialId)) {
			String oldId = this.id;
			this.id = trialId;
			pcs.firePropertyChange("id", oldId, this.id);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
	
	/**
	 * @return a reference to the patients in this trial as a Set
	 */
	public Set<Patient> getPatients() {
		return patients;
	}

	/**
	 * @param patientSet the new set of patients for this trial.
	 */
	public void setPatients(Set<Patient> patientSet) {
		this.patients = patientSet;
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
				pcs.firePropertyChange("patients", oldValue, patients.size());
			}
		}
		return answer;
	}
	
	public boolean addPatient(String patientId) {
		return addPatient(patientId, null);
	}
	
	public boolean hasPatientStartedTrial(Patient patient) {
		boolean answer = false;
		
		if (patient.getTrialId() == id && patient.getTrialStartDate() != null && patient.getTrialEndDate() == null) {
			answer = true;
		}
		
		return answer;
	}
	
	public boolean isPatientInTrial(Patient patient) {
		boolean answer = false;
		
		if (patients.contains(patient) && patient.getTrialId() == id && hasPatientStartedTrial(patient)) {
			answer = true;
		}
		
		return answer;
	}

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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof Trial))
			return false;
		Trial other = (Trial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
