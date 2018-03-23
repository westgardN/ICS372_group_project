package edu.metrostate.ics372.thatgroup.clinicaltrial;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.JsonProcessor;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

public class JsonProcessorTest {

	@Test
	public void testReadAndWrite() {
		try {
			System.out.println("Imported");
			
			List<Reading> readings = JsonProcessor.read("./data/test_data.json");
			assertNotNull(readings);
			assertEquals(4, readings.size());
			
			for (Reading reading : readings) {
				System.out.println(reading);
			}
			
			JsonProcessor.write(readings, "./data/testReadAndWrite_out.json");
			
			System.out.println("Exported");
			
			readings = JsonProcessor.read("./data/testReadAndWrite_out.json");
			assertNotNull(readings);
			assertEquals(4, readings.size());
			
		} catch (IOException | TrialException e) {
			e.printStackTrace();
		}
	}
}
