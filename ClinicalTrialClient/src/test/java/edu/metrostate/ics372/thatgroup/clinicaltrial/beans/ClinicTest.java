package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;
import static org.junit.Assert.*;

import java.beans.PropertyChangeSupport;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;

/**
 * 
 */

/**
 * @author That Group
 *
 */
public class ClinicTest {
	public static final String DEFAULT_ID = "default";
	public static final String PROP_ID = "id";
	public static final String PROP_TRIAL_ID = "trialId";
	public static final String PROP_NAME = "name";
	protected transient PropertyChangeSupport pcs;
	protected String id;
	protected String trialId;
	protected String name;
	
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic#Clinic()}.
	 */
	@Test
	public final void testDefaultClinicConstructorAndMembersShouldBeNull() {
		Clinic clinic =new Clinic();
		
		clinic.setId(DEFAULT_ID);
		
		assertNotNull(clinic);
		assertNotNull(clinic.getId());
		assertNull(clinic.getName());
		assertNull(clinic.getTrialId());
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic#Clinic(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testClinicArgsConstructedShouldBeNotNull() {
		Clinic clinic = new Clinic(id, trialId, name);
		
		clinic.setId(id);
		clinic.setTrialId(trialId);
		clinic.setName(name);
		
		assertNotNull(clinic);
		assertEquals(id, clinic.getId());
		assertEquals(trialId, clinic.getTrialId());
	}


	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic#setId(java.lang.String)} and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic#getId()}.
	 */
	@Test
	public final void testTrialId() {
		Clinic clinic = new Clinic();
		
		clinic.setId(trialId);
		assertEquals(trialId, clinic.getTrialId());
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic#getName()} and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic#setName(java.lang.String)}..
	 */
	@Test
	public final void testGetName() {
		Clinic clinic = new Clinic();
		
		clinic.setName(name);
		assertEquals(name, clinic.getName());
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic#toString()}.
	 */
	@Test
	public final void testToStringEmptyClinic() {
		
		Clinic clinic = new Clinic();
		String result = clinic.toString();
		String expected = "Clinic " + clinic.getId()  + " (Name: " + clinic.getName() 
		+ ") (Trial " + clinic.getTrialId() + ")";
		assertEquals(expected, result);
	}

}
