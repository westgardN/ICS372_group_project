package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.util.List;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

/**
 * @author ThatGroup
 *
 */
public interface TrialCatalog {
	/**
	 * Initializes the directory to where the <code>ClinicalTrialCatalog</code>'s
	 * will be stored. If the Directory does not exist, it will be created
	 * 
	 * @return true if the directory was successfully created, else false
	 */
	boolean init();

	/**
	 * Creates a new ClinicalTrialCatalog in the catalogs directory.
	 * 
	 * @param trial
	 *            the <code>Trial</code> that is to be made into a Trial Catalog
	 * @return true if the <code>ClinicalTrialCatalog</code> creation was
	 *         successful, else false
	 */
	boolean createTrialCatalog(Trial trial);

	/**
	 * Inserts a new <code>Clinic</code> into the "clinics" table of the currently
	 * selected <code>ClinicalTrialCatalog</code>
	 * 
	 * @param clinic
	 *            the <code>Clinic</code> to be inserted
	 * @return
	 */
	boolean insertClinic(Clinic clinic);

	/**
	 * Gets a new <code>Clinic</code> object from the "clinics" table of the
	 * currently selected <code>ClinicalTrialCatalog</code>
	 * 
	 * @param clinicId
	 *            the id of the <code>Clinic</code> to be selected
	 * @return a new <code>Clinic</code> object derived from the
	 *         <code>clinicId</code>
	 */
	Clinic getClinic(String clinicId);

	/**
	 * Updates the information of the corresponding <code>Clinic</code> record in
	 * the currently selected <code>ClinicalTrialCatalog</code>
	 * 
	 * @param clinic
	 *            the <code>Clinic</code> to be updated
	 * @return true if the update was successful, else false
	 */
	boolean updateClinic(Clinic clinic);

	/**
	 * 
	 * @param clinic
	 * @return
	 */
	boolean removeClinic(Clinic clinic);

	/**
	 * 
	 * @param patient
	 * @return
	 */
	boolean insertPatient(Patient patient);

	/**
	 * 
	 * @param patientId
	 * @return
	 */
	Patient getPatient(String patientId);

	/**
	 * 
	 * @param patient
	 * @return
	 */
	boolean updatePatient(Patient patient);

	/**
	 * 
	 * @param patient
	 * @return
	 */
	boolean removePatient(Patient patient);

	/**
	 * 
	 * @param patient
	 * @param reading
	 * @return
	 */
	boolean insertReading(Reading reading);

	/**
	 * 
	 * @param readingId
	 * @return
	 */
	Reading getReading(String readingId);

	/**
	 * 
	 * @param reading
	 * @return
	 */
	boolean updateReading(Reading reading);

	/**
	 * 
	 * @param reading
	 * @return
	 */
	boolean removeReading(Reading reading);

	/**
	 * 
	 * @return
	 */
	List<String> getAllTrialCatalogNamesInDirectory();

	/**
	 * 
	 * @return
	 */
	List<Clinic> getAllClinics();

	/**
	 * 
	 * @return
	 */
	List<Patient> getAllPatients();

	/**
	 * 
	 * @return
	 */
	List<Reading> getAllReadings();

	/**
	 * 
	 * @param patient
	 * @return
	 */
	List<Reading> getReadingsForPatient(Patient patient);

	/**
	 * 
	 * @param clinic
	 * @return
	 */
	List<Reading> getReadingsForClinic(Clinic clinic);
}
