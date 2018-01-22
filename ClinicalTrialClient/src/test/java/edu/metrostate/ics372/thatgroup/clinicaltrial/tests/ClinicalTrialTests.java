package edu.metrostate.ics372.thatgroup.clinicaltrial.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.impl.ReadingType;

public class ClinicalTrialTests {

	@Test
	public void testReadingTypeEnum() {
		assertEquals("weight", ReadingType.WEIGHT.Get());
		assertEquals("steps", ReadingType.STEPS.Get());
		assertEquals("temp", ReadingType.TEMP.Get());
		assertEquals("blood_press", ReadingType.BLOOD_PRESS.Get());
	}
}
