package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Writer {
	FileWriter writer;

	public Writer() {

	}

	public void write(List<Reading> readings, String filePathOut) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
		writer = new FileWriter(filePathOut);

		for (Reading reading : readings) {
			JsonObject jsonObject = new JsonParser().parse(createJsonObject(reading)).getAsJsonObject();
			String json = gson.toJson(jsonObject);
			try {
				writer.write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writer.close();
	}

	private String createJsonObject(Reading reading) {
		String jsonObject = "";
		long convertedDate = reading.getReadingDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		jsonObject = String.format(
				"{\"%s\":\"%s\"," // Patient ID
				+ "\"%s\":\"%s\"," // Reading Type
				+ "\"%s\":\"%s\"," // Reading ID
				+ "\"%s\":\"%s\"," // Reading Value
				+ "\"%s\":\"%s\"}", // Reading Date
				Identifiers.PATIENT_ID.get(), reading.getPatientID(), 
				Identifiers.READING_TYPE.get(), reading.getReadingType().get(), 
				Identifiers.READING_ID.get(), reading.getReadingID(),
				Identifiers.READING_VALUE.get(), reading.getReadingValue(), 
				Identifiers.READING_DATE.get(), String.valueOf(convertedDate));
		return jsonObject;
	}
}
