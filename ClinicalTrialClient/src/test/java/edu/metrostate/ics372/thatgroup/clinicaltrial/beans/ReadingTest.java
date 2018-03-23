/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

/**
 * @author That Group
 *
 */
public class ReadingTest {
	private static final String PATIENT_ID = "test";
	private static final String ID = "test";
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String VALUE = "test";
	private static final String CLINIC_ID = "test";
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#Reading()}.
	 */
	@Test
	public final void testDefaultNoArgsConstructedReading() {
		Reading reading = new Reading();
		
		assertNotNull(reading);
		assertNull(reading.getPatientId());
		assertNull(reading.getId());
		assertNull(reading.getDate());
		assertNull(reading.getValue());
		assertNull(reading.getClinicId());
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#Reading(java.lang.String, java.lang.String, java.time.LocalDateTime, java.lang.Object, java.lang.String)}.
	 */
	@Test
	public final void testConstructedReadingShouldNotBeNullStringStringLocalDateTimeObjectStringReading() {
		Reading reading = new Reading(PATIENT_ID, ID, DATE, VALUE, CLINIC_ID);
		
		assertNotNull(reading);
		assertEquals(PATIENT_ID, reading.getPatientId());
		assertEquals(ID, reading.getId());
		assertEquals(DATE, reading.getDate());
		assertEquals(VALUE, reading.getValue());
		assertEquals(CLINIC_ID, reading.getClinicId());
	}


	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#toString()}.
	 */
	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getPatientId()}.
	 */
	@Test
	public final void testGetPatientId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setPatientId(java.lang.String)}.
	 */
	@Test
	public final void testSetPatientId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getId()}.
	 */
	@Test
	public final void testGetId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setId(java.lang.String)}.
	 */
	@Test
	public final void testSetId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getDate()}.
	 */
	@Test
	public final void testGetDate() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setDate(java.time.LocalDateTime)}.
	 */
	@Test
	public final void testSetDate() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getValue()}.
	 */
	@Test
	public final void testGetValue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setValue(java.lang.Object)}.
	 */
	@Test
	public final void testSetValue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getClinicId()}.
	 */
	@Test
	public final void testGetClinicId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setClinicId(java.lang.String)}.
	 */
	@Test
	public final void testSetClinicId() {
		fail("Not yet implemented"); // TODO
	}

}
