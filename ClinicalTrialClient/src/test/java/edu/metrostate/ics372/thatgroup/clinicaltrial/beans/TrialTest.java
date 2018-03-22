package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class TrialTest {
	private final String PATIENT_ID = "foo";
	private final String OTHER_PATIENT_ID = "baz";
	private final String TRIAL_ID = "bar";
	private final String OTHER_TRIAL_ID = "abc";
	private final String CLINICAL  = "clinical";

	@Test
	public void testConstruction() {
		Trial trial = new Trial();
		assertNotNull(trial);
		assertEquals(Trial.DEFAULT_ID, trial.getId());
		Trial trial1 = new Trial(TRIAL_ID);
		trial1.setId(OTHER_TRIAL_ID);
		assertNotNull(trial1);
		assertEquals(OTHER_TRIAL_ID, trial1.getId());
	}

	@Test
	public void testAddPropertyChangeListener() throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
		Trial trial = new Trial();
		PropertyChangeListener pcl = mock(PropertyChangeListener.class);
		
		trial.addPropertyChangeListener(mock(PropertyChangeListener.class));
	}

	@Test
	public void testHasPatientStartedTrial() {
		Trial trial = new Trial();
		trial.setId(TRIAL_ID);
		Patient patient = new Patient();
		assertFalse(trial.hasPatientStartedTrial(patient));
		patient.setTrialId(TRIAL_ID);
		patient.setTrialStartDate(LocalDate.now());
		patient.setTrialEndDate(null);
		assertTrue(trial.hasPatientStartedTrial(patient));
	}

	@Test
	public void testIsPatientInTrial() {
		Trial trial = new Trial();
		trial.setId(TRIAL_ID);
		Patient patient = new Patient();
		assertFalse(trial.isPatientInTrial(patient));
		patient.setTrialId(TRIAL_ID);
		patient.setTrialStartDate(LocalDate.now());
		patient.setTrialEndDate(null);
		assertTrue(trial.isPatientInTrial(patient));
	}

	@Test
	public void testHashCode() {
		Trial trial0 = new Trial();
		assertEquals(31, trial0.hashCode());
		trial0.setId(TRIAL_ID);
		assertEquals(31 + trial0.getId().toUpperCase().hashCode(), trial0.hashCode());
		trial0.setId(null);
		assertEquals(31, trial0.hashCode());
	}

	@Test
	public void testEquals() {
		Trial trial0 = new Trial();
		Trial trial1 = trial0;
		assertTrue(trial0.equals(trial1));
		
		trial0.setId(TRIAL_ID);
		assertTrue(trial0.equals(trial1));
		
		trial1 = new Trial();
		assertFalse(trial0.equals(trial1));
		
		trial1 = null;
		assertFalse(trial0.equals(trial1));
		assertFalse(trial0.equals(new Object()));
		
		trial0.setId(null);
		trial1 = new Trial();
		trial1.setId(OTHER_TRIAL_ID);
		assertFalse(trial0.equals(trial1));
		
		trial0.setId(OTHER_TRIAL_ID);
		trial1.setId(null);
		assertFalse(trial0.equals(trial1));
		
		trial0 = new Trial();
		trial1 = new Trial();
		trial0.setId(TRIAL_ID);
		trial1.setId(TRIAL_ID);
		assertTrue(trial0.equals(trial1));
	}

}
