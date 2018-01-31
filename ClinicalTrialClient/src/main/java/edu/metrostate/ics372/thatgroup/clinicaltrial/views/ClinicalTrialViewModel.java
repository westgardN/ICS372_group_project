package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.time.LocalDate;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClinicalTrialViewModel {
	private Trial trial;
	ObservableList<Patient> patients;
	ObservableList<Reading> journal;
	ObservableList<String> readingTypes = FXCollections.observableArrayList("Weight", "Steps", "Temp", "Blood Pressure");

	public ClinicalTrialViewModel() {
		trial = new Trial("t01");
		patients = FXCollections.observableArrayList(trial.getPatients());
	}

	/**
	 * @return the trial
	 */
	public Trial getTrial() {
		return trial;
	}


	/**
	 * @return the observablePatients
	 */
	public ObservableList<Patient> getPatients() {
		return patients;
	}


	/**
	 * @return the observablePatientJournal
	 */
	public ObservableList<Reading> getJournal() {
		return journal;
	}

	/**
	 * @param patient the patient's whose journal we are observing.
	 */
	public void setJournal(Patient patient) {
		journal = FXCollections.observableArrayList(patient.getJournal());
	}

	/**
	 * @return the readingTypeChoices
	 */
	public ObservableList<String> getReadingTypeChoices() {
		return readingTypes;
	}


	public ObservableList<Reading> getJournal(Patient patient) {
		return FXCollections.observableArrayList(patient.getJournal());
	}

	public boolean addPatient(String patientId, LocalDate startDate) {
		boolean answer = trial.addPatient(patientId, startDate);
		if (answer) {
			patients.add(trial.getPatient(patientId));
		}
		return answer;
	}
}
