/**
 * File: TrialDataExporter.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.io.OutputStream;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

/**
 * @author That Group
 *
 */
public interface TrialDataExporter {
	public void write(List<Reading> readings, OutputStream os) throws TrialException;
}
