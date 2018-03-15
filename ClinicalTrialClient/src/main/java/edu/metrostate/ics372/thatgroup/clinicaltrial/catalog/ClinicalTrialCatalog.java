package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.PatientFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;

public class ClinicalTrialCatalog implements TrialCatalog {
	private Connection connection;
	private String catalogStoragePath;
	private PreparedStatement pstmt;
	private ResultSet queryResult;

	@Override
	public boolean init() {
		catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		boolean wasPathSet = false;
		if (!Files.exists(Paths.get(catalogStoragePath))) {
			if (new File(catalogStoragePath).mkdirs()) {
				wasPathSet = true;
			}
		}
		return wasPathSet;
	}

	@Override
	public boolean createTrialCatalog(Trial trial) {
		boolean catalogWasCreated = false;
		String trialName = trial.getId();
		String catalogFilePath = catalogStoragePath + trialName.concat(ClinicalTrialCatalogUtilIty.CATALOG_EXTENSION);
		if (!Files.exists(Paths.get(catalogFilePath))) {
			if (ClinicalTrialCatalogUtilIty.writeAndInitializeCatalogFile(new File(catalogFilePath), trialName)) {
				catalogWasCreated = true;
			}
		}
		return catalogWasCreated;
	}

	@Override
	public boolean insertClinic(Clinic clinic) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.INSERT_CLINIC.getStatement());
			pstmt.setString(1, clinic.getId());
			pstmt.setString(2, clinic.getName());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Clinic getClinic(String clinicId) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		Clinic clinic = null;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.GET_CLINIC.getStatement());
			pstmt.setString(1, clinicId);
			queryResult = pstmt.executeQuery();
			clinic = getClinicList(queryResult).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clinic;
	}

	@Override
	public boolean updateClinic(Clinic clinic) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.UPDATE_CLINIC.getStatement());
			pstmt.setString(1, clinic.getId());
			pstmt.setString(2, clinic.getName());
			pstmt.setString(3, clinic.getId());
			queryResult = pstmt.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeClinic(Clinic clinic) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.REMOVE_CLINIC.getStatement());
			pstmt.setString(1, clinic.getId());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insertPatient(Patient patient) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.INSERT_PATIENT.getStatement());
			pstmt.setString(1, patient.getId());
			pstmt.setInt(2, patient.getJournalSize());
			pstmt.setDate(3, Date.valueOf(patient.getTrialStartDate()));
			pstmt.setDate(4, Date.valueOf(patient.getTrialEndDate()));
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Patient getPatient(String patientId) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		Patient patient = null;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.GET_PATIENT.getStatement());
			pstmt.setString(1, patientId);
			queryResult = pstmt.executeQuery();
			patient = getPatientList(queryResult).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patient;
	}

	@Override
	public boolean updatePatient(Patient patient) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.UPDATE_PATIENT.getStatement());
			pstmt.setString(1, patient.getId());
			pstmt.setInt(2, patient.getJournalSize());
			pstmt.setDate(3, Date.valueOf(patient.getTrialStartDate()));
			pstmt.setDate(4, Date.valueOf(patient.getTrialEndDate()));
			pstmt.setString(5, patient.getId());
			queryResult = pstmt.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removePatient(Patient patient) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.REMOVE_PATIENT.getStatement());
			pstmt.setString(1, patient.getId());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insertReading(Reading reading) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.INSERT_CLINIC.getStatement());
			pstmt.setString(1, reading.getId());
			pstmt.setString(2, reading.getPatientId());
			// pstmt.setString(3, reading.getClinicId()); TODO
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Reading getReading(String readingId) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		Reading reading = null;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.GET_READING.getStatement());
			pstmt.setString(1, readingId);
			queryResult = pstmt.executeQuery();
			reading = getReadingList(queryResult).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reading;
	}

	@Override
	public boolean updateReading(Reading reading) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeReading(Reading reading) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		try {
			pstmt = connection.prepareStatement(ClinicalStatement.REMOVE_READING.getStatement());
			pstmt.setString(1, reading.getId());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<String> getAllTrialCatalogNamesInDirectory() {
		List<String> catalogs = new ArrayList<>();
		for (File file : new File(catalogStoragePath).listFiles()) {
			String catalogName = file.getName().replaceAll(ClinicalTrialCatalogUtilIty.CATALOG_EXTENSION, "");
			catalogs.add(catalogName);
		}
		return catalogs;
	}

	@Override
	public List<Clinic> getAllClinics() {
		return getClinicList(getResultSetForGetAllStatement(ClinicalStatement.GET_ALL_CLINICS));
	}

	@Override
	public List<Patient> getAllPatients() {
		return getPatientList(getResultSetForGetAllStatement(ClinicalStatement.GET_ALL_PATIENTS));
	}

	@Override
	public List<Reading> getAllReadings() {
		return getReadingList(getResultSetForGetAllStatement(ClinicalStatement.GET_ALL_READINGS));
	}

	@Override
	public List<Reading> getReadingsForPatient(Patient patient) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		List<Reading> patientReadings = null;
		try {
			PreparedStatement pstmt = connection
					.prepareStatement(ClinicalStatement.GET_PATIENT_READINGS.getStatement());
			pstmt.setString(1, patient.getId());
			queryResult = pstmt.executeQuery(pstmt.toString());
			patientReadings = getReadingList(queryResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patientReadings;
	}

	@Override
	public List<Reading> getReadingsForClinic(Clinic clinic) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		List<Reading> clinicReadings = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement(ClinicalStatement.GET_CLINIC_READINGS.getStatement());
			pstmt.setString(1, clinic.getId());
			queryResult = pstmt.executeQuery(pstmt.toString());
			clinicReadings = getReadingList(queryResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clinicReadings;
	}

	//////////////////////// End of Interface /////////////////////////////////////

	private ResultSet getResultSetForGetAllStatement(ClinicalStatement statement) {
		connection = ClinicalTrialCatalogUtilIty.currentCatalogConnection;
		ResultSet queryResult = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement(statement.getStatement());
			queryResult = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryResult;
	}

	private List<Clinic> getClinicList(ResultSet queryResult) {
		List<Clinic> clinics = new ArrayList<>();
		try {
			while (queryResult.next()) {
				Clinic clinic = new Clinic();
				clinic.setId(queryResult.getString("id"));
				clinic.setName(queryResult.getString("name"));
				clinic.setTrialId(ClinicalTrialCatalogUtilIty.currentCatalogName);
				clinics.add(clinic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clinics;
	}

	private List<Patient> getPatientList(ResultSet queryResult) {
		List<Patient> patients = new ArrayList<>();
		try {
			while (queryResult.next()) {
				Patient patient = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
				patient.setId(queryResult.getString("id"));
				patient.setTrialId(ClinicalTrialCatalogUtilIty.currentCatalogName);
				patient.setTrialStartDate(queryResult.getDate("trial_start_date").toLocalDate());
				patient.setTrialEndDate(queryResult.getDate("trial_end_date").toLocalDate());
				for (Reading reading : getReadingsForPatient(patient)) {
					patient.addReading(reading);
				}
				patients.add(patient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

	private List<Reading> getReadingList(ResultSet queryResult) {
		List<Reading> readings = new ArrayList<>();
		try {
			while (queryResult.next()) {
				Reading reading = ReadingFactory.getReading(queryResult.getString("type"));
				reading.setId(queryResult.getString("id"));
				reading.setValue(queryResult.getString("value"));
				reading.setPatientId(queryResult.getString("patient_id"));
				reading.setDate(LocalDateTime.ofInstant(queryResult.getDate("date").toInstant(),
						ZoneId.systemDefault()));
				readings.add(reading);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return readings;
	}
}
