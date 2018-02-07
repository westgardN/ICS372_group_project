/**
 * File: JsonProcessor.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

/**
 * The JsonProcessor is used for importing and exporting a List of Reading objects to and from a
 * JSON file. It includes methods to read from a JSON file and write to a JSON file.
 * 
 * @author That Group
 *
 */
public class JsonProcessor {
	/**
	 * Reads the specified JSON file and returns a List of Reading object references read from the
	 * file.
	 * 
	 * @param filePath The file to import readings from. This file must exist and it must be in JSON format
	 * 
	 * @return A list of the readings that were read from the file.
	 * 
	 * @throws IOException indicates an error occurred while operating on the file.
	 */
	public static List<Reading> read(String filePath) throws IOException {
		JsonReadings answer = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))){
			Gson gson = new GsonBuilder().create();
			
			answer = gson.fromJson(br, JsonReadings.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return answer != null ? answer.getPatientReadings() : null;
	}
	
	/**
	 * Writes a List of Reading objects to the specified file in JSON format
	 * 
	 * @param readings the list of readings to write to the file
	 * @param filePathOut the file to write to. If it already exists, it is overwritten.
	 *  
	 * @throws IOException indicates an error occurred while operating on the file.
	 */
	public static void write(List<Reading> readings, String filePathOut) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePathOut)))) {
			Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
			
			JsonReadings jReadings = new JsonReadings();
			
			jReadings.setPatientReadings(readings);
			
			bw.write(gson.toJson(jReadings));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
