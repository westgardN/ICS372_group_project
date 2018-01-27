package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

public class Patient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String patientID;
	List<Reading> journal = new ArrayList<Reading>();

	public Patient() {

	}

	/**
	 * Creates a new Patient object with the specified parameters
	 * 
	 * @param patient_id
	 *            the patient_id to be used
	 * @param journal
	 *            the journal name to be used
	 */
	public Patient(String patientID, List<Reading> journal) {
		this.patientID = patientID;
		this.journal = journal;
	}

	/**
	 * @return the patientID
	 */
	public String getPatientID() {
		return patientID;
	}

	/**
	 * @param patientID
	 *            the patientID to set
	 */
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	/**
	 * @return the journal
	 */
	public List<Reading> getJournal() {
		return journal;
	}

	/**
	 * @param journal
	 *            the journal to set
	 */
	public void setJournal(List<Reading> journal) {
		this.journal = journal;
	}

	public void addReading(Reading reading) {
		 journal.add(reading);
	}

	public void startPatientTrial() {
		System.out.println("patient trial has begun.");
	}

	public void endPatientTrial() {
		System.out.println("patient trial has ended.");
	}

	public void getReading() {

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Patient ID: %s", patientID);
	}
}