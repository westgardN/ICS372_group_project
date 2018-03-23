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
public class ReadingTest {
	private static final String PATIENT_ID = "test";
	private static final String ID = "test";
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String VALUE = "test";
	private static final String CLINIC_ID = "test";
	private static final String PATIENT_ID2 = "test2";

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#hashCode()}.
	 */
	@Test
	public final void testTwoReadingdThatAreEqualNotHaveEqualHashCodes() {
		Reading readingA = new Reading(PATIENT_ID, ID, DATE, VALUE, CLINIC_ID);
		Reading readingB = new Reading(PATIENT_ID, ID, DATE, VALUE, CLINIC_ID);

		assertEquals(readingA, readingB);
		assertEquals(readingA.hashCode(), readingB.hashCode());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#equals(java.lang.Object)}.
	 */
	@Test
	public final void testTwoReadingsThatAreNotEqualShouldNOtBeConsideredEqual() {
		Reading readingA = new Reading(PATIENT_ID, ID, DATE, VALUE, CLINIC_ID);
		Reading readingB = new Reading(PATIENT_ID2, ID, DATE, VALUE, CLINIC_ID);

		assertNotEquals(readingA, readingB);
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#Reading()}.
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
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#Reading(java.lang.String, java.lang.String, java.time.LocalDateTime, java.lang.Object, java.lang.String)}.
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
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#toString()}.
	 */
	@Test
	public final void testReadingToString() {
		Reading reading = new Reading(PATIENT_ID, ID, DATE, VALUE, CLINIC_ID);
		String result = reading.toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		String expected = "Reading " + reading.getId() + " for patient " + reading.getPatientId() + " taken on "
				+ reading.getDate().format(formatter) + " taken at " + reading.getClinicId() + " has a value of "
				+ reading.getValue();

		assertEquals(expected, result);
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getPatientId()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setPatientId(java.lang.String)}..
	 */
	@Test
	public final void testReadingPatientId() {
		Reading reading = new Reading();
		reading.setPatientId(PATIENT_ID);

		assertEquals(PATIENT_ID, reading.getPatientId());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getId()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setId(java.lang.String)}..
	 */
	@Test
	public final void testReadingId() {
		Reading reading = new Reading();
		reading.setId(ID);

		assertEquals(ID, reading.getId());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getDate()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setDate(java.time.LocalDateTime)}..
	 */
	@Test
	public final void testReadingDate() {
		Reading reading = new Reading();
		reading.setDate(DATE);

		assertEquals(DATE, reading.getDate());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getValue()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setValue(java.lang.Object)}..
	 */
	@Test
	public final void testReadingValue() {
		Reading reading = new Reading();
		reading.setValue(VALUE);

		assertEquals(VALUE, reading.getValue());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#getClinicId()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading#setClinicId(java.lang.String)}..
	 */
	@Test
	public final void testReadingClinicId() {
		Reading reading = new Reading();
		reading.setClinicId(CLINIC_ID);

		assertEquals(CLINIC_ID, reading.getClinicId());
	}

}
