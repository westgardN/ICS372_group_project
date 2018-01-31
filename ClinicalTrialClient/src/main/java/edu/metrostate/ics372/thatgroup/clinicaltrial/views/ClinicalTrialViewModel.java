package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.PatientFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClinicalTrialViewModel {
	private Trial trial;
	ObservableList<Patient> observablePatients;
	ObservableList<Reading> observablePatientJournal;
	ObservableList<String> readingTypeChoices = FXCollections.observableArrayList("Weight", "Steps", "Temp", "Blood Pressure");

	public ClinicalTrialViewModel() {
		trial = new Trial("t01");
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
	public ObservableList<Patient> getObservablePatients() {
		return FXCollections.observableArrayList(trial.getPatients());
	}


	/**
	 * @return the observablePatientJournal
	 */
	public ObservableList<Reading> getObservablePatientJournal() {
		return observablePatientJournal;
	}

	/**
	 * @param observablePatientJournal
	 *            the observablePatientJournal to set
	 */
	public void setObservablePatientJournal(Patient patient) {
		observablePatientJournal = FXCollections.observableArrayList(patient.getJournal());
	}

	/**
	 * @return the readingTypeChoices
	 */
	public ObservableList<String> getReadingTypeChoices() {
		return readingTypeChoices;
	}


	public ObservableList<Reading> getPatientJournal(Patient patient) {
		ObservableList<Reading> observablePatientJournal = FXCollections.observableArrayList(patient.getJournal());
		return observablePatientJournal;
	}

	public boolean addPatientToTrial(String patientId, LocalDate startDate) {
		return trial.addPatient(patientId, startDate);
	}

}
