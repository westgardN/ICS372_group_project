package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClinicalTrialViewModel {
	private transient final PropertyChangeSupport pcs;
	private Trial trial;
	private Patient selectedPatient;
	ObservableList<Patient> patients;
	ObservableList<Reading> journal;
	ObservableList<String> readingTypes = FXCollections.observableArrayList("Weight", "Steps", "Temp", "Blood Pressure");

	public ClinicalTrialViewModel() {
		trial = new Trial("t01");
		patients = FXCollections.observableArrayList(trial.getPatients());
		selectedPatient = null;
		pcs = new PropertyChangeSupport(this);
	}

	/**
	 * @return the selectedPatient
	 */
	public Patient getSelectedPatient() {
		return selectedPatient;
	}

	/**
	 * @param selectedPatient the selectedPatient to set
	 */
	public void setSelectedPatient(Patient selectedPatient) {
		if (!Objects.equals(this.selectedPatient, selectedPatient)) {
			Patient oldValue = this.selectedPatient;
			this.selectedPatient = selectedPatient;
			pcs.firePropertyChange("selectedPatient", oldValue, this.selectedPatient);
			setJournal(this.selectedPatient);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
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
		ObservableList<Reading> oldValue = journal;
		journal = patient != null ? FXCollections.observableArrayList(patient.getJournal()) : null;
		pcs.firePropertyChange("journal", oldValue, journal);
	}

	/**
	 * @return the readingTypeChoices
	 */
	public ObservableList<String> getReadingTypeChoices() {
		return readingTypes;
	}

	public boolean addPatient(String patientId, LocalDate startDate) {
		int oldValue = trial.getNumPatients();
		boolean answer = trial.addPatient(patientId, startDate);
		if (answer) {
			pcs.firePropertyChange("patients", oldValue, trial.getNumPatients());
			patients.add(trial.getPatient(patientId));
		}
		return answer;
	}
	
	public boolean addReading(String type, String id, Object value, LocalDateTime date) {
		Reading reading = ReadingFactory.getReading(type);
		reading.setPatientId(selectedPatient.getId());
		reading.setId(id);
		reading.setValue(value);
		reading.setDate(date);
		int oldValue = selectedPatient.getJournalSize();
		boolean answer = selectedPatient.addReading(reading);
		if (answer) {
			pcs.firePropertyChange("readings", oldValue, selectedPatient.getJournalSize());
			journal.add(reading);
		}
		return answer;
	}
}
