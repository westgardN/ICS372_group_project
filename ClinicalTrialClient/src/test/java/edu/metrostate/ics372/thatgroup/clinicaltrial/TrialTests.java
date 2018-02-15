package edu.metrostate.ics372.thatgroup.clinicaltrial;

import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.PatientFactory;

public class TrialTests {

	@Test
	public void testConstruction() {
		Trial trial = new Trial();
		assertNotNull(trial);
		assertEquals("", trial.getId());
		Trial trial1 = new Trial("abc");
		assertNotNull(trial1);
		assertEquals("abc", trial1.getId());
	}

	@Test
	public void testAddPropertyChangeListener() throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		Trial trial = new Trial();
		Field pcs = trial.getClass().getDeclaredField("pcs");
		pcs.setAccessible(true);
		pcs.set(trial, null);
		Method getPcs = trial.getClass().getDeclaredMethod("getPcs");
		getPcs.setAccessible(true);
		assertNotNull(getPcs);
		trial.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
	}

	@Test
	public void testAddPatient() {
		Trial trial = new Trial();
		Patient patient = PatientFactory.getPatient("clinical");
		patient.setId("foo");
		trial.addPatient(patient);
		assertEquals(1, trial.getNumPatients());
		trial.addPatient("bar");
		assertEquals(2, trial.getNumPatients());
		trial.addPatient("baz", LocalDate.now());
		assertEquals(3, trial.getNumPatients());
	}

	@Test
	public void testGetSetPatients() {
		Trial trial = new Trial();
		Patient patient;
		int numOfPatientsToAdd = 10;
		Set<Patient> patients = new HashSet<>();
		for (int i = 0; i < numOfPatientsToAdd; i++) {
			patient = PatientFactory.getPatient("clinical");
			patient.setId(String.format("%d", i));
			patients.add(patient);
		}
		trial.setPatients(patients);
		assertEquals(numOfPatientsToAdd, trial.getNumPatients());
		assertEquals(trial.getPatients(), patients);
	}

	@Test
	public void testHasPatientStartedTrial() {
		Trial trial = new Trial();
		trial.setId("foo");
		Patient patient = PatientFactory.getPatient("clinical");
		trial.addPatient(patient);
		assertFalse(trial.hasPatientStartedTrial(patient));
		patient.setTrialId("foo");
		patient.setTrialStartDate(LocalDate.now());
		patient.setTrialEndDate(null);
		assertTrue(trial.hasPatientStartedTrial(patient));
	}

	@Test
	public void testIsPatientInTrial() {
		Trial trial = new Trial();
		trial.setId("foo");
		Patient patient = PatientFactory.getPatient("clinical");
		assertFalse(trial.isPatientInTrial(patient));
		patient.setTrialId("foo");
		patient.setTrialStartDate(LocalDate.now());
		patient.setTrialEndDate(null);
		trial.addPatient(patient);
		assertTrue(trial.isPatientInTrial(patient));
	}

	@Test
	public void testHashCode() {
		Trial trial0 = new Trial();
		assertEquals(31, trial0.hashCode());
		trial0.setId("foo");
		assertEquals(31 + trial0.getId().toUpperCase().hashCode(), trial0.hashCode());
		trial0.setId(null);
		assertEquals(31, trial0.hashCode());
	}

	@Test
	public void testEquals() {
		Trial trial0 = new Trial();
		Trial trial1 = trial0;
		assertTrue(trial0.equals(trial1));
		trial0.setId("foo");
		assertTrue(trial0.equals(trial1));
		trial1 = new Trial();
		assertFalse(trial0.equals(trial1));
		trial1 = null;
		assertFalse(trial0.equals(trial1));
		assertFalse(trial0.equals(new Object()));
		trial0.setId(null);
		trial1 = new Trial();
		trial1.setId("bar");
		assertFalse(trial0.equals(trial1));
		trial0.setId("baz");
		trial1.setId(null);
		assertFalse(trial0.equals(trial1));
		trial0 = new Trial();
		trial1 = new Trial();
		trial0.setId("abc");
		trial1.setId("abc");
		assertTrue(trial0.equals(trial1));
	}

	@Test
	public void testGetPatient() {
		Trial trial = new Trial();
		Patient patient = PatientFactory.getPatient("clinical");
		patient.setId("foo");
		trial.addPatient(patient);
		assertEquals(patient, trial.getPatient(patient.getId()));
		Patient patient1 = PatientFactory.getPatient("clinical");
		patient1.setId("bar");
		assertNull(trial.getPatient(patient1.getId()));
	}

	@Test
	public void testHasPatientInList() {
		Trial trial = new Trial();
		Patient patient = PatientFactory.getPatient("clinical");
		assertFalse(trial.hasPatientInList(patient));
		trial.addPatient(patient);
		assertTrue(trial.hasPatientInList(patient));
	}

	@Test
	public void testToString() {
		Trial trial = new Trial();
		trial.setId("foo");
		String result = String.format("Trial %s has %d patient%s", trial.getId(), trial.getNumPatients(),
				trial.getNumPatients() != 1 ? "s" : "");
		assertEquals(result, trial.toString());
	}
}
