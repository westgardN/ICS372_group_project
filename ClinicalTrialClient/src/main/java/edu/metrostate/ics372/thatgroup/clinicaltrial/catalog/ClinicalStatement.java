package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

public class ClinicalStatement {
	public static final String CREATE_TABLE_TRIAL = "CREATE TABLE IF NOT EXISTS trial (\n"
            + "	id varchar(32) UNIQUE NOT NULL PRIMARY KEY,\n"
            + "	start_date date,\n"
            + "	end_date date \n"
            + ");";
	public static final String CREATE_TABLE_CLINICS = "CREATE TABLE IF NOT EXISTS clinics (\n"
            + "	id varchar(32) UNIQUE NOT NULL PRIMARY KEY,\n"
            + "	name varchar(255) UNIQUE NOT NULL,\n"
            + "	trial_id varchar(32) NOT NULL,\n"
            + "	FOREIGN KEY (trial_id) REFERENCES trial(id)\n"
            + ");";
	public static final String CREATE_TABLE_PATIENTS = "CREATE TABLE IF NOT EXISTS patients (\n"
            + "	id varchar(32) UNIQUE NOT NULL PRIMARY KEY,\n"
            + "	trial_id varchar(32),\n"
            + "	trial_start_date date,\n"
            + "	trial_end_date date\n"
            + ");";
	public static final String CREATE_TABLE_READINGS = "CREATE TABLE IF NOT EXISTS readings (\n"
            + "	id varchar(32) UNIQUE NOT NULL PRIMARY KEY,\n"
            + "	patient_id varchar(32) NOT NULL,\n"
            + "	clinic_id varchar(32) NOT NULL,\n"
            + "	type varchar(32) NOT NULL,\n"
            + "	value varchar(32) NOT NULL,\n"
            + "	date date NOT NULL,\n"
            + "	submitted_by varchar(32),\n"
            + "	FOREIGN KEY (patient_id) REFERENCES patients(id),\n"
            + "	FOREIGN KEY (clinic_id) REFERENCES clinics(id)\n"
            + ");";
	// Clinics
	public static final String INSERT_CLINIC = "INSERT INTO clinics (id, name, trial_id) VALUES(?,?,?)";
	public static final String GET_CLINIC = "SELECT * FROM clinics WHERE id = ?";
	public static final String UPDATE_CLINIC = "UPDATE clinics SET id = ?, name = ? WHERE id = ?";
	public static final String REMOVE_CLINIC = "DELETE FROM clinics WHERE id = ?";
	
	// Patients
	public static final String INSERT_PATIENT = "INSERT INTO patients (id, num_readings, trial_start_date, trial_end_date) VALUES(?,?,?,?)";
	public static final String GET_PATIENT = "SELECT * FROM patients WHERE id = ?";
	public static final String UPDATE_PATIENT = "UPDATE patients SET id = ?, num_readings = ?, trial_start_date = ?, trial_end_date = ? WHERE id = ?";
	public static final String REMOVE_PATIENT = "DELETE FROM patients WHERE id = ?";
	
	// Readings
	public static final String INSERT_READING = "INSERT INTO readings(id, patient_id, clinic_id, type, value, date) VALUES(?,?,?,?,?,?)";
	public static final String GET_READING = "SELECT * FROM readings WHERE id = ?";
	public static final String UPDATE_READING = "";
	public static final String REMOVE_READING = "DELETE FROM readings WHERE id = ?";
	
	// Get All
	public static final String GET_ALL_CLINICS = "SELECT * FROM clinics";
	public static final String GET_ALL_PATIENTS = "SELECT * FROM patients";
	public static final String GET_ALL_READINGS = "SELECT * FROM readings";
	public static final String GET_PATIENT_READINGS = "SELECT * FROM readings WHERE patient_id = ?";
	public static final String GET_CLINIC_READINGS = "SELECT * FROM readings WHERE clinic_id = ?";

}
