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

public class JsonProcessor {
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
	 * Creates a GsonBuilder instance that can be used to build Gson with various configuration settings.
	 *  GsonBuilder follows the builder pattern, and it is typically used by first invoking various configuration
	 *   methods to set desired options, and finally calling create(). Tis Gson builder is liberal with what the parser will accept.
	 * @param readings
	 * @param filePathOut
	 * @throws IOException
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
