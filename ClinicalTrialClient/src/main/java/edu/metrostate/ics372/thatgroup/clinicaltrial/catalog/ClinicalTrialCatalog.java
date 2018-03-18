package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.File;
import java.io.IOException;
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
	private String catalogStoragePath;
	private Trial trial;

	@Override
	public boolean init(Trial trial) throws TrialCatalogException {
		boolean answer = true;
		
		catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		if (!Files.exists(Paths.get(catalogStoragePath))) {
			if (!Paths.get(catalogStoragePath).toFile().mkdirs()) {
				answer = false;
			}
		}
		if (answer) {
				answer = createTrialCatalog(trial);
			if (answer) {
				this.trial = trial;
			}
		}
		return answer;
	}
	
	@Override
	public boolean isInit() {
		return trial != null;
	}

	/**
	 * Creates a new ClinicalTrialCatalog in the catalogs directory.
	 * 
	 * @param trial
	 *            the <code>Trial</code> that is to be made into a Trial Catalog
	 * @return true if the <code>ClinicalTrialCatalog</code> creation was
	 *         successful, else false
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private boolean createTrialCatalog(Trial trial) throws TrialCatalogException {
		boolean answer = false;
		String trialName = trial.getId();
		String catalogFilePath = catalogStoragePath + trialName.concat(ClinicalTrialCatalogUtilIty.CATALOG_EXTENSION);
		try {
			if (!Files.exists(Paths.get(catalogFilePath))) {
					if (ClinicalTrialCatalogUtilIty.writeAndInitializeCatalogFile(Paths.get(catalogFilePath).toFile(), trialName)) {
						answer = true;
					}
			}
		} catch (SQLException | IOException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		return answer;
	}

	@Override
	public boolean insertClinic(Clinic clinic) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.INSERT_CLINIC);){
			pstmt.setString(1, clinic.getId());
			pstmt.setString(2, clinic.getName());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
	}

	@Override
	public Clinic getClinic(String clinicId) throws TrialCatalogException {
		Clinic clinic = null;
		try(Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.GET_CLINIC);){
			pstmt.setString(1, clinicId);
			ResultSet queryResult = pstmt.executeQuery();
			clinic = getClinicList(queryResult).get(0);
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		return clinic;
	}

	@Override
	public boolean updateClinic(Clinic clinic) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.UPDATE_CLINIC);){
			pstmt.setString(1, clinic.getId());
			pstmt.setString(2, clinic.getName());
			pstmt.setString(3, clinic.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean removeClinic(Clinic clinic) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.UPDATE_CLINIC);) {
			pstmt.setString(1, clinic.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
	}

	@Override
	public boolean insertPatient(Patient patient) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.INSERT_PATIENT);) {
			pstmt.setString(1, patient.getId());
			pstmt.setInt(2, patient.getJournalSize());
			pstmt.setDate(3, Date.valueOf(patient.getTrialStartDate()));
			pstmt.setDate(4, Date.valueOf(patient.getTrialEndDate()));
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
	}

	@Override
	public Patient getPatient(String patientId) throws TrialCatalogException {
		Patient patient = null;
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.GET_PATIENT);) {
			pstmt.setString(1, patientId);
			ResultSet queryResult = pstmt.executeQuery();
			patient = getPatientList(queryResult).get(0);
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return patient;
	}

	@Override
	public boolean updatePatient(Patient patient) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.UPDATE_PATIENT);) {
			pstmt.setString(1, patient.getId());
			pstmt.setInt(2, patient.getJournalSize());
			pstmt.setDate(3, Date.valueOf(patient.getTrialStartDate()));
			pstmt.setDate(4, Date.valueOf(patient.getTrialEndDate()));
			pstmt.setString(5, patient.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
	}

	@Override
	public boolean removePatient(Patient patient) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.REMOVE_PATIENT);) {
			pstmt.setString(1, patient.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
	}

	@Override
	public boolean insertReading(Reading reading) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.REMOVE_PATIENT);) {
			pstmt.setString(1, reading.getId());
			pstmt.setString(2, reading.getPatientId());
			pstmt.setString(3, reading.getClinicId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
	}

	@Override
	public Reading getReading(String readingId) throws TrialCatalogException {
		Reading reading = null;
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.GET_READING);) {
			pstmt.setString(1, readingId);
			ResultSet queryResult = pstmt.executeQuery();
			reading = getReadingList(queryResult).get(0);
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return reading;
	}

	@Override
	public boolean updateReading(Reading reading) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.UPDATE_READING);) {
			pstmt.setString(1, reading.getId());
			pstmt.setString(2, reading.getPatientId());
			pstmt.setString(3, reading.getClinicId());
			pstmt.setString(4, ReadingFactory.getReadingType(reading));
			pstmt.setDate(5, Date.valueOf(reading.getDate().toString()));
			pstmt.setString(6, reading.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
	}

	@Override
	public boolean removeReading(Reading reading) throws TrialCatalogException {
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.REMOVE_READING);) {
			pstmt.setString(1, reading.getId());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
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
	public List<Clinic> getAllClinics() throws TrialCatalogException {
		return getClinicList(getResultSetForGetAllStatement(ClinicalStatement.GET_ALL_CLINICS));
	}

	@Override
	public List<Patient> getAllPatients() throws TrialCatalogException {
		return getPatientList(getResultSetForGetAllStatement(ClinicalStatement.GET_ALL_PATIENTS));
	}

	@Override
	public List<Reading> getAllReadings() throws TrialCatalogException {
		return getReadingList(getResultSetForGetAllStatement(ClinicalStatement.GET_ALL_READINGS));
	}

	@Override
	public List<Reading> getReadingsForPatient(Patient patient) throws TrialCatalogException {
		List<Reading> patientReadings = null;
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.GET_PATIENT_READINGS);) {
			pstmt.setString(1, patient.getId());
			ResultSet queryResult = pstmt.executeQuery();
			patientReadings = getReadingList(queryResult);
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return patientReadings;
	}

	@Override
	public List<Reading> getReadingsForClinic(Clinic clinic) throws TrialCatalogException {
		List<Reading> clinicReadings = null;
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(ClinicalStatement.GET_CLINIC_READINGS);) {
			pstmt.setString(1, clinic.getId());
			ResultSet queryResult = pstmt.executeQuery();
			clinicReadings = getReadingList(queryResult);
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return clinicReadings;
	}

	//////////////////////// End of Interface /////////////////////////////////////

	private ResultSet getResultSetForGetAllStatement(String statement) throws TrialCatalogException {
		ResultSet queryResult = null;
		try (Connection conn = ClinicalTrialCatalogUtilIty.getConnection(trial.getId());
				PreparedStatement pstmt = conn.prepareStatement(statement);) {
			queryResult = pstmt.executeQuery();
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return queryResult;
	}

	private List<Clinic> getClinicList(ResultSet queryResult) throws TrialCatalogException {
		List<Clinic> clinics = new ArrayList<>();
		try {
			while (queryResult.next()) {
				Clinic clinic = new Clinic();
				clinic.setId(queryResult.getString("id"));
				clinic.setName(queryResult.getString("name"));
				clinic.setTrialId(trial.getId());
				clinics.add(clinic);
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return clinics;
	}

	private List<Patient> getPatientList(ResultSet queryResult) throws TrialCatalogException {
		List<Patient> patients = new ArrayList<>();
		try {
			while (queryResult.next()) {
				Patient patient = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
				patient.setId(queryResult.getString("id"));
				patient.setTrialId(trial.getId());
				patient.setTrialStartDate(queryResult.getDate("trial_start_date").toLocalDate());
				patient.setTrialEndDate(queryResult.getDate("trial_end_date").toLocalDate());
				for (Reading reading : getReadingsForPatient(patient)) {
					patient.addReading(reading);
				}
				patients.add(patient);
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return patients;
	}

	private List<Reading> getReadingList(ResultSet queryResult) throws TrialCatalogException {
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
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return readings;
	}
}

