package edu.metrostate.ics372.thatgroup.clinicaltrial.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.Reader;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ReadingType;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Writer;

public class ClinicalTrialTests {

	@Test
	public void testReadingTypeEnum() {
		assertEquals("weight", ReadingType.WEIGHT.get());
		assertEquals("steps", ReadingType.STEPS.get());
		assertEquals("temp", ReadingType.TEMP.get());
		assertEquals("blood_press", ReadingType.BLOOD_PRESS.get());
	}
	
	@Test
	public void testReader() {
		Reader rr = new Reader();
		try {
			rr.read("./data/test_data.json");
			List<Reading> readings = rr.getReadings();
			for (Reading reading : readings) {
				System.out.println(reading);
			}
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	
	@Test
	public void testWriter() {
		List<Reading> readings = null;
		Reader rr = new Reader();
		try {
			rr.read("./data/test_data.json");
			readings = rr.getReadings();
		} catch (IOException e) {
			e.printStackTrace();
		};
		
		Writer writer = new Writer();
		try {
			writer.write(readings, "./data/test_data_out.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
