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
	public static final String PROP_READINGS = "readings";
	public static final String PROP_PATIENTS = "patients";
	public static final String PROP_JOURNAL = "journal";
	public static final String PROP_SELECTED_READING = "selectedReading";
	public static final String PROP_SELECTED_PATIENT = "selectedPatient";
	public static final String PROP_UPDATE_PATIENT = "updatePatient";
	private static final String DEFAULT_TRIAL_NAME = "";
	private transient final PropertyChangeSupport pcs;
	private Trial trial;
	private Patient selectedPatient;
	private Reading selectedReading;
	
	private ObservableList<Patient> patients;
	private ObservableList<Reading> journal;
	private ObservableList<String> readingTypes = FXCollections.observableArrayList("Weight", "Steps", "Temp", "Blood Pressure");

	public ClinicalTrialViewModel() {
		this(DEFAULT_TRIAL_NAME);
	}

	public ClinicalTrialViewModel(String name) {
		trial = new Trial(name);
		patients = FXCollections.observableArrayList(trial.getPatients());
		selectedPatient = null;
		selectedReading = null;
		pcs = new PropertyChangeSupport(this);
	}

	/**
	 * @return the selectedPatient
	 */
	public Patient getSelectedPatient() {
		return selectedPatient;
	}

	public void fireUpdatePatient(Patient patient) {
		if (patient != null && trial.hasPatientInList(patient)) {
			Patient oldValue = null;
			pcs.firePropertyChange(PROP_UPDATE_PATIENT, oldValue, patient);
		}
	}
	
	public void fireUpdatePatient(String patientId) {
		Patient patient = getPatient(patientId);
		fireUpdatePatient(patient);
	}
	
	/**
	 * @param selectedPatient the selectedPatient to set
	 */
	public void setSelectedPatient(Patient selectedPatient) {
		setSelectedPatient(selectedPatient, true);
	}

	/**
	 * @param selectedPatient the selectedPatient to set
	 */
	public void setSelectedPatient(Patient selectedPatient, boolean notify) {
		if (!Objects.equals(this.selectedPatient, selectedPatient)) {
			Patient oldValue = this.selectedPatient;
			this.selectedPatient = selectedPatient;
			if (notify) {
				pcs.firePropertyChange(PROP_SELECTED_PATIENT, oldValue, this.selectedPatient);
			}
			
			setJournal(this.selectedPatient, notify);
		}
	}

	/**
	 * @param selectedReading the selectedReading to set
	 */
	public void setSelectedReading(Reading selectedReading) {
		if (!Objects.equals(this.selectedReading, selectedReading)) {
			Reading oldValue = this.selectedReading;
			this.selectedReading = selectedReading;
			pcs.firePropertyChange(PROP_SELECTED_READING, oldValue, this.selectedReading);
		}
	}
	
	public Reading getSelectedReading() {
		return selectedReading;
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
		setJournal(patient, true);
	}
	
	/**
	 * @param patient the patient's whose journal we are observing.
	 */
	public void setJournal(Patient patient, boolean notify) {
		ObservableList<Reading> oldValue = journal;
		journal = patient != null ? FXCollections.observableArrayList(patient.getJournal()) : null;
		
		if (notify) {
			pcs.firePropertyChange(PROP_JOURNAL, oldValue, journal);
		}
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
			pcs.firePropertyChange(PROP_PATIENTS, oldValue, trial.getNumPatients());
			patients.add(trial.getPatient(patientId));
		}
		return answer;
	}
	
	public boolean importReading(Reading reading) {
		boolean answer = false;
		
		if (reading != null) {
			int oldValue = selectedPatient.getJournalSize();
			answer = selectedPatient.addReading(reading);
			if (answer) {
				pcs.firePropertyChange(PROP_READINGS, oldValue, selectedPatient.getJournalSize());
				journal.add(reading);
			}
		}
		return answer;
	}
	
	public boolean importReading(String type, String id, Object value, LocalDateTime date) {
		Reading reading = ReadingFactory.getReading(type);
		reading.setPatientId(selectedPatient.getId());
		reading.setId(id);
		reading.setValue(value);
		reading.setDate(date);
		
		return importReading(reading);
	}
	
	public boolean addReading(Reading reading) {
		boolean answer = false;
		
		if (reading != null) {
			int oldValue = selectedPatient.getJournalSize();
			answer = isPatientInTrial(selectedPatient) ? selectedPatient.addReading(reading) : false;
			if (answer) {
				pcs.firePropertyChange(PROP_READINGS, oldValue, selectedPatient.getJournalSize());
				journal.add(reading);
			}
		}
		return answer;
	}
	
	public boolean addReading(String type, String id, Object value, LocalDateTime date) {
		Reading reading = ReadingFactory.getReading(type);
		reading.setPatientId(selectedPatient.getId());
		reading.setId(id);
		reading.setValue(value);
		reading.setDate(date);
		
		return addReading(reading);
	}

	public Patient getPatient(String patientId) {
		return trial.getPatient(patientId);
	}

	public String getTrialId() {
		return trial.getId();
	}
	
	public boolean isPatientInTrial(Patient patient) {
		return trial.isPatientInTrial(patient);
	}
	
	public boolean hasPatientStartedTrial(Patient patient) {
		return trial.hasPatientStartedTrial(patient);
	}
}
