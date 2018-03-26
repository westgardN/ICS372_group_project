/**
 * File: TrialDataJsonImportExporter.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The TrialDataJsonImportExporter is used for importing and exporting a List of Reading objects to and from a
 * JSON file. It includes methods to read from a JSON file and write to a JSON file.
 * 
 * @author That Group
 *
 */
public class TrialDataJsonImportExporter implements TrialDataImporter, TrialDataExporter {
	private Trial trial;
	private List<Reading> readings;
	private List<Patient> patients;
	private List<Clinic> clinics;
	
	///////////////////////////// Begin TrialDataImporter Interface /////////////////
	
	@Override
	public List<Reading> getReadings() {
		return readings;
	}

	@Override
	public List<Clinic> getClinics() {
		return clinics;
	}

	@Override
	public List<Patient> getPatients() {
		return patients;
	}

	/**
	 * Reads the specified JSON file and returns a List of Reading object references read from the
	 * file.
	 * 
	 * @param filePath The file to import readings from. This file must exist and it must be in JSON format
	 * 
	 * @return A list of the readings that were read from the file.
	 * 
	 * @throws IOException indicates an error occurred while operating on the file.
	 * @throws TrialException 
	 */
	@Override
	public boolean read(Trial trial, InputStream is) throws TrialException {
		this.trial = trial; 
		prepareRead();
		boolean answer = false;
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
			JsonReader jsonReader = new JsonReader(br);
			
			jsonReader.beginObject();
			
			while (jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				
				if (name.equalsIgnoreCase("patient_readings")) {
					readReadings(jsonReader);
				}
			}
			
			jsonReader.endObject();
			
			answer = !readings.isEmpty();
		} catch (JsonSyntaxException | IOException e) {
			throw new TrialException("Unable to read from the input stream:\n" + e.getMessage(), e);
		}
		
		return answer;
	}

	///////////////////////////// End TrialDataImporter Interface ///////////////////
	///////////////////////////// Begin TrialDataExporter Interface /////////////////
	
	@Override
	public void setReadings(List<Reading> readings) {
		this.readings = readings;
	}

	@Override
	public void setClinics(List<Clinic> clinics) {
		this.clinics = clinics;
	}

	@Override
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	
	/**
	 * Writes a List of Reading objects to the specified file in JSON format
	 * 
	 * @param readings the list of readings to write to the file
	 * @param filePathOut the file to write to. If it already exists, it is overwritten.
	 *  
	 * @throws IOException indicates an error occurred while operating on the file.
	 */
	@Override
	public boolean write(OutputStream os) throws TrialException {
		prepareWrite();
		boolean answer = false;
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
			JsonWriter jsonWriter = new JsonWriter(bw);
			jsonWriter.setIndent("    ");
			jsonWriter.beginObject();
			jsonWriter.name("patient_readings");
			jsonWriter.beginArray();
			
			for (Reading reading : readings) {
				jsonWriter.beginObject();
				String type = ReadingFactory.getReadingType(reading);
				jsonWriter.name("patient_id").value(reading.getPatientId());
				jsonWriter.name("clinic_id").value(reading.getPatientId());
				jsonWriter.name("reading_type").value(type);
				jsonWriter.name("reading_id").value(reading.getId());
				writeValue(jsonWriter, type, reading);
				writeDate(jsonWriter, reading);
				jsonWriter.endObject();
			}
			
			jsonWriter.endArray();
			jsonWriter.endObject();
			
			answer = true;

		} catch (IOException e) {
			throw new TrialException("Unable to write to the output stream", e);
		}
		
		return answer;
	}

	private void writeDate(JsonWriter jsonWriter, Reading reading) throws IOException {
		long date = 0;
		
		if (reading.getDate() != null) {
			date = reading.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		}
		
		jsonWriter.name("reading_date").value(date);
	}

	private void writeValue(JsonWriter jsonWriter, String type, Reading reading) throws IOException {
		switch(type) {
			case ReadingFactory.TEMPERATURE:
			{
				boolean hasValue = reading.getValue() instanceof Double;
				Double value = hasValue ? (Double) reading.getValue() : 0.0;
				jsonWriter.name("reading_value").value(value);
				break;
			}
			case ReadingFactory.WEIGHT:
			case ReadingFactory.STEPS:
			{
				boolean hasValue = reading.getValue() instanceof Integer;
				Integer value = hasValue ? (Integer) reading.getValue() : 0;
				jsonWriter.name("reading_value").value(value);
				break;
			}
			default:
				boolean hasValue = reading.getValue() != null;
				String value = hasValue ? reading.getValue().toString() : Strings.EMPTY;
				jsonWriter.name("reading_value").value(value);
				break;
		}
		
	}

	///////////////////////////// End TrialDataExporter Interface ///////////////////
	private void prepareRead() throws TrialException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty()) {
			throw new TrialException("trial must be non null have a valid id.");
		}
		
		readings = new LinkedList<Reading>();
		clinics = new LinkedList<Clinic>();
		patients = new LinkedList<Patient>();
	}
	
	private void prepareWrite() throws TrialException {
		if (readings == null || readings.isEmpty()) {
			throw new TrialException("readings cannot be null or empty");
		}
	}
	
	private void readReadings(JsonReader jsonReader) throws TrialException {
		try {
			jsonReader.beginArray();
			
			while (jsonReader.hasNext()) {
				String patient_id = null;
				String clinic_id = null;
				String reading_type = null;
				String reading_id = null;
				String reading_value = null;
				long reading_date = 0;
				
				jsonReader.beginObject();
				
				while (jsonReader.hasNext()) {
					String name = jsonReader.nextName();
					
					if (name.equalsIgnoreCase("patient_id")) {
						patient_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase("clinic_id")) {
						clinic_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase("reading_type")) {
						reading_type = jsonReader.nextString();
					} else if (name.equalsIgnoreCase("reading_id")) {
						reading_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase("reading_value")) {
						reading_value = jsonReader.nextString();
					} else if (name.equalsIgnoreCase("reading_date")) {
						reading_date = jsonReader.nextLong();
					}
				}
				
				if (reading_type != null && !reading_type.trim().isEmpty()) {
					Reading reading = ReadingFactory.getReading(reading_type);
					reading.setId(reading_id != null ? reading_id.trim() : null);
					reading.setClinicId(clinic_id != null ? clinic_id.trim() : null);
					reading.setPatientId(patient_id != null ? patient_id.trim() : null);
					reading.setValue(reading_value);
					reading.setDate(Instant.ofEpochMilli(reading_date).atZone(ZoneId.systemDefault()).toLocalDateTime());
					
					if (clinic_id != null && !clinic_id.trim().isEmpty()) {
						Clinic clinic = new Clinic();
						clinic.setId(clinic_id.trim());
						clinic.setTrialId(trial.getId());
						if (!clinics.contains(clinic)) {
							clinics.add(clinic);
						}
					}
					
					if (patient_id != null && !patient_id.trim().isEmpty()) {
						Patient patient = new Patient();
						patient.setId(patient_id.trim());
						patient.setTrialId(trial.getId());
						
						if (!patients.contains(patient)) {
							patients.add(patient);
						}
					}
					
					if (!readings.contains(reading)) {
						readings.add(reading);
					}
				}
				
				jsonReader.endObject();
			}
			
			jsonReader.endArray();
		} catch (IOException | IllegalStateException | NumberFormatException | DateTimeException ex) {
			throw new TrialException("An error was encountered while parsing the import stream", ex);
		}
	}
}
