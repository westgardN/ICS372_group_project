package edu.metrostate.ics372.thatgroup.clinicaltrial.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.impl.ReadingType;

public class ClinicalTrialTests {

	@Test
	public void testReadingTypeEnum() {
		assertEquals("weight", ReadingType.WEIGHT.get());
		assertEquals("steps", ReadingType.STEPS.get());
		assertEquals("temp", ReadingType.TEMP.get());
		assertEquals("blood_press", ReadingType.BLOOD_PRESS.get());
	}
}
