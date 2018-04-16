package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalTrialCatalog;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;


public class ClinicalTrialDatabaseTest {

	@Test
	public void test() throws TrialCatalogException {
		Trial trial = new Trial("test");
		assertNotNull(trial);
		ClinicalTrialCatalog catalog = new ClinicalTrialCatalog(trial);
		assertEquals(true, catalog.init());		
	}
}
