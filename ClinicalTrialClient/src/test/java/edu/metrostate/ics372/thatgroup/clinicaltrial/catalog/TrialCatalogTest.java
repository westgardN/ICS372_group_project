package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;

public class TrialCatalogTest {
	Trial trial = new Trial();
	Patient patient = new Patient();
	Clinic clinic = new Clinic();
	List<Patient> patients;
	ClinicalTrialCatalog catalog = new ClinicalTrialCatalog();
	
	
	

	@Test
	public void test() throws TrialCatalogException {
		ClinicalTrialCatalog catalog = new ClinicalTrialCatalog();
		Trial trial = new Trial("test");
		Clinic clinic = new Clinic("Clinic", "test","testing" );
		assertNotNull(clinic);
		if (catalog.isInit()) {
		}
		
	}
	
	@Test
	public void testPatient() throws TrialCatalogException{
		ClinicalTrialCatalog catalog = new ClinicalTrialCatalog();
		Trial trial = new Trial("test");
		Patient patient = new Patient();
		assertNotNull(patient);
		if(catalog.getPatients().contains(patient));
	}
	
	@Test
	
	public void testRemove() throws TrialCatalogException{
		assertFalse(catalog.exists(clinic));
		if(catalog.remove(clinic));{
	}
		assertFalse(catalog.exists(this.patient));
		if(catalog.remove(this.patient));
	}
	}

