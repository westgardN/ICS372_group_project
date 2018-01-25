package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Reader {
	public static List<Reading> read(String filePath) throws IOException {
		JsonReadings answer = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))){
			StringBuilder json = new StringBuilder();
			
			while (br.ready()) {
				json.append(br.readLine());
			}
			
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			
			answer = gson.fromJson(json.toString(), JsonReadings.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return answer != null ? answer.getPatientReadings() : null;
	}
}
