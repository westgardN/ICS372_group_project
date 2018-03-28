/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

/**
 * @author Beansy
 *
 */
public class TrialDataXmlImporterTest {

	@Test
	public void testXmlRead() {
		System.out.println("Import / Export XML Test: Importing...");
		
		String testFile = "./data/test_sample.xml";
		
		try (InputStream is = new FileInputStream(Paths.get(testFile).toFile());){
			Trial trial = new Trial();
			TrialDataImporter importer = new TrialDataXmlImporter();
			boolean read = importer.read(trial, is);
			
			assertTrue(read);
			
			List<Reading> readings = importer.getReadings();
			List<Clinic> clinics = importer.getClinics();
			List<Patient> patients = importer.getPatients();
			int expectedReadings = 5;
			int expectedClinics = 1;
			int expectedPatients = 4;
			
			assertNotNull(readings);
			assertNotNull(clinics);
			assertNotNull(patients);
			assertFalse(readings.isEmpty());
			assertFalse(clinics.isEmpty());
			assertFalse(patients.isEmpty());
			assertEquals(expectedReadings, readings.size());
			assertEquals(expectedClinics, clinics.size());
			assertEquals(expectedPatients, patients.size());
			
			System.out.println("Clinics");
			for (Clinic clinic : clinics) {
				System.out.println(clinic);
			}
			
			System.out.println("Patients");
			for (Patient patient : patients) {
				System.out.println(patient);
			}
			
			System.out.println("Readings");
			for (Reading reading : readings) {
				System.out.println(reading);
			}
		} catch (IOException | TrialException e) {
			System.out.println(e.getMessage());
		}
	}

}
