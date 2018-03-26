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
 * @author That Group
 *
 */
public interface TrialDataExporter {
	public boolean write(OutputStream os) throws TrialException;
	
	public void setReadings(List<Reading> readings);
	
	public void setClinics(List<Clinic> clinics);
	
	public void setPatients(List<Patient> patients);	
}
