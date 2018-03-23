/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.Test;

/**
 * @author That Group
 *
 */

public class StepsTest {

	private static final Object MIN_VALUE = Integer.MIN_VALUE;
	private static final String STRING_PATIENT_ID = "test";
	private static final String PATIENT_ID = null;
	private static final String  STRING_ID = "test";
	private static final String ID = null;
	private  LocalDateTime DATE;
	private Object VALUE;
	private static final String CLINIC_ID = null;
	private static final String STRING_CLINIC_ID = "test";
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#toString()}.
	 */
	@Test
	public final void testToString() {
		Steps steps = new Steps(STRING_PATIENT_ID, STRING_ID, DATE, VALUE, STRING_CLINIC_ID);
		String result = steps.toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		String expected = "Steps taken " + steps.getDate().format(formatter) + " is" + steps.getValue(); 

		assertEquals(expected, result);
	}
	

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#getValue()} and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#setValue(java.lang.Object)}..
	 */
	@Test
	public final void testStepValueInteger() {
		Steps steps = new Steps();
		
		steps.setValue(MIN_VALUE);
		assertEquals(MIN_VALUE, steps.getValue());
	}
	
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#Steps()}.
	 */
	@Test
	public final void testArgsConstrcutedSteps() {
		Steps steps = new Steps(PATIENT_ID, ID, DATE, VALUE, CLINIC_ID);
		
		assertNotNull(steps);
		assertNull(steps.getPatientId());
		assertNull(steps.getId());
		assertNull(steps.getDate());
		assertNotNull(steps.getValue());
		assertNull(steps.getClinicId());
		
		
	//String patientId, String id, LocalDateTime date, Object value, String clinicId)
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#Steps(java.lang.String, java.lang.String, java.time.LocalDateTime, java.lang.Object, java.lang.String)}.
	 */
	@Test
	public final void testConstrcutedNoArgsStepsStringStringLocalDateTimeObjectString() {
		Steps steps = new Steps(null, null, null, Integer.MIN_VALUE, null);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		
		steps.setDate(DATE);
		
		assertNotNull(steps);
		assertEquals(PATIENT_ID, steps.getPatientId());
		assertEquals(ID, steps.getId());
		assertNotNull(steps.getDate().format(formatter));
		assertEquals(VALUE, steps.getValue());
		assertEquals(CLINIC_ID, steps.getClinicId());
	}

}
