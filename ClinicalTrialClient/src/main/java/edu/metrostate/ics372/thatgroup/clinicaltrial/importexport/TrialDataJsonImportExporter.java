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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.UnitValue;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The TrialDataJsonImportExporter is used for importing and exporting
 * trial data to and from a JSON file. It includes methods to read
 * from a JSON file and write to a JSON file.
 * 
 * @author That Group
 *
 */
public class TrialDataJsonImportExporter implements TrialDataImporter, TrialDataExporter {
	private static final String MSG_UNKNOWN_READING_PROPERTY = " unknown Reading property: ";
	private static final String MSG_READING_ID = "Reading ID: ";
	private static final String MSG_WITH_VALUE = " with value: ";
	private static final String MSG_UNKNOWN_PATIENT_PROPERTY = " unknown Patient property: ";
	private static final String MSG_PATIENT_ID = "Patient ID: ";
	private static final String MSG_UNKNOWN_CLINIC_PROPERTY = " unknown Clinic property: ";
	private static final String MSG_CLINIC_ID = "Clinic ID: ";
	private static final String JSON_TRIAL_ID = "trial_id";
	private static final String JSON_PATIENTS = "patients";
	private static final String JSON_CLINICS = "clinics";
	private static final String JSON_PATIENT_READINGS = "patient_readings";
	private static final String JSON_READING_ID = "reading_id";
	private static final String JSON_PATIENT_ID = "patient_id";
	private static final String JSON_CLINIC_ID = "clinic_id";
	private static final String JSON_CLINIC_NAME = "clinic_name";
	private static final String JSON_READING_TYPE = "reading_type";
	private static final String JSON_READING_VALUE = "reading_value";
	private static final String JSON_READING_VALUE_UNIT = "reading_value_unit";
	private static final String JSON_READING_DATE = "reading_date";
	private static final String JSON_PATIENT_TRIAL_START_DATE = "patient_trial_start_date";
	private static final String JSON_PATIENT_TRIAL_END_DATE = "patient_trial_end_date";
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
	 * Reads the specified JSON file and returns true if the import was successful.
	 * If the import was successful, then the lists for the imported objects will be
	 * valid after this method returns.
	 * 
	 * @param trial the trial that the data is being imported into. Cannot be null.
	 * @param is the ImportStream to read the data from.
	 * 
	 * @return true if the import was successful; otherwise false is returned.
	 * 
	 * @throws TrialException indicates an error occurred while operating on the file. 
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
				
				if (name.equalsIgnoreCase(JSON_PATIENT_READINGS)) {
					readReadings(jsonReader);
				} else if (name.equalsIgnoreCase(JSON_CLINICS)) {
					readClinics(jsonReader);
				} else if (name.equalsIgnoreCase(JSON_PATIENTS)) {
					readPatients(jsonReader);
				}
			}
			
			jsonReader.endObject();
			
			answer = true;
		} catch (JsonSyntaxException | IOException e) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_BAD_STREAM + e.getMessage(), e);
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
	 * Writes the lists of Reading objects, Patient objects, and Clinic objects associated
	 * with this trial to the specified file in JSON format.
	 * 
	 * @param os the OutputStream that the data will be written to.
	 *  
	 * @throws TrialException indicates an error occurred while exporting the data.
	 */
	@Override
	public boolean write(OutputStream os) throws TrialException {
		prepareWrite();
		boolean answer = false;
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
			JsonWriter jsonWriter = new JsonWriter(bw);
			jsonWriter.setIndent("    ");
			
			jsonWriter.beginObject();
			
			if (clinics != null) {
				jsonWriter.name(JSON_CLINICS);
				jsonWriter.beginArray();
				
				for (Clinic clinic : clinics) {
					jsonWriter.beginObject();
					jsonWriter.name(JSON_CLINIC_ID).value(clinic.getId());
					jsonWriter.name(JSON_CLINIC_NAME).value(clinic.getName());
					jsonWriter.name(JSON_TRIAL_ID).value(clinic.getTrialId());
					jsonWriter.endObject();
				}
				jsonWriter.endArray();
			}
			
			if (patients != null) {
				jsonWriter.name(JSON_PATIENTS);
				jsonWriter.beginArray();
				
				for (Patient patient : patients) {
					jsonWriter.beginObject();
					jsonWriter.name(JSON_PATIENT_ID).value(patient.getId());
					writeDates(jsonWriter, patient);
					jsonWriter.name(JSON_TRIAL_ID).value(patient.getTrialId());
					jsonWriter.endObject();
				}
				jsonWriter.endArray();
			}
			
			jsonWriter.name(JSON_PATIENT_READINGS);
			jsonWriter.beginArray();
			
			for (Reading reading : readings) {
				jsonWriter.beginObject();
				String type = ReadingFactory.getReadingType(reading);
				jsonWriter.name(JSON_PATIENT_ID).value(reading.getPatientId());
				jsonWriter.name(JSON_CLINIC_ID).value(reading.getClinicId());
				jsonWriter.name(JSON_READING_TYPE).value(type);
				jsonWriter.name(JSON_READING_ID).value(reading.getId());
				writeValue(jsonWriter, type, reading);
				writeDateTime(jsonWriter, reading);
				jsonWriter.endObject();
			}
			
			jsonWriter.endArray();
			jsonWriter.endObject();
			
			answer = true;

		} catch (IOException e) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_EXPORTER_BAD_STREAM + e.getMessage(), e);
		}
		
		return answer;
	}

	///////////////////////////// End TrialDataExporter Interface ///////////////////
	
	private void writeDates(JsonWriter jsonWriter, Patient patient) throws IOException {
		long date = 0;
		
		if (patient.getTrialStartDate() != null) {
			date = patient.getTrialStartDate().toEpochDay();
			jsonWriter.name(JSON_PATIENT_TRIAL_START_DATE).value(date);
			date = 0;
		}
		
		if (patient.getTrialEndDate() != null) {
			date = patient.getTrialEndDate().toEpochDay();
			jsonWriter.name(JSON_PATIENT_TRIAL_END_DATE).value(date);
			date = 0;
		}		
	}

	private void writeDateTime(JsonWriter jsonWriter, Reading reading) throws IOException {
		long date = 0;
		
		if (reading.getDate() != null) {
			date = reading.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		}
		
		jsonWriter.name(JSON_READING_DATE).value(date);
	}

	private void writeValue(JsonWriter jsonWriter, String type, Reading reading) throws IOException {
		switch(type) {
			case ReadingFactory.TEMPERATURE:
			{
				boolean hasValue = reading.getValue() instanceof UnitValue;
				UnitValue unitValue = (UnitValue) reading.getValue();
				Double value = hasValue ? (Double) unitValue.getNumberValue() : 0.0;
				jsonWriter.name(JSON_READING_VALUE).value(value);
				String unit = hasValue ? unitValue.getUnit() : Strings.EMPTY;
				
				if (unit != null && !unit.trim().isEmpty()) {
					jsonWriter.name(JSON_READING_VALUE_UNIT).value(unit);
				}
				
				break;
			}
			case ReadingFactory.WEIGHT:
			{
				boolean hasValue = reading.getValue() instanceof UnitValue;
				UnitValue unitValue = (UnitValue) reading.getValue();
				Long value = hasValue ? (Long) unitValue.getNumberValue() : 0L;
				jsonWriter.name(JSON_READING_VALUE).value(value);
				String unit = hasValue ? unitValue.getUnit() : Strings.EMPTY;
				
				if (unit != null && !unit.trim().isEmpty()) {
					jsonWriter.name(JSON_READING_VALUE_UNIT).value(unit);
				}
				
				break;
			}
			case ReadingFactory.STEPS:
			{
				boolean hasValue = reading.getValue() instanceof Integer;
				Integer value = hasValue ? (Integer) reading.getValue() : 0;
				jsonWriter.name(JSON_READING_VALUE).value(value);
				break;
			}
			default:
				boolean hasValue = reading.getValue() != null;
				String value = hasValue ? reading.getValue().toString() : Strings.EMPTY;
				jsonWriter.name(JSON_READING_VALUE).value(value);
				break;
		}
		
	}

	private void prepareRead() throws TrialException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty()) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_BAD_TRIAL);
		}
		
		readings = new LinkedList<Reading>();
		clinics = new LinkedList<Clinic>();
		patients = new LinkedList<Patient>();
	}
	
	private void prepareWrite() throws TrialException {
		if ((readings == null || readings.isEmpty()) 
				&& (clinics == null || clinics.isEmpty())
				&& (patients == null || patients.isEmpty())) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_EXPORTER_NO_DATA);
		}
	}
	
	private void readClinics(JsonReader jsonReader) throws TrialException {
		try {
			jsonReader.beginArray();
			
			while (jsonReader.hasNext()) {
				String clinic_id = null;
				String clinic_name = null;
				String trial_id = trial.getId();
				
				jsonReader.beginObject();
				
				while (jsonReader.hasNext()) {
					String name = jsonReader.nextName();
					
					if (name.equalsIgnoreCase(JSON_CLINIC_ID)) {
						clinic_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_CLINIC_NAME)) {
						clinic_name = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_TRIAL_ID)) {
						jsonReader.nextString(); // ignore for now and just swallow the prop
					} else {
						Logger.getLogger(TrialDataJsonImportExporter.class.getName()).log(Level.INFO, MSG_CLINIC_ID + clinic_id + MSG_UNKNOWN_CLINIC_PROPERTY + name + MSG_WITH_VALUE + jsonReader.nextString());
					}
				}
				
				if (clinic_id != null && !clinic_id.trim().isEmpty()) {
					if (clinic_name == null || clinic_name.trim().isEmpty()) {
						clinic_name = clinic_id;
					}
					
					Clinic clinic = new Clinic(clinic_id, trial_id, clinic_name);
					
					if (!clinics.contains(clinic)) {
						clinics.add(clinic);
					} else {
						clinics.set(clinics.indexOf(clinic), clinic);
					}
				}
				
				jsonReader.endObject();
			}
			
			jsonReader.endArray();
		} catch (IOException | IllegalStateException | NumberFormatException | DateTimeException ex) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_PARSING_STREAM + ex.getMessage(), ex);
		}
	}
	
	private void readPatients(JsonReader jsonReader) throws TrialException {
		try {
			jsonReader.beginArray();
			
			while (jsonReader.hasNext()) {
				String patient_id = null;
				long lStartDate = -1;
				long lEndDate = -1;
				String trial_id = trial.getId();

				jsonReader.beginObject();
				
				while (jsonReader.hasNext()) {
					String name = jsonReader.nextName();
					
					if (name.equalsIgnoreCase(JSON_PATIENT_ID)) {
						patient_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_PATIENT_TRIAL_START_DATE)) {
						lStartDate = jsonReader.nextLong();
					} else if (name.equalsIgnoreCase(JSON_PATIENT_TRIAL_END_DATE)) {
						lEndDate = jsonReader.nextLong();
					} else if (name.equalsIgnoreCase(JSON_TRIAL_ID)) {
						jsonReader.nextString(); // ignore for now and just swallow the prop
					} else {
						Logger.getLogger(TrialDataJsonImportExporter.class.getName()).log(Level.INFO, MSG_PATIENT_ID + patient_id + MSG_UNKNOWN_PATIENT_PROPERTY + name + MSG_WITH_VALUE + jsonReader.nextString());
					}
				}
				
				if (patient_id != null && !patient_id.trim().isEmpty()) {
					LocalDate startDate = lStartDate >= 0 ? LocalDate.ofEpochDay(lStartDate) : null;
					LocalDate endDate = lEndDate >= 0 ? LocalDate.ofEpochDay(lEndDate) : null;
					
					Patient patient = new Patient(patient_id, trial_id, startDate, endDate);
					
					if (!patients.contains(patient)) {
						patients.add(patient);
					} else {
						patients.set(patients.indexOf(patient), patient);
					}
				}
				
				jsonReader.endObject();
			}
			
			jsonReader.endArray();
		} catch (IOException | IllegalStateException | NumberFormatException | DateTimeException ex) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_PARSING_STREAM + ex.getMessage(), ex);
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
				String reading_value_unit = null;
				long reading_date = 0;
				
				jsonReader.beginObject();
				
				while (jsonReader.hasNext()) {
					String name = jsonReader.nextName();
					
					if (name.equalsIgnoreCase(JSON_PATIENT_ID)) {
						patient_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_CLINIC_ID)) {
						clinic_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_READING_TYPE)) {
						reading_type = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_READING_ID)) {
						reading_id = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_READING_VALUE)) {
						reading_value = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_READING_VALUE_UNIT)) {
						reading_value_unit = jsonReader.nextString();
					} else if (name.equalsIgnoreCase(JSON_READING_DATE)) {
						reading_date = jsonReader.nextLong();
					} else {
						Logger.getLogger(TrialDataJsonImportExporter.class.getName()).log(Level.INFO, MSG_READING_ID + reading_id + MSG_UNKNOWN_READING_PROPERTY + name + MSG_WITH_VALUE + jsonReader.nextString());
					}
				}
				
				if (reading_type != null && !reading_type.trim().isEmpty()) {
					Reading reading = ReadingFactory.getReading(reading_type);
					reading.setId(reading_id != null ? reading_id.trim() : null);
					reading.setClinicId(clinic_id != null ? clinic_id.trim() : null);
					reading.setPatientId(patient_id != null ? patient_id.trim() : null);
					reading_value = reading_value_unit != null ? reading_value + UnitValue.DELIM + reading_value_unit : reading_value;
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
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_PARSING_STREAM + ex.getMessage(), ex);
		}
	}
}
