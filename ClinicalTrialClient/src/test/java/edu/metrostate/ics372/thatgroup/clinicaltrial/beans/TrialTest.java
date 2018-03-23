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
 * @author That Group This will test the Trial class methods and constructors
 *
 */
public class TrialTest {
	private final String DEFAULT_ID = Trial.DEFAULT_ID;
	private final String TRIAL_ID = "test";
	private final String TRIAL_ID_B = "testB";
	private final LocalDate START_DATE = LocalDate.of(2018, 1, 1);
	private final LocalDate END_DATE = LocalDate.of(2018, 2, 28);

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#Trial()}.
	 */
	@Test
	public final void testDefaultConstructorShouldBeNotNullAndHaveDefaultIdAndNowDateAndOtherMembersShouldBeNull() {
		Trial trial = new Trial();

		assertNotNull(trial);
		assertEquals(DEFAULT_ID, trial.getId());
		assertEquals(LocalDate.now(), trial.getStartDate());
		assertNull(trial.getEndDate());
	}


	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#getId()} and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#setId(java.lang.String)}.
	 */
	@Test
	public final void testId() {
		Trial trial = new Trial();

		trial.setId(DEFAULT_ID);
		assertEquals(DEFAULT_ID, trial.getId());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#getStartDate()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#setStartDate(java.time.LocalDate)}.
	 */
	@Test
	public final void testStartDate() {
		Trial trial = new Trial();

		trial.setStartDate(START_DATE);
		assertEquals(START_DATE, trial.getStartDate());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#getEndDate()}
	 * and
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#setEndDate(java.time.LocalDate)}.
	 */
	@Test
	public final void testEndDate() {
		Trial trial = new Trial();

		trial.setEndDate(END_DATE);
		assertEquals(END_DATE, trial.getEndDate());
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#hasPatientStartedTrial(edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient)}.
	 */
	@Test
	public final void testHasThePatientStartedATrial() {
		Trial trial = new Trial();

		trial.setId(TRIAL_ID);

		Patient patient = new Patient();
		assertFalse(trial.hasPatientStartedTrial(patient));
		patient.setTrialId(TRIAL_ID);
		patient.setTrialStartDate(START_DATE);
		patient.setTrialEndDate(null);
		assertTrue(trial.hasPatientStartedTrial(patient));
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#isPatientInTrial(edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient)}.
	 */
	@Test
	public final void testIsPatientInAnActiveTrial() {
		Trial trial = new Trial();
		trial.setId(TRIAL_ID);

		Patient patient = new Patient();
		assertFalse(trial.isPatientInTrial(patient));

		patient.setTrialId(TRIAL_ID);
		patient.setTrialStartDate(START_DATE);
		patient.setTrialEndDate(null);
		assertTrue(trial.isPatientInTrial(patient));
		assertNull(trial.getEndDate());
	}
	

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#hashCode()} 
	 * and {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#equals(java.lang.Object)}.
	 */
	@Test
	public final void testTwoTrialsThatAreEqualButBotEqualHashCodes() {
		Trial trialA = new Trial(TRIAL_ID);
		Trial trialB = new Trial(TRIAL_ID);

		assertEquals(trialA, trialB);
		assertEquals(trialA.hashCode(), trialB.hashCode());
	}
	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#equals(java.lang.Object)} and 
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#hashCode()} 
	 */
	@Test
	public final void testTwoTrialsThatAreNotEqualShouldNotBeConsideredEqual() {
		Trial trialA = new Trial(TRIAL_ID);
		Trial trialB = new Trial(TRIAL_ID_B);
		Trial trialC = new Trial(DEFAULT_ID);
		
		assertNotEquals(trialA, trialB);
		assertNotEquals(trialA, trialC);
		assertNotEquals(trialB, trialC);
	}

	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#toString()}.
	 */
	@Test
	public final void testToStringStartingTrial() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		Trial trial = new Trial();
		String result = trial.toString();
		String expected = "Trial " + trial.getId()+ " (" + trial.getStartDate().format(formatter) + ")";
		assertEquals(expected, result);
	}
	
	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#toString()}.
	 */
	@Test
	public final void testToStringStartingTrialWithEndDateThatIsNotNull() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		Trial trial = new Trial();
		trial.setId(TRIAL_ID);
		trial.setStartDate(START_DATE);
		trial.setEndDate(END_DATE);
		
		String result = trial.toString();
		String expected = ("Trial " + trial.getId() + " (" + trial.getStartDate().format(formatter) +" - "+ trial.getEndDate().format(formatter) + ")");
		assertEquals(expected, result);
	}
	
	
	/**
	 * Test method for
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial#clone()}.
	 */
	@Test
	public final void testClone() {
		Trial trialA = new Trial();
		Trial trialB = trialA;

		assertEquals(trialA, trialB);
		assertTrue(trialA == trialB);

		trialB = trialA.clone();

		assertEquals(trialA, trialB);
		assertFalse(trialA == trialB);
	}

}
