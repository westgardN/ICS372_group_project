package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.IOException;
import java.sql.SQLException;
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
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public boolean init(Trial trial) throws TrialCatalogException;

	public boolean isInit();
	
	/**
	 * Inserts a new <code>Clinic</code> into the "clinics" table of the currently
	 * selected <code>ClinicalTrialCatalog</code>
	 * 
	 * @param clinic
	 *            the <code>Clinic</code> to be inserted
	 * @return
	 * @throws TrialCatalogException 
	 */
	public boolean insertClinic(Clinic clinic) throws TrialCatalogException;

	/**
	 * Gets a new <code>Clinic</code> object from the "clinics" table of the
	 * currently selected <code>ClinicalTrialCatalog</code>
	 * 
	 * @param clinicId
	 *            the id of the <code>Clinic</code> to be selected
	 * @return a new <code>Clinic</code> object derived from the
	 *         <code>clinicId</code>
	 */
	public Clinic getClinic(String clinicId) throws TrialCatalogException;

	/**
	 * Updates the information of the corresponding <code>Clinic</code> record in
	 * the currently selected <code>ClinicalTrialCatalog</code>
	 * 
	 * @param clinic
	 *            the <code>Clinic</code> to be updated
	 * @return true if the update was successful, else false
	 */
	public boolean updateClinic(Clinic clinic) throws TrialCatalogException;

	/**
	 * 
	 * @param clinic
	 * @return
	 */
	public boolean removeClinic(Clinic clinic) throws TrialCatalogException;

	/**
	 * 
	 * @param patient
	 * @return
	 */
	public boolean insertPatient(Patient patient) throws TrialCatalogException;

	/**
	 * 
	 * @param patientId
	 * @return
	 */
	public Patient getPatient(String patientId) throws TrialCatalogException;

	/**
	 * 
	 * @param patient
	 * @return
	 */
	public boolean updatePatient(Patient patient) throws TrialCatalogException;

	/**
	 * 
	 * @param patient
	 * @return
	 */
	public boolean removePatient(Patient patient) throws TrialCatalogException;

	/**
	 * 
	 * @param patient
	 * @param reading
	 * @return
	 */
	public boolean insertReading(Reading reading) throws TrialCatalogException;

	/**
	 * 
	 * @param readingId
	 * @return
	 */
	public Reading getReading(String readingId) throws TrialCatalogException;

	/**
	 * 
	 * @param reading
	 * @return
	 */
	boolean updateReading(Reading reading) throws TrialCatalogException;

	/**
	 * 
	 * @param reading
	 * @return
	 */
	public boolean removeReading(Reading reading) throws TrialCatalogException;

	/**
	 * 
	 * @return
	 */
	public List<String> getAllTrialCatalogNamesInDirectory();

	/**
	 * 
	 * @return
	 */
	public List<Clinic> getAllClinics() throws TrialCatalogException;

	/**
	 * 
	 * @return
	 */
	public List<Patient> getAllPatients() throws TrialCatalogException;

	/**
	 * 
	 * @return
	 */
	public List<Reading> getAllReadings() throws TrialCatalogException;

	/**
	 * 
	 * @param patient
	 * @return
	 */
	public List<Reading> getReadingsForPatient(Patient patient) throws TrialCatalogException;

	/**
	 * 
	 * @param clinic
	 * @return
	 */
	public List<Reading> getReadingsForClinic(Clinic clinic) throws TrialCatalogException;
}
