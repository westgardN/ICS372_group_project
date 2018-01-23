package edu.metrostate.ics372.thatgroup.clinicaltrial.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Patient {
	private String patient_id;
	ArrayList<Object> journal = new ArrayList<Object>();
	
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
	public Patient(String patient_id, ArrayList<Object> journal) {
		this.patient_id = patient_id;
		this.journal = journal;
	}	
	
	public void addReading(){
		Reading r = new Reading();
		journal.add(r);
		
	}
	public void startPatientTrial(){
		System.out.println("patient trial has begun.");
	}
	public void endPatientTrial(){
		System.out.println("patient trial has ended.");
	}
	public void getReading(){
		
	}
}