package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

public class TrialDataJsonImportExporterTest {

	@Test
	public void testReadAndWrite() {
		System.out.println("Import / Export JSON Test: Importing...");
			
		String testFile = "./data/test_sample.json";
		List<Reading> readings = null;
		
		try (InputStream is = new FileInputStream(Paths.get(testFile).toFile());){
			Trial trial = new Trial();
			TrialDataImporter importer = new TrialDataJsonImportExporter();
			boolean read = importer.read(trial, is);
			
			assertTrue(read);
			
			readings = importer.getReadings();
			List<Clinic> clinics = importer.getClinics();
			List<Patient> patients = importer.getPatients();
			int expectedReadings = 13;
			int expectedClinics = 6;
			int expectedPatients = 6;

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
			e.printStackTrace();
		}
		
		if (readings != null) {
			testFile = "./data/testReadAndWrite_out.json";
			try (OutputStream os = new FileOutputStream(Paths.get(testFile).toFile());) {
				System.out.println("Import / Export JSON Test: Exporting...");
				TrialDataExporter exporter = new TrialDataJsonImportExporter();
				
				exporter.setReadings(readings);
				
				boolean write = exporter.write(os);
				
				assertTrue(write);
				
			} catch (IOException | TrialException e) {
				e.printStackTrace();
			}			
		}
	}
}
