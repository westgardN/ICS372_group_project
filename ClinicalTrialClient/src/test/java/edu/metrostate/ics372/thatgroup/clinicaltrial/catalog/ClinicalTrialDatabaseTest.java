package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalTrialCatalog;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalogException;


public class ClinicalTrialDatabaseTest {

	@Test
	public void test() throws TrialCatalogException {
		ClinicalTrialCatalog catalog = new ClinicalTrialCatalog();
		Trial trial = new Trial("test");
		assertNotNull(trial);
		if (catalog.init(trial)) {
		}
		
		for (String catalogName : catalog.getAllTrialCatalogNamesInDirectory()) {
			System.out.println(catalogName);
		}
	}
}
