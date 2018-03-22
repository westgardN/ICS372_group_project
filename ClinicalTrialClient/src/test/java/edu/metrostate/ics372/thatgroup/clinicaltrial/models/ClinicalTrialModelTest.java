/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.models;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalogException;

/**
 * @author vpalo
 *
 */
public class ClinicalTrialModelTest {

	@Test
	public void newTrialModelShouldNotBeNull() {
		ClinicalTrialModel model = null;
		
		try {
			model = new ClinicalTrialModel();
		} catch (TrialCatalogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(model);
	}

}
