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
            + "	date timestamp NOT NULL,\n"
            + "	FOREIGN KEY (patient_id) REFERENCES patients(id),\n"
            + "	FOREIGN KEY (clinic_id) REFERENCES clinics(id)\n"
            + ");";

	public static final String MIGRATION_PROJECT_3_00 = "PRAGMA table_info('patients');";
	
	public static final String MIGRATION_PROJECT_3_01 = "CREATE TABLE IF NOT EXISTS patient_status (\n"
			+ " id varchar(32) NOT NULL PRIMARY KEY,\n"
			+ " display_status varchar(32) NOT NULL\n"
			+ ");";
	
	public static final String MIGRATION_PROJECT_3_02 = "INSERT INTO patient_status (id, display_status)\n"
			+ " VALUES ('INACTIVE', 'Inactive'), ('ACTIVE', 'Active'), ('WITHDRAWN', 'Withdrawn'), ('FAILED', 'Failed'),\n"
			+ "('COMPLETED', 'Completed');";
	
	public static final String MIGRATION_PROJECT_3_03 = "ALTER TABLE patients ADD status_id varchar(32);";
	
	public static final String MIGRATION_PROJECT_3_04 = "UPDATE patients SET status_id = 'INACTIVE'\n"
			+ " WHERE start_date IS NULL;";
	
	public static final String MIGRATION_PROJECT_3_05 = "UPDATE patients SET status_id = 'COMPLETED'\n"
			+ " WHERE start_date IS NOT NULL AND end_date IS NOT NULL;";
	
	public static final String MIGRATION_PROJECT_3_06 = "UPDATE patients SET status_id = 'ACTIVE'\n"
			+ " WHERE start_date IS NOT NULL AND end_date IS NULL;";
	
	public static final String MIGRATION_PROJECT_3_07 = "ALTER TABLE patients RENAME TO temp_patients;";
	
	public static final String MIGRATION_PROJECT_3_08 = "CREATE TABLE IF NOT EXISTS patients (\n"
            + "	id varchar(32) NOT NULL PRIMARY KEY,\n"
            + "	trial_id varchar(32),\n"
            + "	start_date date,\n"
            + "	end_date date,\n"
            + "	status_id varchar(32),\n"
            + " FOREIGN KEY (trial_id) REFERENCES trials(id)\n"
            + " FOREIGN KEY (status_id) REFERENCES patient_status(id)\n"
            + ");";
	
	public static final String MIGRATION_PROJECT_3_09 = "INSERT INTO patients (id, trial_id, start_date, end_date, status_id)\n"
			+ " SELECT id, trial_id, start_date, end_date, status_id FROM temp_patients;";
	
	public static final String MIGRATION_PROJECT_3_10 = "DROP TABLE temp_patients;";
	
	
	// Trials
	public static final String INSERT_TRIAL = "INSERT INTO trials (id, start_date, end_date) VALUES(?,?,?)";
	public static final String GET_TRIAL = "SELECT id, start_date, end_date FROM trials WHERE id = ?";
	
	// Clinics
	public static final String INSERT_CLINIC = "INSERT INTO clinics (id, name, trial_id) VALUES(?,?,?)";
	public static final String GET_CLINIC = "SELECT id, name, trial_id FROM clinics WHERE id = ? AND trial_id = ?";
	public static final String UPDATE_CLINIC = "UPDATE clinics SET trial_id = ?, name = ? WHERE id = ?";
	public static final String DELETE_CLINIC = "DELETE FROM clinics WHERE id = ? AND trial_id = ?";
	
	// Patients
	public static final String INSERT_PATIENT = "INSERT INTO patients (id, start_date, end_date, trial_id, status_id) VALUES(?,?,?,?,?)";
	public static final String GET_PATIENT = "SELECT id, trial_id, start_date, end_date, status_id FROM patients WHERE id = ? AND trial_id = ?";
	public static final String UPDATE_PATIENT = "UPDATE patients SET trial_id = ?, start_date = ?, end_date = ?, status_id = ? WHERE id = ?";
	public static final String DELETE_PATIENT = "DELETE FROM patients WHERE id = ? AND trial_id = ?";
	
	// PatientStatus
	public static final String INSERT_PATIENT_STATUS = "INSERT INTO patient_status (id, display_status) VALUES(?,?)";
	public static final String GET_PATIENT_STATUS = "SELECT id, display_status FROM patient_status WHERE id = ?";
	public static final String UPDATE_PATIENT_STATUS = "UPDATE patient_status SET display_status = ? WHERE id = ?";
	public static final String DELETE_PATIENT_STATUS = "DELETE FROM patient_status WHERE id = ?";
	
	// Readings
	public static final String INSERT_READING = "INSERT INTO readings(id, patient_id, clinic_id, type, value, date) VALUES(?,?,?,?,?,?)";
	public static final String GET_READING = "SELECT id, patient_id, clinic_id, type, date, value FROM readings WHERE id = ?";
	public static final String UPDATE_READING = "UPDATE readings SET patient_id = ?, clinic_id = ?, type = ?, value = ?, date = ? WHERE id = ?";
	public static final String DELETE_READING = "DELETE FROM readings WHERE id = ? AND patient_id = ? AND clinic_id = ?";
	
	// Get All
	public static final String GET_ALL_CLINICS = "SELECT id, name, trial_id FROM clinics WHERE trial_id = ?";
	public static final String GET_ALL_PATIENTS = "SELECT id, trial_id, start_date, end_date, status_id FROM patients WHERE trial_id = ?";
	public static final String GET_ALL_PATIENT_STATUSES = "SELECT id, display_status FROM patient_status;";
	public static final String GET_ALL_END_TRIAL_PATIENT_STATUSES = "SELECT id, display_status FROM patient_status WHERE id IN ('COMPLETED', 'FAILED', 'WITHDRAWN');";
	public static final String GET_ALL_ACTIVE_PATIENTS = "SELECT id, trial_id, start_date, end_date, status_id FROM patients WHERE trial_id = ? AND status_id = 'ACTIVE'";
	public static final String GET_ALL_INACTIVE_PATIENTS = "SELECT id, trial_id, start_date, end_date, status_id FROM patients WHERE trial_id = ? AND status_id = 'INACTIVE'";
	public static final String GET_ALL_READINGS = "SELECT id, patient_id, clinic_id, type, date, value FROM readings";
	public static final String GET_PATIENT_READINGS = "SELECT r.id, patient_id, clinic_id, type, date, value FROM readings r INNER JOIN patients p ON r.patient_id = p.id WHERE p.status_id IN ('ACTIVE', 'COMPLETED') AND patient_id = ?";
	public static final String GET_CLINIC_READINGS = "SELECT r.id, patient_id, clinic_id, type, date, value FROM readings r INNER JOIN patients p ON r.patient_id = p.id WHERE p.status_id IN ('ACTIVE', 'COMPLETED') AND clinic_id = ?";
	public static final String GET_ALL_ACTIVE_AND_COMPLETE_READINGS = "SELECT r.id, patient_id, clinic_id, type, date, value FROM readings r INNER JOIN patients p ON r.patient_id = p.id WHERE p.status_id IN ('ACTIVE', 'COMPLETED')";
	public static final String HAS_READINGS_CLINIC = "SELECT COUNT(r.id) AS reading_count FROM readings r INNER JOIN patients p ON r.patient_id = p.id INNER JOIN clinics c ON r.clinic_id = c.id WHERE p.status_id IN ('ACTIVE', 'COMPLETED') AND c.id = ? AND c.trial_id = ?";
	public static final String HAS_READINGS_PATIENT = "SELECT COUNT(r.id) AS reading_count FROM readings r INNER JOIN patients p ON r.patient_id = p.id WHERE p.status_id IN ('ACTIVE', 'COMPLETED') AND p.id = ? AND p.trial_id = ?";

}
