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
	private static final String PATIENT_ID = "test";
	private static final String ID = "test";
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final Integer INTEGER_VALUE = 99;
	private static final String STRING_VALUE = "99";
	private static final String CLINIC_ID = "test";
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#toString()}.
	 */
	@Test
	public final void testToStringWithValidDate() {
		Steps steps = new Steps(PATIENT_ID, ID, DATE, INTEGER_VALUE, CLINIC_ID);
		String result = steps.toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		String expected = "Steps taken on " + steps.getDate().format(formatter) + " is: " + steps.getValue(); 

		assertEquals(expected, result);
	}
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#toString()}.
	 */
	@Test
	public final void testToStringWithNullDate() {
		Steps steps = new Steps(PATIENT_ID, ID, null, INTEGER_VALUE, CLINIC_ID);
		String result = steps.toString();
		String expected = "Steps taken is: " + steps.getValue(); 

		assertEquals(expected, result);
	}
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#getValue()} and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#setValue(java.lang.Object)}..
	 */
	@Test
	public final void testStepValueInteger() {
		Steps steps = new Steps();
		
		steps.setValue(INTEGER_VALUE);
		assertEquals(INTEGER_VALUE, steps.getValue());
	}
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#getValue()} and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#setValue(java.lang.Object)}..
	 */
	@Test
	public final void testStepValueString() {
		Steps steps = new Steps();
		
		steps.setValue(STRING_VALUE);
		assertEquals(INTEGER_VALUE, steps.getValue());
	}
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#Steps()}.
	 */
	@Test
	public final void testArgsConstructorMemberValuesShouldBeNotNull() {
		Steps steps = new Steps(PATIENT_ID, ID, DATE, INTEGER_VALUE, CLINIC_ID);
		
		assertNotNull(steps);
		assertNotNull(steps.getPatientId());
		assertNotNull(steps.getId());
		assertNotNull(steps.getDate());
		assertNotNull(steps.getValue());
		assertNotNull(steps.getClinicId());
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Steps#Steps(java.lang.String, java.lang.String, java.time.LocalDateTime, java.lang.Object, java.lang.String)}.
	 */
	@Test
	public final void testNoArgConStructorEveryThingButValueShouldBeNull() {
		Steps steps = new Steps();
		
		assertNotNull(steps);
		assertNull(steps.getPatientId());
		assertNull(steps.getId());
		assertNull(steps.getDate());
		assertEquals(Integer.MIN_VALUE, steps.getValue());
		assertNull(steps.getClinicId());
	}

}
