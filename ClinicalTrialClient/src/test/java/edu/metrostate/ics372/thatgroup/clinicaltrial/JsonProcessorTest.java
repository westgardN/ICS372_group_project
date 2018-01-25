package edu.metrostate.ics372.thatgroup.clinicaltrial;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.JsonProcessor;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

public class JsonProcessorTest {

	@Test
	public void testReadAndWrite() {
		try {
			System.out.println("Imported");
			
			List<Reading> readings = JsonProcessor.read("./data/test_data.json");
			
			for (Reading reading : readings) {
				System.out.println(reading);
			}
			
			JsonProcessor.write(readings, "./data/testReadAndWrite_out.json");
			
			System.out.println("Exported");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
