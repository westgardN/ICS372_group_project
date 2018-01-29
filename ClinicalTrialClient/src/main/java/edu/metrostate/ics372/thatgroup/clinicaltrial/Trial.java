package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.util.ArrayList;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;

public class Trial {
	private String id;
	List<Patient> patients;
	
	public Trial() {
		this(null);
	}
	
	
	/**
	 * Creates a new Trial object with the specified values.
	 * 
	 * @param trialId
	 *          the id of this trial.
	 */
	public Trial(String trialId) {
		this.id = trialId;
		patients = new ArrayList<>();
	}
	
	
	
	/**
	 * @return the id of this trial.
	 */
	public String getTrialId() {
		return id;
	}



	/**
	 * @param trialId the new id of this trial.
	 */
	public void setTrialID(String trialId) {
		this.id = trialId;
	}



	/**
	 * @return the patientList
	 */
	public List<Patient> getPatientList() {
		return patients;
	}



	/**
	 * @param patientList the patientList to set
	 */
	public void setPatientList(List<Patient> patientList) {
		this.patients = patientList;
	}

	public void addPatient(Patient patient) {
		patients.add(patient);
	}
	
	public void updatePatient(Patient patient) {
		
	}
}
