/**
 * File: TrialDataImporter.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.io.InputStream;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

/**
 * @author That Group
 *
 */
public interface TrialDataImporter {
	public boolean read(Trial trial, InputStream is) throws TrialException;
	
	public List<Reading> getReadings();
	
	public List<Clinic> getClinics();
	
	public List<Patient> getPatients();
	
}
