package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

public class ClinicalStatement {
	public static final String CREATE_TABLE_TRIAL = "CREATE TABLE IF NOT EXISTS trials (\n"
            + "	id varchar(32) NOT NULL PRIMARY KEY,\n"
            + "	start_date date,\n"
            + "	end_date date \n"
            + ");";
	public static final String CREATE_TABLE_CLINICS = "CREATE TABLE IF NOT EXISTS clinics (\n"
            + "	id varchar(32) NOT NULL PRIMARY KEY,\n"
            + "	name varchar(255) NOT NULL,\n"
            + "	trial_id varchar(32) NOT NULL,\n"
            + "	FOREIGN KEY (trial_id) REFERENCES trial(id)\n"
            + ");";
	public static final String CREATE_TABLE_PATIENTS = "CREATE TABLE IF NOT EXISTS patients (\n"
            + "	id varchar(32) NOT NULL PRIMARY KEY,\n"
            + "	trial_id varchar(32),\n"
            + "	start_date date,\n"
            + "	end_date date,\n"
            + " FOREIGN KEY (trial_id) REFERENCES trials(id)\n"
            + ");";
	public static final String CREATE_TABLE_READINGS = "CREATE TABLE IF NOT EXISTS readings (\n"
            + "	id varchar(32) NOT NULL PRIMARY KEY,\n"
            + "	patient_id varchar(32) NOT NULL,\n"
            + "	clinic_id varchar(32) NOT NULL,\n"
            + "	type varchar(32) NOT NULL,\n"
            + "	value varchar(32) NOT NULL,\n"
            + "	date date NOT NULL,\n"
            + "	FOREIGN KEY (patient_id) REFERENCES patients(id),\n"
            + "	FOREIGN KEY (clinic_id) REFERENCES clinics(id)\n"
            + ");";

	public static final String GET_TRIAL = "SELECT id, name, start_date, end_date FROM trials WHERE id = ?";
	
	// Clinics
	public static final String INSERT_CLINIC = "INSERT INTO clinics (id, name, trial_id) VALUES(?,?,?)";
	public static final String GET_CLINIC = "SELECT id, name, trial_id FROM clinics WHERE id = ? AND trial_id = ?";
	public static final String UPDATE_CLINIC = "UPDATE clinics SET trial_id = ?, name = ? WHERE id = ?";
	public static final String DELETE_CLINIC = "DELETE FROM clinics WHERE id = ? AND trial_id = ?";
	
	// Patients
	public static final String INSERT_PATIENT = "INSERT INTO patients (id, start_date, end_date, trial_id) VALUES(?,?,?,?)";
	public static final String GET_PATIENT = "SELECT id, trial_id, start_date, end_date FROM patients WHERE id = ? AND trial_id = ?";
	public static final String UPDATE_PATIENT = "UPDATE patients SET trial_id = ?, start_date = ?, end_date = ? WHERE id = ?";
	public static final String DELETE_PATIENT = "DELETE FROM patients WHERE id = ? AND trial_id = ?";
	
	// Readings
	public static final String INSERT_READING = "INSERT INTO readings(id, patient_id, clinic_id, type, value, date) VALUES(?,?,?,?,?,?)";
	public static final String GET_READING = "SELECT id, patient_id, clinic_id, type, date, value FROM readings WHERE id = ?";
	public static final String UPDATE_READING = "UPDATE readings SET patient_id = ?, clinic_id = ?, type = ?, value = ?, date = ? WHERE id = ?";
	public static final String DELETE_READING = "DELETE FROM readings WHERE id = ? AND patient_id = ? AND clinic_id = ?";
	
	// Get All
	public static final String GET_ALL_CLINICS = "SELECT id, name, trial_id FROM clinics WHERE trial_id = ?";
	public static final String GET_ALL_PATIENTS = "SELECT id, trial_id, start_date, end_date FROM patients WHERE trial_id = ?";
	public static final String GET_ALL_ACTIVE_PATIENTS = "SELECT id, trial_id, start_date, end_date FROM patients WHERE trial_id = ? AND start_date IS NOT NULL AND end_date IS NULL";
	public static final String GET_ALL_INACTIVE_PATIENTS = "SELECT id, trial_id, start_date, end_date FROM patients WHERE trial_id = ? AND start_date IS NOT NULL AND end_date IS NOT NULL";
	public static final String GET_ALL_READINGS = "SELECT id, patient_id, clinic_id, type, date, value FROM readings";
	public static final String GET_PATIENT_READINGS = "SELECT id, patient_id, clinic_id, type, date, value FROM readings WHERE patient_id = ?";
	public static final String GET_CLINIC_READINGS = "SELECT id, patient_id, clinic_id, type, date, value FROM readings WHERE clinic_id = ?";

}
