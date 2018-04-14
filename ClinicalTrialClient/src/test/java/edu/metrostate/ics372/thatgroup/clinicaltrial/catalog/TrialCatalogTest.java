package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;

public class TrialCatalogTest {
	private static final String TEST_TRIAL_ID = "test";
	Trial trial;
	TrialCatalog catalog;
	
	
	@Before
	public void setupTestHarness() {
		trial = new Trial(TEST_TRIAL_ID);
		
		try {
			TrialManager tm = TrialManager.getInstance();
			if (tm.exists(trial)) {
				boolean expected = true;
				boolean actual = tm.remove(trial);
				
				assertEquals(expected, actual);
			}
			
			catalog = tm.getTrialCatalog(trial);
		} catch (TrialCatalogException e) {
			teardownTestHarness();
			e.printStackTrace();
		}
		
		assertNotNull(trial);
		assertNotNull(catalog);
	}

	@After
	public void teardownTestHarness() {
		TrialManager.getInstance().uninitialize();
		catalog = null;
		trial = null;
	}
	
	@Test
	public void testInsertClinic() throws TrialCatalogException {
		Clinic clinic = new Clinic("test", TEST_TRIAL_ID,"testing" );
		
		boolean expected = true;
		boolean actual = catalog.insert(clinic);
		
		assertEquals(expected, actual);
		
	}
	
	@Test(expected = TrialCatalogException.class)
	public void testInsertInvalidPatientShouldThrowException() throws TrialCatalogException {
		Patient patient = new Patient();
		
		if(!catalog.exists(patient)) {
			catalog.insert(patient);
		}
	}
	
	@Test	
	public void testRemove() throws TrialCatalogException {
		Clinic clinic = new Clinic("test", TEST_TRIAL_ID,"testing");
		
		testInsertClinic();
		boolean expected = true;
		boolean actual = catalog.exists(clinic);
		assertEquals(expected, actual);
		
		actual = catalog.remove(clinic);
		assertEquals(expected, actual);
	}
}

