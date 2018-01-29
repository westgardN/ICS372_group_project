package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.metrostate.ics372.thatgroup.clinicaltrial.JsonProcessor;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.PatientFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClinicalTrialViewModel {
	public static Trial trial;
	ObservableList<Patient> patients;
	ObservableList<Reading> readings;
	ObservableList<String> types;

	public ClinicalTrialViewModel() {
		// This is just a mock implementation.  TODO
		types = FXCollections.observableArrayList("Weight", "Steps", "Temp", "Blood Pressure");
		trial = new Trial();
		patients = FXCollections.observableArrayList(trial.getPatientList());
		readings = FXCollections.observableArrayList(trial.getPatientList().get(0).getJournal());
	}

	/**
	 * @return the obvservableSimPatients
	 */
	public ObservableList<Patient> getPatients() {
		return patients;
	}

	/**
	 * @param obvservableSimPatients the obvservableSimPatients to set
	 */
	public void setPatients(ObservableList<Patient> obvservableSimPatients) {
		this.patients = obvservableSimPatients;
	}

	/**
	 * @return the obvservableSimReadings
	 */
	public ObservableList<Reading> getReadings() {
		return readings;
	}

	/**
	 * @param obvservableSimReadings the obvservableSimReadings to set
	 */
	public void setReadings(ObservableList<Reading> obvservableSimReadings) {
		this.readings = obvservableSimReadings;
	}
	
	/**
	 * @return the readingTypeChoices
	 */
	public ObservableList<String> getTypes() {
		return types;
	}

	public ObservableList<Reading> getPatientJournal(Patient patient) {
		ObservableList<Reading> observablePatientJournal = FXCollections.observableArrayList(patient.getJournal());
		return observablePatientJournal;
	}
}
