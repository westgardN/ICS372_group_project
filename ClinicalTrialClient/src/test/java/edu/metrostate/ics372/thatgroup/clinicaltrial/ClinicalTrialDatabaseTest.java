package edu.metrostate.ics372.thatgroup.clinicaltrial;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalTrialCatalog;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalTrialCatalogUtilIty;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

public class ClinicalTrialDatabaseTest {

	@Test
	public void test() {
		ClinicalTrialCatalog catalog = new ClinicalTrialCatalog();
		Trial trial = new Trial("test");
		if (catalog.init()) {
			catalog.createTrialCatalog(trial);
		}
		
		for (String catalogName : catalog.getAllTrialCatalogNamesInDirectory()) {
			System.out.println(catalogName);
		}
	}
}
