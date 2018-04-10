package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.io.InputStream;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Trial;

/**
 * The TrialDataImporter interface is implemented by classes that support
 * importing the trial data from an InputStream.
 * <p>
 * Known implementing classes are TrialDataJsonImportExporter and TrialDataXmlImporter
 *
 * @author That Group
 */
public interface TrialDataImporter {
    /**
     * Reads the Readings, Patients, and Clinics from the specified InputStream.
     * Returns true to indicate the import was a success and that the lists
     * are ready to be retrieved.
     *
     * @param trial the Trial that this data is being imported in to
     * @param is    the InputStream that will be read from during the import
     * @return true if the data was imported; otherwise false.
     * @throws TrialException indicates an error occurred while importing.
     */
    boolean read(Trial trial, InputStream is) throws TrialException;

    /**
     * Gets the list of readings that were imported.
     *
     * @return a List of Reading objects.
     */
    List<Reading> getReadings();

    /**
     * Gets the list of clinics that were imported.
     *
     * @return a List of Clinic objects.
     */
    List<Clinic> getClinics();

    /**
     * Gets the list of patients that were imported.
     *
     * @return a List of Patient objects.
     */
    List<Patient> getPatients();

}