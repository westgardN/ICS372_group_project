/**
 * File: TrialDataExporter.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;

/**
 * @author That Group
 *
 */
public interface TrialDataExporter {
	public void write(List<Reading> readings, String filePathOut);
}
