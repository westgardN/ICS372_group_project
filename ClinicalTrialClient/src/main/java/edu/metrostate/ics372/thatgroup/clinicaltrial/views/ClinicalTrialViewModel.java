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
	ObservableList<Patient> obvservableSimPatients;
	ObservableList<Reading> obvservableSimReadings;
	ObservableList<String> readingTypeChoices;

	public ClinicalTrialViewModel() {
		// This is just a mock implementation.  TODO
		readingTypeChoices = FXCollections.observableArrayList("Weight", "Steps", "Temp", "Blood Pressure");
		trial = new Trial("testTrial01");
		int k = 0;
		for (int i = 0; i < 30; i++) {
			Set<Reading> simJournal = new HashSet<>();
			for (int j = 0; j < 30; j++) {
				try {
					simJournal.addAll(JsonProcessor.read("./data/testReadAndWrite_out.json"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Patient patient = PatientFactory.getPatient("clinical");
			patient.setId(String.format("p%d", k++));
			patient.setJournal(simJournal);
			trial.addPatient(patient);
		}
		obvservableSimPatients = FXCollections.observableArrayList(trial.getPatientList());
		obvservableSimReadings = FXCollections.observableArrayList(trial.getPatientList().get(0).getJournal());
	}

	/**
	 * @return the obvservableSimPatients
	 */
	public ObservableList<Patient> getObvservableSimPatients() {
		return obvservableSimPatients;
	}

	/**
	 * @param obvservableSimPatients the obvservableSimPatients to set
	 */
	public void setObvservableSimPatients(ObservableList<Patient> obvservableSimPatients) {
		this.obvservableSimPatients = obvservableSimPatients;
	}

	/**
	 * @return the obvservableSimReadings
	 */
	public ObservableList<Reading> getObvservableSimReadings() {
		return obvservableSimReadings;
	}

	/**
	 * @param obvservableSimReadings the obvservableSimReadings to set
	 */
	public void setObvservableSimReadings(ObservableList<Reading> obvservableSimReadings) {
		this.obvservableSimReadings = obvservableSimReadings;
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
}
