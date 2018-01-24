package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.util.ArrayList;

public class Trial {
	private String trialID;
	ArrayList<String> sites = new ArrayList<String>();
	ArrayList<Patient> patientList = new ArrayList<Patient>();
	
	
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
	public Trial(String trialID, ArrayList<String> sites, ArrayList<Patient> patientList)
	{
		this.trialID = trialID;
		this.sites = sites;
		this.patientList = patientList;
	};	
	
	
	public void addPatient(Patient patient, String trialID)
	{
		Patient p = patient;
		patientList.add(p);
	};
	
	public void updatePatient(Patient patient, String trialID) 
	{
		
	};
}
