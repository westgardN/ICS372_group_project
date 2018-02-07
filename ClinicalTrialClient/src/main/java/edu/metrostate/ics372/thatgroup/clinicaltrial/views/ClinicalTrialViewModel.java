/**
 * File: ClinicalTrialViewModel.java
 */

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

/**
 * The ClinicalTrialViewModel is a class that provides a central means of access to
 * all of the information that a clinical trial provides. The model is shared among all
 * of the views. At this time, the ClinicalTrialViewModel is not considered thread safe but,
 * since the model is created on the UI thread and because the model is shared by all of the views
 * this is not an issue at this time. 
 * 
 * The view model class provides properties for the trial and notifies listeners when those properties
 * change. This design allows for UI controls to update themselves when the data they are bound to changes.
 *  
 * The model itself is not much more than a wrapper for the Trial bean in that it internally calls the methods
 * of the trial for the associated operations. 
 *  
 * At this time, only a single trial is associated with a view model.
 * 
 * Although this particular view model is used in a JavaFX application, it does not use any JavaFX specified properties
 * but, it does provide JavaFX Observable collections and values via some of the getters.
 *  
 * @author That Group
 *
 */
public class ClinicalTrialViewModel {
	/**
	 * The readings property.
	 */
	public static final String PROP_READINGS = "readings";
	
	/**
	 * The patients property.
	 */
	public static final String PROP_PATIENTS = "patients";
	
	/**
	 * The journal property.
	 */
	public static final String PROP_JOURNAL = "journal";
	
	/**
	 * The selectedReading property.
	 */
	public static final String PROP_SELECTED_READING = "selectedReading";
	
	/**
	 * The selectedPatient property.
	 */
	public static final String PROP_SELECTED_PATIENT = "selectedPatient";
	
	/**
	 * The updatePatient property.
	 */
	public static final String PROP_UPDATE_PATIENT = "updatePatient";
	
	private static final String DEFAULT_TRIAL_NAME = "";
	private transient final PropertyChangeSupport pcs;
	private Trial trial;
	private Patient selectedPatient;
	private Reading selectedReading;
	
	private ObservableList<Patient> patients;
	private ObservableList<Reading> journal;
	private ObservableList<String> readingTypes = FXCollections.observableArrayList("Weight", "Steps", "Temp", "Blood Pressure");

	/**
	 * Initializes a new empty view model with the id of the trial set to the default trial name of ""
	 */
	public ClinicalTrialViewModel() {
		this(DEFAULT_TRIAL_NAME);
	}

	/**
	 * Initializes a new empty view model with the id of the trial set to the specified name.
	 * @param name the id of this trial
	 */
	public ClinicalTrialViewModel(String name) {
		trial = new Trial(name);
		patients = FXCollections.observableArrayList(trial.getPatients());
		selectedPatient = null;
		selectedReading = null;
		pcs = new PropertyChangeSupport(this);
	}

	/**
	 * @return the patient that is currently selected, which may be null. 
	 * 
	 */
	public Patient getSelectedPatient() {
		return selectedPatient;
	}

	/**
	 * Notifies any registered listeners that the specified patient has been updated, if the patient
	 * is in the trial's list of patients. Otherwise nothing happens.
	 * 
	 * @param patient the patient that was updated.
	 */
	public void fireUpdatePatient(Patient patient) {
		if (patient != null && trial.hasPatientInList(patient)) {
			Patient oldValue = null;
			pcs.firePropertyChange(PROP_UPDATE_PATIENT, oldValue, patient);
		}
	}
	
	/**
	 * Notifies any registered listeners that the specified patient has been updated, if the patient
	 * is in the trial's list of patients. Otherwise nothing happens.
	 * 
	 * @param patientId the patient that was updated.
	 */
	public void fireUpdatePatient(String patientId) {
		Patient patient = getPatient(patientId);
		fireUpdatePatient(patient);
	}
	
	/**
	 * Sets the "selected" patient to the specified patient and notifies any listeners
	 * of this change. Typically the selected patient is set in response to a UI event
	 * where the user selects a patient from a list and that list then notifies this model of the change.
	 * 
	 * @param selectedPatient the patient that is now considered to be the "selected"
	 * patient. 
	 */
	public void setSelectedPatient(Patient selectedPatient) {
		setSelectedPatient(selectedPatient, true);
	}

	/**
	 * Sets the "selected" patient to the specified patient and if notify is set to true,
	 * notifies any listeners of this change. Typically the selected patient is set in
	 * response to a UI event where the user selects a patient from a list and that list
	 * then notifies this model of the change.
	 * 
	 * @param selectedPatient the patient that is now considered to be the "selected"
	 * patient.
	 * @param notify is set to true any listeners are notified of this change. 
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
	 * Sets the "selected" reading to the specified reading and notifies any listeners
	 * of this change. Typically the selected reading is set in response to a UI event
	 * where the user selects a reading from a list and that list then notifies this model of the change.
	 * 
	 * @param selectedReading the reading that is now considered to be the "selected"
	 * selectedReading. 
	 */
	public void setSelectedReading(Reading selectedReading) {
		if (!Objects.equals(this.selectedReading, selectedReading)) {
			Reading oldValue = this.selectedReading;
			this.selectedReading = selectedReading;
			pcs.firePropertyChange(PROP_SELECTED_READING, oldValue, this.selectedReading);
		}
	}
	
	/**
	 * @return the reading that is currently selected, which may be null. 
	 * 
	 */
	public Reading getSelectedReading() {
		return selectedReading;
	}

	/**
	 * Add a PropertyChangeListener to the listener list. The listener is registered
	 * for all properties. The same listener object may be added more than once, and
	 * will be called as many times as it is added. If listener is null, no
	 * exception is thrown and no action is taken.
	 * 
	 * @param listener
	 *            - The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
	
	/**
	 * @return a reference to the actual trial associated with this view model. Changes made
	 * to the trial will affect this model's trial instance.
	 */
	public Trial getTrial() {
		return trial;
	}

	/**
	 * @return the reference to the observable list of patients that can directly be listened to for changes.
	 */
	public ObservableList<Patient> getPatients() {
		return patients;
	}

	/**
	 * @return the reference to the observable list of readings known as a journal, that can directly be
	 * listened to for changes.
	 */
	public ObservableList<Reading> getJournal() {
		return journal;
	}

	/**
	 * Sets the observable journal to be that of the specified patient.
	 * 
	 * @param patient the patient's whose journal we are observing.
	 */
	public void setJournal(Patient patient) {
		setJournal(patient, true);
	}
	
	/**
	 * Sets the observable journal to be that of the specified patient.
	 * 
	 * @param patient the patient's whose journal we are observing.
	 * @param notify if set to true then any registered listeners are notified
	 * via a PROP_JOURNAL change notification.
	 */
	public void setJournal(Patient patient, boolean notify) {
		ObservableList<Reading> oldValue = journal;
		journal = patient != null ? FXCollections.observableArrayList(patient.getJournal()) : null;
		
		if (notify) {
			pcs.firePropertyChange(PROP_JOURNAL, oldValue, journal);
		}
	}

	/**
	 * @return the reference to the observable list of reading types that can directly be
	 * listened to for changes.
	 */
	public ObservableList<String> getReadingTypeChoices() {
		return readingTypes;
	}

	/**
	 * Adds the specified patient to this model's trial with the specified start date.
	 * Returns true if the patient was added and false if the patient was not added. A patient
	 * can only be added to a trial once. Fires a PROP_PATIENTS change notification
	 * 
	 * @param patientId the id of the patient to add 
	 * @param startDate the date the patient started the trial, which may be null if the patient
	 * hasn't actually started the trial yet.
	 * @return true if the patient was added and false if the patient was not added.
	 */
	public boolean addPatient(String patientId, LocalDate startDate) {
		int oldValue = trial.getNumPatients();
		boolean answer = trial.addPatient(patientId, startDate);
		if (answer) {
			pcs.firePropertyChange(PROP_PATIENTS, oldValue, trial.getNumPatients());
			patients.add(trial.getPatient(patientId));
		}
		return answer;
	}
	
	/**
	 * Imports the specified reading in to the currently selected patient's journal. The currently
	 * selected patient must be a valid patient reference. Importing differs from adding in that a patient
	 * simply has to be in the trial's list in order to import a reading for it whereas to add a reading, the
	 * patient must be currently active in the trial.
	 * 
	 * @param reading the reading to add to the currently selected patient.
	 * 
	 * @return true if the reading was imported; otherwise false.
	 */
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
	
	/**
	 * Imports the specified reading in to the currently selected patient's journal. The currently
	 * selected patient must be a valid patient reference. Importing differs from adding in that a patient
	 * simply has to be in the trial's list in order to import a reading for it whereas to add a reading, the
	 * patient must be currently active in the trial.
	 * 
	 * @param type the type of reading that is being added
	 * @param id the id of the reading being added
	 * @param value the value of the reading being added
	 * @param date the date the reading was taken
	 * @return true if the reading was imported; otherwise false.
	 */
	public boolean importReading(String type, String id, Object value, LocalDateTime date) {
		Reading reading = ReadingFactory.getReading(type);
		reading.setPatientId(selectedPatient.getId());
		reading.setId(id);
		reading.setValue(value);
		reading.setDate(date);
		
		return importReading(reading);
	}
	
	/**
	 * Adds the specified reading in to the currently selected patient's journal. The currently
	 * selected patient must be a valid patient reference. Adding differs from importing in that a patient
	 * simply has to be in the trial's list in order to import a reading for it whereas to add a reading, the
	 * patient must be currently active in the trial.
	 * 
	 * @param reading the reading to add to the currently selected patient.
	 * 
	 * @return true if the reading was imported; otherwise false.
	 */
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
	
	/**
	 * Adds the specified reading in to the currently selected patient's journal. The currently
	 * selected patient must be a valid patient reference. Adding differs from importing in that a patient
	 * simply has to be in the trial's list in order to import a reading for it whereas to add a reading, the
	 * patient must be currently active in the trial.
	 * 
	 * @param type the type of reading that is being added
	 * @param id the id of the reading being added
	 * @param value the value of the reading being added
	 * @param date the date the reading was taken
	 * @return true if the reading was imported; otherwise false.
	 */
	public boolean addReading(String type, String id, Object value, LocalDateTime date) {
		Reading reading = ReadingFactory.getReading(type);
		reading.setPatientId(selectedPatient.getId());
		reading.setId(id);
		reading.setValue(value);
		reading.setDate(date);
		
		return addReading(reading);
	}

	/**
	 * Returns a reference to a patient for the specified patient id. If the patient doesn't exist
	 * the null reference is returned.
	 *  
	 * @param patientId the patient id whose reference we are to retrieve. Cannot be null.
	 * 
	 * @return the requested patient reference or null if the patient could not be found in this trial.
	 */
	public Patient getPatient(String patientId) {
		return trial.getPatient(patientId);
	}

	/**
	 * Returns the id of the trial that this model is associated with.
	 *  
	 * @return the id of the trial that this model is associated with.
	 */
	public String getTrialId() {
		return trial.getId();
	}
	
	/**
	 * Returns true if the patient is considered to be in the trial. Please note
	 * that a return value of true does not mean the patient is currently active in
	 * the trial, it just means that the patient is considered to be or have been a
	 * part of this trial.
	 * 
	 * @param patient
	 *            The patient to check. Cannot be null.
	 * @return true if the patient is considered to be in the trial;
	 *         otherwise false.
	 */
	public boolean isPatientInTrial(Patient patient) {
		return trial.isPatientInTrial(patient);
	}
	
	/**
	 * Returns true if the specified patient has started this trial
	 * 
	 * @param patient
	 *            The patient to check. Cannot be null.
	 * @return answer true if the specified patient has started this trial
	 */
	public boolean hasPatientStartedTrial(Patient patient) {
		return trial.hasPatientStartedTrial(patient);
	}

	/**
	 * Returns true if the specified patient was added to this trial.
	 * 
	 * @param patientId The id of the patient to add. Cannot be null
	 * 
	 * @return answer true if the specified patient was added to this trial; otherwise false.
	 */
	public boolean addPatient(String patientId) {
		return addPatient(patientId, null);
	}
}
