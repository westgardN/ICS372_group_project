package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.util.ArrayList;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;

public class Trial {
	private String trialID;
	List<String> sites;
	List<Patient> patientList;
	
	
	/**
	 * Creates a new Trial object with the following parameters
	 * 
	 * @param trialID
	 *          the trialID for the trial
	 * @param sites
	 *          an array list of the sites
	 * @param patient           
	 * 			an array list of the sites
	 */
	public Trial(String trialID) {
		this.trialID = trialID;
		sites = new ArrayList<String>();
		patientList = new ArrayList<>();
	}
	
	
	
	/**
	 * @return the trialID
	 */
	public String getTrialID() {
		return trialID;
	}



	/**
	 * @param trialID the trialID to set
	 */
	public void setTrialID(String trialID) {
		this.trialID = trialID;
	}



	/**
	 * @return the patientList
	 */
	public List<Patient> getPatientList() {
		return patientList;
	}



	/**
	 * @param patientList the patientList to set
	 */
	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}

	public void addPatient(Patient patient) {
		patientList.add(patient);
	}
	
	public void updatePatient(Patient patient) {
		
	}
}
