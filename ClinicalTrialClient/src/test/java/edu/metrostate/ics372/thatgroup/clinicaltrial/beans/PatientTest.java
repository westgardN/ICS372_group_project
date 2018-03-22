/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.Test;

/**
 * @author That Group
 *
 */
public class PatientTest {
	private final String ID = "test";
	private final String TRIAL_ID = "test";
	private final LocalDate START_DATE = LocalDate.of(2018, 1, 1);
	private final LocalDate END_DATE = LocalDate.of(2018, 2, 28);

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#hashCode()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#equals(java.lang.Object)}.
	 */
	@Test
	public final void twoPatientsThatAreEqualShouldHaveEqualHashCodes() {
		Patient patientA = new Patient(ID, TRIAL_ID, START_DATE, END_DATE);
		Patient patientB = new Patient(ID, TRIAL_ID, START_DATE, END_DATE);

		assertEquals(patientA, patientB);
		assertEquals(patientA.hashCode(), patientB.hashCode());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#Patient()}.
	 */
	@Test
	public final void defaultConstructedShouldBeNotNullAndMemberShouldBeNull() {
		Patient patient = new Patient();

		assertNotNull(patient);
		assertNull(patient.getId());
		assertNull(patient.getTrialId());
		assertNull(patient.getTrialStartDate());
		assertNull(patient.getTrialEndDate());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#Patient(java.lang.String, java.lang.String, java.time.LocalDate, java.time.LocalDate)}.
	 */
	@Test
	public final void argConstructedShouldBeNotNullAndMembersShouldBeEquals() {

		Patient patient = new Patient(ID, TRIAL_ID, START_DATE, END_DATE);

		assertNotNull(patient);
		assertEquals(ID, patient.getId());
		assertEquals(TRIAL_ID, patient.getTrialId());
		assertEquals(START_DATE, patient.getTrialStartDate());
		assertEquals(END_DATE, patient.getTrialEndDate());

	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#getId()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#setId(java.lang.String)}.
	 */
	@Test
	public final void testPatienId() {
		Patient patient = new Patient();

		patient.setId(ID);
		assertEquals(ID, patient.getId());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#getTrialStartDate()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#setTrialStartDate()}.
	 */
	@Test
	public final void testTrialStartDate() {
		Patient patient = new Patient();

		patient.setTrialStartDate(START_DATE);

		assertEquals(START_DATE, patient.getTrialStartDate());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#getTrialEndDate()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#setTrialEndDate(java.time.LocalDate)}.
	 */
	@Test
	public final void testTrialEndDate() {
		Patient patient = new Patient();

		patient.setTrialEndDate(END_DATE);

		assertEquals(END_DATE, patient.getTrialEndDate());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#toString()}.
	 */
	@Test
	public final void testToStringEmptyPatient() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		Patient patient = new Patient();
		String result = patient.toString();
		String expected = "null has not started the trial";
		assertEquals(expected, result);
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#toString()}.
	 */
	@Test
	public final void testToStringNonEmptyPatientNotStartedTrial() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		Patient patient = new Patient(ID, TRIAL_ID, null, null);
		String result = patient.toString();
		String expected = ID + " has not started the trial";
		assertEquals(expected, result);
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#toString()}.
	 */
	@Test
	public final void testToStringNonEmptyActivePatient() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		Patient patient = new Patient(ID, TRIAL_ID, START_DATE, null);
		String result = patient.toString();
		String expected = ID + ": active " + patient.getTrialStartDate().format(formatter);
		assertEquals(expected, result);
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#toString()}.
	 */
	@Test
	public final void testToStringNonEmptyInactivePatient() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		Patient patient = new Patient(ID, TRIAL_ID, START_DATE, END_DATE);
		String result = patient.toString();
		String expected = ID + ": inactive (" + patient.getTrialStartDate().format(formatter) + " - "
				+ patient.getTrialEndDate().format(formatter) + ")";
		assertEquals(expected, result);
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#getTrialId()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient#setTrialId(java.lang.String)}.
	 */
	@Test
	public final void testTrialId() {
		Patient patient = new Patient();

		patient.setTrialId(TRIAL_ID);
		assertEquals(TRIAL_ID, patient.getTrialId());
	}

}
