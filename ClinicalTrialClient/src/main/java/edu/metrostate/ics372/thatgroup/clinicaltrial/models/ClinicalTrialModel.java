/**
 * File: ClinicalTrialModel.java
 */

package edu.metrostate.ics372.thatgroup.clinicaltrial.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalog;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialManager;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ReadingFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The ClinicalTrialModel is a class that provides a central means of access to
 * all of the information that a clinical trial provides. The model is shared among all
 * of the views. At this time, the ClinicalTrialModel is not considered thread safe but,
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
public class ClinicalTrialModel {
	/**
	 * The readings property.
	 */
	public static final String PROP_READINGS = "readings";
	
	/**
	 * The patients property.
	 */
	public static final String PROP_PATIENTS = "patients";
	
	/**
	 * The patients property.
	 */
	public static final String PROP_CLINICS = "clinics";
	
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
	 * The selectedPatient property.
	 */
	public static final String PROP_SELECTED_CLINIC = "selectedClinic";
	
	/**
	 * The updatePatient property.
	 */
	public static final String PROP_UPDATE_PATIENT = "updatePatient";
	
	/**
	 * The updatePatient property.
	 */
	public static final String PROP_UPDATE_CLINIC = "updateClinic";
	
	private static final String DEFAULT_TRIAL_NAME = "default";
	private transient final PropertyChangeSupport pcs;
	private Trial trial;
	private Patient selectedPatient;
	private Clinic selectedClinic;
	private Clinic defaultClinic;
	private Reading selectedReading;
	
	private ObservableList<Patient> patients;
	private ObservableList<Clinic> clinics;
	private ObservableList<Reading> journal;
	private ObservableList<String> readingTypes = FXCollections.observableArrayList(ReadingFactory.getPrettyReadingTypes());

	private TrialCatalog catalog;
	/**
	 * Initializes a new empty view model with the id of the trial set to the default trial name of ""
	 * @throws TrialCatalogException 
	 */
	public ClinicalTrialModel() throws TrialCatalogException {
		this(DEFAULT_TRIAL_NAME);
	}

	/**
	 * Initializes a new empty view model with the id of the trial set to the specified name.
	 * @param name the id of this trial
	 * @throws TrialCatalogException 
	 */
	public ClinicalTrialModel(String name) throws TrialCatalogException {
		trial = new Trial(name);
		catalog = TrialManager.getInstance().getTrialCatalog(trial);
		patients = FXCollections.observableArrayList(catalog.getPatients());
		clinics = FXCollections.observableArrayList(catalog.getClinics());
		defaultClinic = catalog.getDefaultClinic();
		selectedPatient = null;
		selectedClinic = null;
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
	 * @return the clinic that is currently selected, which may be null. 
	 * 
	 */
	public Clinic getSelectedClinic() {
		return selectedClinic;
	}

	/**
	 * @return the clinic that is the default clinic and is never null; 
	 * 
	 */
	public Clinic getDefaultClinic() {
		return defaultClinic;
	}

	/**
	 * @return the reading that is currently selected, which may be null. 
	 * 
	 */
	public Reading getSelectedReading() {
		return selectedReading;
	}
	
	/**
	 * Notifies any registered listeners that the specified patient has been updated, if the patient
	 * is in the trial's list of patients. Otherwise nothing happens.
	 * 
	 * @param patient the patient that was updated.
	 * @throws TrialCatalogException 
	 */
	public void fireUpdatePatient(Patient patient) throws TrialCatalogException {
		if (patient != null && catalog.exists(patient)) {
			Patient oldValue = null;
			pcs.firePropertyChange(PROP_UPDATE_PATIENT, oldValue, patient);
		}
	}
	
	/**
	 * Notifies any registered listeners that the specified patient has been updated, if the patient
	 * is in the trial's list of patients. Otherwise nothing happens.
	 * 
	 * @param patientId the patient that was updated.
	 * @throws TrialCatalogException 
	 */
	public void fireUpdatePatient(String patientId) throws TrialCatalogException {
		Patient patient = getPatient(patientId);
		fireUpdatePatient(patient);
	}
	
	/**
	 * Notifies any registered listeners that the specified clinic has been updated,
	 * if the clinic is in the trial's list of clinics. Otherwise nothing happens.
	 * 
	 * @param clinic the clinic that was updated.
	 * @throws TrialCatalogException 
	 */
	public void fireUpdateClinic(Clinic clinic) throws TrialCatalogException {
		if (clinic != null && catalog.exists(clinic)) {
			Clinic oldValue = null;
			pcs.firePropertyChange(PROP_UPDATE_CLINIC, oldValue, clinic);
		}
	}
	
	/**
	 * Notifies any registered listeners that the specified clinic has been updated,
	 * if the clinic is in the trial's list of clinics. Otherwise nothing happens.
	 * 
	 * @param clinicId the clinic that was updated.
	 * @throws TrialCatalogException 
	 */
	public void fireUpdateClinic(String clinicId) throws TrialCatalogException {
		Clinic clinic = getClinic(clinicId);
		fireUpdateClinic(clinic);
	}
	
	/**
	 * Sets the "selected" clinic to the specified clinic and notifies any listeners
	 * of this change. Typically the selected clinic is set in response to a UI event
	 * where the user selects a clinic from a list and that list then notifies this
	 * model of the change.
	 * 
	 * @param selectedClinic the clinic that is now considered to be the "selected"
	 * clinic. 
	 * @throws TrialCatalogException 
	 */
	public void setSelectedClinic(Clinic selectedClinic) throws TrialCatalogException {
		setSelectedClinic(selectedClinic, true);
	}

	/**
	 * Sets the "selected" clinic to the specified clinic and if notify is set to true,
	 * notifies any listeners of this change. Typically the selected clinic is set in
	 * response to a UI event where the user selects a clinic from a list and that list
	 * then notifies this model of the change.
	 * 
	 * @param selectedClinic the clinic that is now considered to be the "selected"
	 * clinic.
	 * @param notify is set to true any listeners are notified of this change. 
	 * @throws TrialCatalogException 
	 */
	public void setSelectedClinic(Clinic selectedClinic, boolean notify) throws TrialCatalogException {
		if (!Objects.equals(this.selectedClinic, selectedClinic)) {
			Clinic oldValue = this.selectedClinic;
			this.selectedClinic = selectedClinic;
			if (notify) {
				pcs.firePropertyChange(PROP_SELECTED_CLINIC, oldValue, this.selectedClinic);
			}
			
			setJournal(this.selectedClinic, notify);
		}
	}

	/**
	 * Sets the "selected" patient to the specified patient and notifies any listeners
	 * of this change. Typically the selected patient is set in response to a UI event
	 * where the user selects a patient from a list and that list then notifies this
	 * model of the change.
	 * 
	 * @param selectedPatient the patient that is now considered to be the "selected"
	 * patient. 
	 * @throws TrialCatalogException 
	 */
	public void setSelectedPatient(Patient selectedPatient) throws TrialCatalogException {
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
	 * @throws TrialCatalogException 
	 */
	public void setSelectedPatient(Patient selectedPatient, boolean notify) throws TrialCatalogException {
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

	public Clinic getSelectedOrDefaultClinic() {
		return selectedClinic != null ? selectedClinic : defaultClinic;
	}
	
	/**
	 * @return the reference to the observable list of patients that can directly be listened to for changes.
	 */
	public ObservableList<Patient> getPatients() {
		return patients;
	}

	/**
	 * @return the reference to the observable list of clinics that can directly be listened to for changes.
	 */
	public ObservableList<Clinic> getClinics() {
		return clinics;
	}

	/**
	 * @return the reference to the observable list of readings known as a journal, that can directly be
	 * listened to for changes.
	 */
	public ObservableList<Reading> getJournal() {
		return journal;
	}

	/**
	 * @return A list of readings for the specified patient
	 * 
	 * @throws TrialCatalogException 
	 */
	public List<Reading> getJournal(Patient patient) throws TrialCatalogException {
		return catalog.getReadings(patient);
	}

	/**
	 * @return A list of readings for the specified clinic
	 * 
	 * @throws TrialCatalogException 
	 */
	public List<Reading> getJournal(Clinic clinic) throws TrialCatalogException {
		return catalog.getReadings(clinic);
	}

	/**
	 * Sets the observable journal to be that of the specified clinic.
	 * 
	 * @param clinic the clinic's whose journal we are observing.
	 * @throws TrialCatalogException 
	 */
	public void setJournal(Clinic clinic) throws TrialCatalogException {
		setJournal(clinic, true);
	}
	
	/**
	 * Sets the observable journal to be that of the specified clinic.
	 * 
	 * @param clinic the clinic's whose journal we are observing.
	 * @param notify if set to true then any registered listeners are notified
	 * via a PROP_JOURNAL change notification.
	 * @throws TrialCatalogException 
	 */
	public void setJournal(Clinic clinic, boolean notify) throws TrialCatalogException {
		ObservableList<Reading> oldValue = journal;
		journal = clinic != null ? FXCollections.observableArrayList(catalog.getReadings(clinic)) : null;
		
		if (notify) {
			pcs.firePropertyChange(PROP_JOURNAL, oldValue, journal);
		}
	}

	/**
	 * Sets the observable journal to be that of the specified patient.
	 * 
	 * @param patient the patient's whose journal we are observing.
	 * @throws TrialCatalogException 
	 */
	public void setJournal(Patient patient) throws TrialCatalogException {
		setJournal(patient, true);
	}
	
	/**
	 * Sets the observable journal to be that of the specified patient.
	 * 
	 * @param patient the patient's whose journal we are observing.
	 * @param notify if set to true then any registered listeners are notified
	 * via a PROP_JOURNAL change notification.
	 * @throws TrialCatalogException 
	 */
	public void setJournal(Patient patient, boolean notify) throws TrialCatalogException {
		ObservableList<Reading> oldValue = journal;
		journal = patient != null ? FXCollections.observableArrayList(catalog.getReadings(patient)) : null;
		
		if (notify) {
			pcs.firePropertyChange(PROP_JOURNAL, oldValue, journal);
		}
	}

	/**
	 * @return the reference to the observable list of reading types that can directly be
	 * listened to for changes.
	 */
	public ObservableList<String> getReadingTypes() {
		return readingTypes;
	}

	/**
	 * Adds the specified clinic to this model's trial with the specified name.
	 * Returns true if the clinic was added and false if the clinic was not added. A clinic
	 * can only be added to a trial once. Fires a PROP_CLINICS change notification
	 * 
	 * @param clinicId the id of the clinic to add 
	 * @param name the name of the clinic, which may be null if the clinic doesn't
	 * have a name.
	 * @return true if the clinic was added and false if the clinic was not added.
	 * @throws TrialCatalogException 
	 */
	public boolean addClinic(String clinicId, String name) throws TrialCatalogException {
		Clinic clinic = new Clinic(clinicId, getTrialId(), name);
		boolean answer = false;
		
		if (!catalog.exists(clinic)) {
			answer = catalog.insert(clinic);
			if (answer) {
				int oldValue = clinics.size();
				pcs.firePropertyChange(PROP_CLINICS, oldValue, oldValue + 1);
				clinics.add(clinic);
			}
		}
		return answer;
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
	 * @throws TrialCatalogException 
	 */
	public boolean addPatient(String patientId, LocalDate startDate) throws TrialCatalogException {
		Patient patient = new Patient(patientId, getTrialId(), startDate, null);
		boolean answer = false;
		
		if (!catalog.exists(patient)) {
			answer = catalog.insert(patient);
			if (answer) {
				int oldValue = patients.size();
				pcs.firePropertyChange(PROP_PATIENTS, oldValue, oldValue + 1);
				patients.add(patient);
			}
		}
		return answer;
	}
	
	public boolean update(Patient patient) throws TrialCatalogException {
		boolean answer = false;
		
		if (patient != null) {
			if (catalog.exists(patient)) {
				answer = catalog.update(patient);
				if (answer) {
					pcs.firePropertyChange(PROP_UPDATE_PATIENT, null, patient);
				}
			} else {
				answer = catalog.insert(patient);
				if (answer) {
					int oldValue = patients.size();
					pcs.firePropertyChange(PROP_PATIENTS, oldValue, oldValue + 1);
					patients.add(patient);
				}
			}
		}
		
		return answer;
	}
	
	/**
	 * Imports the specified reading. The currently selected patient and currently selected clinic
	 * must be a valid patient and clinic reference. Importing differs from adding in that a patient
	 * simply has to be in the trial's list in order to import a reading for it whereas to add a reading, the
	 * patient must be currently active in the trial or the reading date must be within the active dates
	 * that the patient was in the trial.
	 * 
	 * @param reading the reading to import.
	 * 
	 * @return true if the reading was imported; otherwise false.
	 * @throws TrialCatalogException 
	 */
	public boolean importReading(Reading reading) throws TrialCatalogException {
		boolean answer = false;
		
		if (reading != null) {
			if (!catalog.exists(reading)) {
				answer = catalog.insert(reading);
				if (answer) {
					int oldValue = journal.size();
					pcs.firePropertyChange(PROP_READINGS, oldValue, oldValue + 1);
					journal.add(reading);
				}
			}
		}
		return answer;
	}
	
	/**
	 * Imports the specified reading. The currently selected patient and currently selected clinic
	 * must be a valid patient and clinic reference. Importing differs from adding in that a patient
	 * simply has to be in the trial's list in order to import a reading for it whereas to add a reading, the
	 * patient must be currently active in the trial.
	 * 
	 * @param type the type of reading that is being added
	 * @param id the id of the reading being added
	 * @param value the value of the reading being added
	 * @param date the date the reading was taken
	 * @return true if the reading was imported; otherwise false.
	 * @throws TrialCatalogException 
	 */
	public boolean importReading(String type, String id, Object value, LocalDateTime date) throws TrialCatalogException {
		Reading reading = ReadingFactory.getReading(type);
		reading.setPatientId(selectedPatient.getId());
		reading.setClinicId(getSelectedOrDefaultClinic().getId());
		reading.setId(id);
		reading.setValue(value);
		reading.setDate(date);
		
		return importReading(reading);
	}
	
	/**
	 * Adds the specified reading. The currently selected patient and currently selected
	 * clinic must be a valid patient and clinic reference. Adding differs from
	 * importing in that a patient must be currently active in the trial.
	 * 
	 * @param reading the reading to add.
	 * 
	 * @return true if the reading was imported; otherwise false.
	 * @throws TrialCatalogException 
	 */
	public boolean addReading(Reading reading) throws TrialCatalogException {
		boolean answer = false;
		
		if (canAddReading(reading)) {
			answer = catalog.insert(reading);
			if (answer) {
				int oldValue = journal.size();
				pcs.firePropertyChange(PROP_READINGS, oldValue, oldValue + 1);
				journal.add(reading);
				
			}
		}
		return answer;
	}
	
	/**
	 * Adds the specified reading. The currently selected patient and currently selected
	 * clinic must be a valid patient and clinic reference. Adding differs from
	 * importing in that a patient must be currently active in the trial.
	 * 
	 * @param type the type of reading that is being added
	 * @param id the id of the reading being added
	 * @param value the value of the reading being added
	 * @param date the date the reading was taken
	 * @return true if the reading was imported; otherwise false.
	 * @throws TrialCatalogException 
	 */
	public boolean addReading(String type, String id, Object value, LocalDateTime date) throws TrialCatalogException {
		Reading reading = ReadingFactory.getReading(type);
		reading.setPatientId(selectedPatient.getId());
		reading.setClinicId(getSelectedOrDefaultClinic().getId());
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
	 * @throws TrialCatalogException 
	 */
	public Patient getPatient(String patientId) throws TrialCatalogException {
		Patient patient = new Patient(patientId, getTrialId(), null, null);
		Patient answer = null;
		
		if (catalog.exists(patient)) {
			answer = catalog.get(patient);
		}
		
		return answer;
	}

	/**
	 * Returns a reference to a clinic for the specified clinic id. If the clinic doesn't exist
	 * the null reference is returned.
	 *  
	 * @param clinicId the clinic id whose reference we are to retrieve. Cannot be null.
	 * 
	 * @return the requested clinic reference or null if the clinic could not be found in this trial.
	 * @throws TrialCatalogException 
	 */
	public Clinic getClinic(String clinicId) throws TrialCatalogException {
		Clinic clinic = new Clinic(clinicId, getTrialId(), null);
		Clinic answer = null;
		
		if (catalog.exists(clinic)) {
			answer = catalog.get(clinic);
		}
		
		return answer;
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
	 * Adds the specified patient to this model's trial with no start date.
	 * Returns true if the patient was added and false if the patient was not added. A patient
	 * can only be added to a trial once. Fires a PROP_PATIENTS change notification
	 * 
	 * @param patientId The id of the patient to add. Cannot be null
	 * 
	 * @return answer true if the specified patient was added to this trial; otherwise false.
	 * @throws TrialCatalogException 
	 */
	public boolean addPatient(String patientId) throws TrialCatalogException {
		return addPatient(patientId, null);
	}
	
	/**
	 * Returns true if the specified reading can be added to the selected patient.
	 * 
	 * @param reading the reading to add.
	 * @return true if the specified reading can be added to the selected patient.
	 * @throws TrialCatalogException
	 */
	public boolean canAddReading(Reading reading) throws TrialCatalogException {
		boolean answer = false;
		
		if (reading != null && reading.getDate() != null && selectedPatient != null) {
			LocalDate endDate = selectedPatient.getTrialEndDate();
			if (!catalog.exists(reading)) {
				if (endDate != null && endDate.compareTo(reading.getDate().toLocalDate()) >= 0) {
					answer = true;
				} else if (hasPatientStartedTrial(selectedPatient)){
					answer = true;
				}
			}
			
		}
		
		return answer;
	}
}