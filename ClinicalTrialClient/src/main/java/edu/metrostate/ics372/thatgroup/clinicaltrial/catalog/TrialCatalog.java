package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.util.List;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;

/**
 * The TrialCatalog interface is used to access the data of a specific trial.
 * You can check for the existence of, create, update, delete, and retrieve
 * data for clinics, patients, and readings.
 * 
 * @author That Group
 *
 */
public interface TrialCatalog {
	/**
	 * Initializes the trial catalog and sets the specified trial as the
	 * active trial. Returns true if the catalog was successfully initialized
	 * and is ready to be used; otherwise false is returned.
	 * 
	 * @param trial 
	 * 			  the id of the <code>Trial</code> to be retrieved and activated
	 * must be set. If the id field identifies a valid trial, the remaining
	 * fields of the specified trial are populated with the retrieved data, the
	 * trial becomes the active trial and the method returns true; otherwise false
	 * is returned.
	 * @return true if the catalog was successfully initialized and is ready
	 * to be used; otherwise false is returned.
	 * @throws TrialCatalogException indicates that trial is null or that an error
	 * occurred during the initialization process. 
	 */
	public boolean init() throws TrialCatalogException;

	/**
	 * Returns true if the catalog has been initialized; otherwise false is returned.
	 * 
	 * @return true if the catalog has been initialized; otherwise false is returned.
	 */
	public boolean isInit();
	
	/**
	 * Returns true if the specified clinic exists within this catalog;
	 * otherwise false is returned.
	 * 
	 * @param clinic
	 *            the id of the <code>Clinic</code> to be checked must be
	 *            set. If the id field identifies a valid clinic then the
	 *            method returns true; otherwise false is returned. 
	 * @return true if the id field identifies a valid clinic; otherwise
	 * false is returned.
	 * @throws TrialCatalogException indicates that clinic is null or
	 * another type of error occurred. 
	 */
	public boolean exists(Clinic clinic) throws TrialCatalogException;
	
	/**
	 * Inserts the specified <code>clinic</code> into the catalog. If the specified
	 * clinic already exists, a TrialCatalogException will be thrown. Returns true
	 * if the specified clinic was inserted in to the catalog; otherwise false is
	 * returned.
	 * 
	 * @param clinic
	 *            the <code>Clinic</code> to be insert in to the catalog.
	 * @return true if the specified clinic was inserted in to the catalog;
	 * otherwise false is returned.
	 * @throws TrialCatalogException indicates that clinic is null, the specified
	 * clinic id already exists, or another type of error occurred. 
	 */
	public boolean insert(Clinic clinic) throws TrialCatalogException;

	/**
	 * If the specified clinic id specifies a valid clinic, then a new Clinic is
	 * created and populated with the retrieved data and returned; otherwise null
	 * is returned.
	 * 
	 * @param clinic
	 *            the id of the <code>Clinic</code> to be retrieved must be
	 *            set. If the id field identifies a valid clinic, a new Clinic
	 *            is created and its fields are populated with the
	 *            retrieved data and returned; otherwise null is returned. 
	 * @return the specified clinic; otherwise null.
	 * @throws TrialCatalogException indicates that clinic is null or an error
	 * was encountered.
	 */
	public Clinic get(Clinic clinic) throws TrialCatalogException;

	/**
	 * Returns the default clinic for this trial.
	 * 
	 * @return the default clinic for this trial. 
	 * @throws TrialCatalogException indicates that an error
	 * was encountered.
	 */
	public Clinic getDefaultClinic() throws TrialCatalogException;
	
	/**
	 * Updates the existing clinic record, specified by the clinic id of
	 * the specified clinic, with the information contained within the
	 * specified clinic. Returns true if the updated information was saved;
	 * otherwise false is returned.
	 * 
	 * @param clinic
	 *            the clinic must not be null and the id of the specified clinic
	 *            must be a valid id for a clinic within this catalog. 
	 * @return true if the updated information was saved; otherwise false is returned.
	 * @throws TrialCatalogException indicates that clinic is null or an error
	 * was encountered.
	 */
	public boolean update(Clinic clinic) throws TrialCatalogException;

	/**
	 * Removes the specified clinic from the catalog. A clinic can only be removed
	 * from the catalog if it does not have any associated readings.
	 * 
	 * @param clinic 
	 *            the clinic must not be null and the id of the specified clinic
	 *            must be a valid id for a clinic within this catalog. 
	 * @return true if the clinic was removed from the catalog; otherwise false is returned.
	 * @throws TrialCatalogException indicates that clinic is null or an error
	 * was encountered such as the clinic having associated readings.
	 */
	public boolean remove(Clinic clinic) throws TrialCatalogException;

	/**
	 * Returns true if the specified patient exists within this catalog;
	 * otherwise false is returned.
	 * 
	 * @param patient
	 *            the id of the <code>Patient</code> to be checked must be
	 *            set. If the id field identifies a valid patient then the
	 *            method returns true; otherwise false is returned. 
	 * @return true if the id field identifies a valid patient; otherwise
	 * false is returned.
	 * @throws TrialCatalogException indicates that patient is null or
	 * another type of error occurred. 
	 */
	public boolean exists(Patient patient) throws TrialCatalogException;
	
	/**
	 * Inserts the specified <code>patient</code> into the catalog. If the specified
	 * patient already exists, a TrialCatalogException will be thrown. Returns true
	 * if the specified patient was inserted in to the catalog; otherwise false is
	 * returned.
	 * 
	 * @param patient
	 *            the <code>Patient</code> to be insert in to the catalog.
	 * @return true if the specified patient was inserted in to the catalog;
	 * otherwise false is returned.
	 * @throws TrialCatalogException indicates that patient is null, the specified
	 * patient id already exists, or another type of error occurred. 
	 */
	public boolean insert(Patient patient) throws TrialCatalogException;

	/**
	 * If the specified patient id specifies a valid patient, then a new Patient is
	 * created populated with the retrieved data and returned; otherwise null
	 * is returned.
	 * 
	 * @param patient
	 *            the id of the <code>Patient</code> to be retrieved must be
	 *            set. If the id field identifies a valid patient, a new Patient
	 *            is created and its fields are populated with the
	 *            retrieved data and the method returns a reference to it; otherwise
	 *            null is returned. 
	 * @return true if the specified patient was retrieved; otherwise false.
	 * @throws TrialCatalogException indicates that patient is null or an error
	 * was encountered.
	 */
	public Patient get(Patient patient) throws TrialCatalogException;


	/**
	 * Returns the default patient for this trial.
	 * 
	 * @return the default patient for this trial. 
	 * @throws TrialCatalogException indicates that an error
	 * was encountered.
	 */
	public Patient getDefaultPatient() throws TrialCatalogException;
	
	/**
	 * Updates the existing patient record, specified by the patient id of
	 * the specified patient, with the information contained within the
	 * specified patient. Returns true if the updated information was saved;
	 * otherwise false is returned.
	 * 
	 * @param patient
	 *            the patient must not be null and the id of the specified patient
	 *            must be a valid id for a patient within this catalog. 
	 * @return true if the updated information was saved; otherwise false is returned.
	 * @throws TrialCatalogException indicates that patient is null or an error
	 * was encountered.
	 */
	public boolean update(Patient patient) throws TrialCatalogException;

	/**
	 * Removes the specified patient from the catalog. A patient can only be removed
	 * from the catalog if it does not have any associated readings.
	 * 
	 * @param patient 
	 *            the patient must not be null and the id of the specified patient
	 *            must be a valid id for a patient within this catalog. 
	 * @return true if the patient was removed from the catalog; otherwise false is returned.
	 * @throws TrialCatalogException indicates that patient is null or an error
	 * was encountered such as the patient having associated readings.
	 */
	public boolean remove(Patient patient) throws TrialCatalogException;

	/**
	 * Returns true if the specified patient status exists within this catalog;
	 * otherwise false is returned.
	 * 
	 * @param patientStatus
	 *            the id of the <code>PatientStatus</code> to be checked must be
	 *            set. If the id field identifies a valid patient status then the
	 *            method returns true; otherwise false is returned. 
	 * @return true if the id field identifies a valid patient status; otherwise
	 * false is returned.
	 * @throws TrialCatalogException indicates that patient is null or
	 * another type of error occurred. 
	 */
	public boolean exists(PatientStatus patientStatus) throws TrialCatalogException;
	
	/**
	 * Inserts the specified <code>PatientStatus</code> into the catalog. If the specified
	 * patient status already exists, a TrialCatalogException will be thrown. Returns true
	 * if the specified patient status was inserted in to the catalog; otherwise an exception
	 * is thrown.
	 * 
	 * @param patientStatus
	 *            the <code>PatientStatus</code> to be insert in to the catalog.
	 * @return true if the specified patient status was inserted in to the catalog;
	 * otherwise an exception is returned.
	 * @throws TrialCatalogException indicates that patient status is null, the specified
	 * patient status id already exists, or another type of error occurred. 
	 */
	public boolean insert(PatientStatus patientStatus) throws TrialCatalogException;

	/**
	 * If the specified patient status id specifies a valid patient status, then a new PatientStatus is
	 * created and populated with the retrieved data and returned; otherwise null is returned.
	 * 
	 * @param patientStatus
	 *            the id of the <code>PatientStatus</code> to be retrieved must be
	 *            set. If the id field identifies a valid patient status, a new PatientStatus
	 *            is created and its fields are populated with the
	 *            retrieved data and the method returns a reference to it; otherwise
	 *            null is returned. 
	 * @return true if the specified patient status was retrieved; otherwise false.
	 * @throws TrialCatalogException indicates that patient status is null or an error
	 * was encountered.
	 */
	public PatientStatus get(PatientStatus patientStatus) throws TrialCatalogException;

	/**
	 * Returns the default patient status id for a patient.
	 * 
	 * @return the default patient status id for a patient. 
	 * @throws TrialCatalogException indicates that an error
	 * was encountered.
	 */
	public String getDefaultPatientStatusId() throws TrialCatalogException;
	
	/**
	 * Updates the existing patient status record. Returns true if the update was successful; otherwise fals.
	 * 
	 * @param patientStatus
	 *            the patient status must not be null and the id of the specified patient status
	 *            must be a valid id for a patient status within this catalog. 
	 * @return true if the updated information was saved; otherwise false is returned.
	 * @throws TrialCatalogException indicates that patient status is null or an error
	 * was encountered.
	 */
	public boolean update(PatientStatus patientStatus) throws TrialCatalogException;

	/**
	 * Removes the specified patient status from the catalog. A patient status can only be removed
	 * from the catalog if it does not have any associated patients.
	 * 
	 * @param patientStatus 
	 *            the patientStatus must not be null and the id of the specified patient status
	 *            must be a valid id for a patient status within this catalog. 
	 * @return true if the patient status was removed from the catalog; otherwise false is returned.
	 * @throws TrialCatalogException indicates that patient status is null or an error
	 * was encountered such as the patient status having associated patients.
	 */
	public boolean remove(PatientStatus patientStatus) throws TrialCatalogException;

	/**
	 * Returns true if the specified reading exists within this catalog;
	 * otherwise false is returned.
	 * 
	 * @param reading
	 *            the id of the <code>Reading</code> to be checked must be
	 *            set. If the id field identifies a valid reading then the
	 *            method returns true; otherwise false is returned. 
	 * @return true if the id field identifies a valid reading; otherwise
	 * false is returned.
	 * @throws TrialCatalogException indicates that reading is null or
	 * another type of error occurred. 
	 */
	public boolean exists(Reading reading) throws TrialCatalogException;
	
	/**
	 * Inserts the specified <code>reading</code> into the catalog. If the specified
	 * reading already exists, a TrialCatalogException will be thrown. Returns true
	 * if the specified reading was inserted in to the catalog; otherwise false is
	 * returned.
	 * 
	 * @param reading
	 *            the <code>Reading</code> to be insert in to the catalog.
	 * @return true if the specified reading was inserted in to the catalog;
	 * otherwise false is returned.
	 * @throws TrialCatalogException indicates that reading is null, the specified
	 * reading id already exists, or another type of error occurred. 
	 */
	public boolean insert(Reading reading) throws TrialCatalogException;

	/**
	 * If the specified reading id specifies a valid reading, then a new Reading is
	 * created and populated with the retrieved data and returned; otherwise null
	 * is returned.
	 * 
	 * @param reading
	 *            the id of the <code>Reading</code> to be retrieved must be
	 *            set. If the id field identifies a valid reading, a new Reading
	 *            is created and populated with the retrieved data and the method
	 *            returns a reference to the new reading; otherwise null is returned. 
	 * @return true if the specified reading was retrieved; otherwise false.
	 * @throws TrialCatalogException indicates that reading is null or an error
	 * was encountered.
	 */
	public Reading get(Reading reading) throws TrialCatalogException;

	/**
	 * Updates the existing reading record, specified by the reading id of
	 * the specified patient, with the information contained within the
	 * specified reading. Returns true if the updated information was saved;
	 * otherwise false is returned.
	 * 
	 * @param reading
	 *            the reading must not be null and the id of the specified reading
	 *            must be a valid id for a reading within this catalog. 
	 * @return true if the updated information was saved; otherwise false is returned.
	 * @throws TrialCatalogException indicates that reading is null or an error
	 * was encountered.
	 */
	public boolean update(Reading reading) throws TrialCatalogException;

	/**
	 * Removes the specified reading from the catalog. 
	 * 
	 * @param reading 
	 *            the reading must not be null and the id of the specified reading
	 *            must be a valid id for a reading within this catalog. 
	 * @return true if the reading was removed from the catalog; otherwise false is returned.
	 * @throws TrialCatalogException indicates that reading is null or an error
	 * was encountered.
	 */
	public boolean remove(Reading patient) throws TrialCatalogException;

	/**
	 * Returns a List&lt;Clinic&gt; of all the clinics in the active trial.
	 * 
	 * @return a List&lt;Clinic&gt; of all the clinics in the active trial.
	 * @throws TrialCatalogException indicates there is no active trial or
	 * an error was encountered.
	 */
	public List<Clinic> getClinics() throws TrialCatalogException;

	/**
	 * Returns a List&lt;Patient&gt; of all the patients in the active trial.
	 * 
	 * @return a List&lt;Patient&gt; of all the patients in the active trial.
	 * @throws TrialCatalogException indicates there is no active trial or
	 * an error was encountered.
	 */
	public List<Patient> getPatients() throws TrialCatalogException;

	/**
	 * Returns a List&lt;PatientStatus&gt; of all the patient statuses.
	 *  
	 * @return a List&lt;PatientStatus&gt; of all the patient statuses.
	 * 
	 * @throws TrialCatalogException indicates there is no active trial or
	 * an error was encountered.
	 */
	public List<PatientStatus> getPatientStatuses() throws TrialCatalogException;
	
	/**
	 * Returns a List&lt;Patient&gt; of all the patients that are currently
	 * active in the active trial.
	 * 
	 * @return a List&lt;Patient&gt; of all the patients that are currently
	 * active in the active trial.
	 * @throws TrialCatalogException indicates there is no active trial or
	 * an error was encountered.
	 */
	public List<Patient> getActivePatients() throws TrialCatalogException;
	
	/**
	 * Returns a List&lt;Patient&gt; of all the patients that are currently
	 * not active in the active trial.
	 * 
	 * @return a List&lt;Patient&gt; of all the patients that are currently
	 * not active in the active trial.
	 * @throws TrialCatalogException indicates there is no active trial or
	 * an error was encountered.
	 */
	public List<Patient> getInactivePatients() throws TrialCatalogException;
	
	/**
	 * Returns a List&lt;Reading&gt; of all the readings in the active trial.
	 * 
	 * @return a List&lt;Reading&gt; of all the readings in the active trial.
	 * @throws TrialCatalogException indicates there is no active trial or
	 * an error was encountered.
	 */
	public List<Reading> getReadings() throws TrialCatalogException;

	/**
	 * Returns a List&lt;Reading&gt; of all the readings in the active trial
	 * for the specified patient. The specified patient must contain a valid
	 * id and must exist in this catalog.
	 * 
	 * @param patient the specified patient to retrieve the readings for.
	 * the specified patient cannot be null and must have a valid id.
	 * @return a List&lt;Reading&gt; of all the readings in the active trial
	 * for the specified patient.
	 * @throws TrialCatalogException indicates that patient is null, there
	 * is no active trial or an error was encountered.
	 */
	public List<Reading> getReadings(Patient patient) throws TrialCatalogException;

	/**
	 * Returns a List&lt;Reading&gt; of all the readings in the active trial
	 * for the specified clinic. The specified clinic must contain a valid
	 * id and must exist in this catalog.
	 * 
	 * @param clinic the specified clinic to retrieve the readings for.
	 * the specified clinic cannot be null and must have a valid id.
	 * @return a List&lt;Reading&gt; of all the readings in the active trial
	 * for the specified clinic.
	 * @throws TrialCatalogException indicates that clinic is null, there
	 * is no active trial or an error was encountered.
	 */
	public List<Reading> getReadings(Clinic clinic) throws TrialCatalogException;
}
