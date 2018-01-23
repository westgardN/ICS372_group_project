package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class Reader {

	File file;
	FileReader in;
	JsonReader reader;
	List<Reading> readings;

	public Reader() {

	}
	
	public List<Reading> getReadings() {
		return readings;
	}

	public void read(String filePath) throws IOException {
		file = new File(filePath);
		try {
			in = new FileReader(file);
			reader = new JsonReader(in);
			while (reader.hasNext()) {
				JsonToken nextToken = reader.peek();
				if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
					reader.beginObject();
				} else if (JsonToken.NAME.equals(nextToken) && reader.nextName().equals(Identifiers.ARRAY_NAME.get())) {
					reader.beginArray();
					readArray(reader);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		reader.close();
	}

	private void readArray(JsonReader reader) throws IOException {
		readings = new ArrayList<>();
		String patientID = "";
		String readingType = "";
		String readingID = "";
		String readingValue = "";
		long readingDate = 0;
		while (reader.hasNext()) {
			reader.beginObject();
			if (reader.nextName().equals(Identifiers.PATIENT_ID.get())) {
				patientID = reader.nextString();
			}
			if (reader.nextName().equals(Identifiers.READING_TYPE.get())) {
				readingType = reader.nextString();
			}
			if (reader.nextName().equals(Identifiers.READING_ID.get())) {
				readingID = reader.nextString();
			}
			if (reader.nextName().equals(Identifiers.READING_VALUE.get())) {
				readingValue = reader.nextString();
			}
			if (reader.nextName().equals(Identifiers.READING_DATE.get())) {
				readingDate = reader.nextLong();
			}
			reader.endObject();
			Reading reading = createReading(patientID, readingType, readingID, readingValue, readingDate);
			readings.add(reading);
		}
	}

	private Reading createReading(String patientID, String readingType, String readingID, String readingValue,
			long readingDate) {

		ReadingType readType = null;
		LocalDateTime date = Instant.ofEpochMilli(readingDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
		for (ReadingType type : ReadingType.values()) {
			if (type.get().equals(readingType)) {
				readType = type;
			}
		}
		return new Reading(patientID, readType, readingID, readingValue, date);
	}
}
