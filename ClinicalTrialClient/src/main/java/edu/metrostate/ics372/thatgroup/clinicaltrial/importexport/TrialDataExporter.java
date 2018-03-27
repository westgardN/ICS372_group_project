/**
 * File: TrialDataExporter.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.io.OutputStream;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

/**
 * The TrialDataExporter interface is implemented by classes that support
 * exporting the trial data to an OutputStream.
 * 
 * Known implementing classes are TrialDataJsonImportExporter
 * 
 * @author That Group
 *
 */
public interface TrialDataExporter {
	/**
	 * Writes the Readings, Patients, and Clinics that have been set to the
	 * specified OutputStream.
	 * 
	 * @param os the OutputStream that will be written to during the export
	 * 
	 * @return true if the data was exported; otherwise false.
	 * 
	 * @throws TrialException indicates an error occurred while exporting.
	 */
	public boolean write(OutputStream os) throws TrialException;

	/**
	 * Sets the list of readings to export
	 * 
	 * @param readings the list of readings to export
	 */
	public void setReadings(List<Reading> readings);
	
	/**
	 * Sets the list of clinics to export
	 * 
	 * @param clinics the list of clinics to export
	 */
	public void setClinics(List<Clinic> clinics);
	
	/**
	 * Sets the list of patients to export
	 * 
	 * @param patients the list of patients to export
	 */
	public void setPatients(List<Patient> patients);	
}
