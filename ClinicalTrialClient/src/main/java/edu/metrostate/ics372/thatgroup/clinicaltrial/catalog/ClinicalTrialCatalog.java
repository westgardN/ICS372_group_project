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
import java.util.LinkedList;
import java.util.List;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;

public class ClinicalTrialCatalog implements TrialCatalog {
	protected static final String END_DATE = "end_date";
	protected static final String START_DATE = "start_date";
	protected static final String ID = "id";
	protected static final String NAME = "name";
	protected static final String TRIAL_ID = "trial_id";
	protected static final String TYPE = "type";
	protected static final String PATIENT_ID = "patient_id";
	protected static final String CLINIC_ID = "clinic_id";
	protected static final String DATE = "date";
	protected static final String VALUE = "value";
	protected Trial trial;

	/**
	 * @param trial
	 * @throws TrialCatalogException
	 */
	private void validateParam(Trial trial) throws TrialCatalogException {
		if (!isValidTrial(trial)) {
			throw new TrialCatalogException("trial cannot be null and must have a valid id.");
		}
	}
	
	/**
	 * @param clinic
	 * @throws TrialCatalogException
	 */
	private void validateParam(Clinic clinic) throws TrialCatalogException {
		if (clinic == null || clinic.getId() == null || clinic.getId().trim().isEmpty()) {
			throw new TrialCatalogException("clinic cannot be null and must have a valid id.");
		}
	}
	
	/**
	 * @param patient
	 * @throws TrialCatalogException
	 */
	private void validateParam(Patient patient) throws TrialCatalogException {
		if (patient == null || patient.getId() == null || patient.getId().trim().isEmpty()) {
			throw new TrialCatalogException("patient cannot be null and must have a valid id.");
		}
	}
	
	/**
	 * @param reading
	 * @throws TrialCatalogException
	 */
	private void validateParam(Reading reading) throws TrialCatalogException {
		if (reading == null || reading.getId() == null || reading.getId().trim().isEmpty()) {
			throw new TrialCatalogException("reading cannot be null and must have a valid id.");
		}
	}
	
	/**
	 * @throws TrialCatalogException
	 */
	private void validateIsInit() throws TrialCatalogException {
		if (!isValidTrial(trial)) {
			throw new TrialCatalogException("There is no active trial. Please call init with a valid Trial.");
		}
	}
	
	private boolean isValidTrial(Trial trial) {
		return trial != null && trial.getId() != null && !trial.getId().trim().isEmpty();
	}
	
	/**
	 * Creates a new ClinicalTrialCatalog in the catalogs directory.
	 * 
	 * @param trial
	 *            the <code>Trial</code> that is to be made into a Trial Catalog
	 * @param catalogStoragePath 
	 * @return true if the <code>ClinicalTrialCatalog</code> creation was
	 *         successful, else false
	 * @throws SQLException 
	 * @throws IOException 
	 */
	protected boolean createTrialCatalog(Trial trial, String catalogStoragePath) throws TrialCatalogException {
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

	protected String getActiveId() {
		return trial != null ? trial.getId() : null;
	}
	
	protected Connection getConnection() throws SQLException {
		return ClinicalTrialCatalogUtilIty.getConnection(getActiveId());
	}
	
	protected PreparedStatement getPreparedSelect(final Connection conn, Trial trial) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_TRIAL);
		answer.setString(1, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelect(final Connection conn, Clinic clinic) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_CLINIC);
		answer.setString(1, clinic.getId());
		answer.setString(2, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelect(final Connection conn, Patient patient) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_PATIENT);
		answer.setString(1, patient.getId());
		answer.setString(2, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelect(final Connection conn, Reading reading) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_READING);
		answer.setString(1, reading.getId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllClinics(final Connection conn) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_ALL_CLINICS);
		answer.setString(1, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllPatients(final Connection conn) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_ALL_PATIENTS);
		answer.setString(1, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllActivePatients(final Connection conn) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_ALL_ACTIVE_PATIENTS);
		answer.setString(1, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllInactivePatients(final Connection conn) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_ALL_INACTIVE_PATIENTS);
		answer.setString(1, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllReadings(final Connection conn) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_PATIENT_READINGS);
		answer.setString(1, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllReadings(final Connection conn, Patient patient) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_PATIENT_READINGS);
		answer.setString(1, patient.getId());
		answer.setString(2, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllReadings(final Connection conn, Clinic clinic) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_CLINIC_READINGS);
		answer.setString(1, clinic.getId());
		answer.setString(2, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedInsert(final Connection conn, Clinic clinic) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.INSERT_CLINIC);
		answer.setString(1, clinic.getId());
		answer.setString(2, clinic.getName());
		answer.setString(3, getActiveId());
        return answer;
	}
	
	protected PreparedStatement getPreparedInsert(final Connection conn, Patient patient) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.INSERT_PATIENT);
		answer.setString(1, patient.getId());

		if (patient.getTrialStartDate() != null) {
			answer.setDate(2, Date.valueOf(patient.getTrialStartDate()));
        } else {
        	answer.setDate(2, null);
        }
		
		if (patient.getTrialEndDate() != null) {
			answer.setDate(3, Date.valueOf(patient.getTrialEndDate()));
        } else {
        	answer.setDate(3, null);
        }
		
		answer.setString(4, getActiveId());
		
        return answer;
	}
	
	protected PreparedStatement getPreparedInsert(final Connection conn, Reading reading) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.INSERT_READING);
		answer.setString(1, reading.getId());
		answer.setString(2, reading.getPatientId());
		answer.setString(3, reading.getClinicId());
		answer.setString(4, ReadingFactory.getReadingType(reading));
		answer.setString(5, reading.getValue() != null ? reading.getValue().toString() : null);
		
		if (reading.getDate() != null) {
			answer.setDate(6, Date.valueOf(reading.getDate().toString()));
        } else {
        	answer.setDate(6, null);
        }
		
        return answer;
	}
	
	protected PreparedStatement getPreparedUpdate(final Connection conn, Clinic clinic) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.UPDATE_CLINIC);
		answer.setString(1, clinic.getTrialId());
		answer.setString(2, clinic.getName());
		answer.setString(3, clinic.getId());
		return answer;
	}
	
	protected PreparedStatement getPreparedUpdate(final Connection conn, Patient patient) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.UPDATE_PATIENT);
		answer.setString(1, patient.getTrialId());
		if (patient.getTrialStartDate() != null) {
			answer.setDate(2, Date.valueOf(patient.getTrialStartDate()));
        } else {
        	answer.setDate(2, null);
        }
		
		if (patient.getTrialEndDate() != null) {
			answer.setDate(3, Date.valueOf(patient.getTrialEndDate()));
        } else {
        	answer.setDate(3, null);
        }
		answer.setString(4, patient.getId());
		return answer;
	}
	
	protected PreparedStatement getPreparedUpdate(final Connection conn, Reading reading) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.UPDATE_READING);
		answer.setString(1, reading.getPatientId());
		answer.setString(2, reading.getClinicId());
		answer.setString(3, ReadingFactory.getReadingType(reading));
		answer.setString(4, reading.getValue() != null ? reading.getValue().toString() : null);
		
		if (reading.getDate() != null) {
			answer.setDate(5, Date.valueOf(reading.getDate().toString()));
        } else {
        	answer.setDate(5, null);
        }
		
		answer.setString(6, reading.getId());
		
        return answer;
	}
	
	protected PreparedStatement getPreparedDelete(final Connection conn, Clinic clinic) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.DELETE_CLINIC);
		answer.setString(1, clinic.getId());
		answer.setString(2, clinic.getTrialId());
		return answer;
	}
	
	protected PreparedStatement getPreparedDelete(final Connection conn, Patient patient) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.DELETE_PATIENT);
		answer.setString(1, patient.getId());
		answer.setString(2, patient.getTrialId());
		return answer;
	}
	
	protected PreparedStatement getPreparedDelete(final Connection conn, Reading reading) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.DELETE_READING);
		answer.setString(1, reading.getId());
		answer.setString(2, reading.getPatientId());
		answer.setString(3, reading.getClinicId());
		return answer;
	}
	
	protected Trial loadTrial(ResultSet rs) throws SQLException {
		Trial answer = new Trial();
		
		answer.setId(rs.getString(ID));
		
		if (rs.getDate(START_DATE) != null) {
			answer.setStartDate(rs.getDate(START_DATE).toLocalDate());
        }
		if (rs.getDate(END_DATE) != null) {
			answer.setEndDate(rs.getDate(END_DATE).toLocalDate());
        }
		
		return answer;
	}
	
	protected Clinic loadClinic(ResultSet rs) throws SQLException {
		Clinic answer = new Clinic();
		
		answer.setId(rs.getString(ID));
		answer.setName(rs.getString(NAME));
		answer.setTrialId(rs.getString(TRIAL_ID));
		
		return answer;
	}
	
	protected Patient loadPatient(ResultSet rs) throws SQLException {
		Patient answer = new Patient();
		
		answer.setId(rs.getString(ID));
		answer.setTrialId(rs.getString(TRIAL_ID));
		
		if (rs.getDate(START_DATE) != null) {
			answer.setTrialStartDate(rs.getDate(START_DATE).toLocalDate());
        }
		if (rs.getDate(END_DATE) != null) {
			answer.setTrialEndDate(rs.getDate(END_DATE).toLocalDate());
        }
		
		return answer;
	}
	
	protected Reading loadReading(ResultSet rs) throws SQLException {
		String type = rs.getString(TYPE);
		Reading answer = ReadingFactory.getReading(type);
		
		answer.setId(rs.getString(ID));
		answer.setPatientId(rs.getString(PATIENT_ID));
		answer.setClinicId(rs.getString(CLINIC_ID));
		
		if (rs.getDate(DATE) != null) {
			answer.setDate(LocalDateTime.ofInstant(rs.getDate(DATE).toInstant(), ZoneId.systemDefault()));
        }
		answer.setValue(rs.getString(VALUE));
		
		return answer;
	}
	
	protected Trial get(Trial trial) throws TrialCatalogException {
		validateParam(trial);
		Trial answer = null;
		
		try(Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, trial);
				ResultSet rs = pstmt.executeQuery();){
			
			if (rs.next()) {
				answer = loadTrial(rs);
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		
		return answer;
	}
	
	///////////////////////// Begin Interface //////////////////////////////////////
	@Override
	public boolean init(Trial trial) throws TrialCatalogException {
		validateParam(trial);
		
		boolean answer = true;
		
		String catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		if (!Files.exists(Paths.get(catalogStoragePath))) {
			if (!Paths.get(catalogStoragePath).toFile().mkdirs()) {
				answer = false;
			}
		}
		if (answer) {
				answer = createTrialCatalog(trial, catalogStoragePath);
			if (answer) {
				this.trial =  get(trial);
				answer = this.trial != null;
			}
		}
		return answer;
	}

	@Override
	public boolean isInit() {
		return isValidTrial(trial);
	}

	@Override
	public boolean exists(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, clinic);
				ResultSet rs = pstmt.executeQuery()){
			if (rs.next()) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		
		return answer;
	}
	
	@Override
	public boolean insert(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedInsert(conn, clinic);){
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		
		return answer;
	}

	@Override
	public Clinic get(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		Clinic answer = null;

		try(Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, clinic);
				ResultSet rs = pstmt.executeQuery();){
			
			if (rs.next()) {
				answer = loadClinic(rs);
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		
		return answer;
	}

	@Override
	public boolean update(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		boolean answer = false;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedUpdate(conn, clinic);){
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		
		return answer;
	}

	@Override
	public boolean remove(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedDelete(conn, clinic);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public boolean exists(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, patient);
				ResultSet rs = pstmt.executeQuery()){
			if (rs.next()) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		
		return answer;
	}
	
	@Override
	public boolean insert(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedInsert(conn, patient);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public Patient get(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		Patient answer = null;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, patient);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				answer = loadPatient(rs);
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public boolean update(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedUpdate(conn, patient);){
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public boolean remove(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedDelete(conn, patient);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public boolean exists(Reading reading) throws TrialCatalogException {
		validateIsInit();
		validateParam(reading);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, reading);
				ResultSet rs = pstmt.executeQuery()){
			if (rs.next()) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
		
		return answer;
	}
	
	@Override
	public boolean insert(Reading reading) throws TrialCatalogException {
		validateIsInit();
		validateParam(reading);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedInsert(conn, reading);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public Reading get(Reading reading) throws TrialCatalogException {
		validateIsInit();
		validateParam(reading);
		Reading answer = null;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, reading);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				answer = loadReading(rs);
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public boolean update(Reading reading) throws TrialCatalogException {
		validateIsInit();
		validateParam(reading);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedUpdate(conn, reading);){
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public boolean remove(Reading reading) throws TrialCatalogException {
		validateIsInit();
		validateParam(reading);
		boolean answer = false;
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedDelete(conn, reading);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	public List<String> getAllTrialCatalogNamesInDirectory() {
		List<String> catalogs = new ArrayList<>();
		String catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		for (File file : Paths.get(catalogStoragePath).toFile().listFiles()) {
			String catalogName = file.getName().replaceAll(ClinicalTrialCatalogUtilIty.CATALOG_EXTENSION, "");
			catalogs.add(catalogName);
		}
		return catalogs;
	}

	@Override
	public List<Clinic> getClinics() throws TrialCatalogException {
		validateIsInit();
		List<Clinic> answer = new LinkedList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllClinics(conn);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadClinic(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public List<Patient> getPatients() throws TrialCatalogException {
		validateIsInit();
		List<Patient> answer = new LinkedList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllPatients(conn);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadPatient(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public List<Patient> getActivePatients() throws TrialCatalogException {
		validateIsInit();
		List<Patient> answer = new LinkedList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllActivePatients(conn);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadPatient(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public List<Patient> getInactivePatients() throws TrialCatalogException {
		validateIsInit();
		List<Patient> answer = new LinkedList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllInactivePatients(conn);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadPatient(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public List<Reading> getReadings() throws TrialCatalogException {
		validateIsInit();
		List<Reading> answer = new LinkedList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllReadings(conn);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadReading(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public List<Reading> getReadings(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		
		List<Reading> answer = new LinkedList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllReadings(conn, patient);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadReading(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	@Override
	public List<Reading> getReadings(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		
		List<Reading> answer = new LinkedList<>();
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllReadings(conn, clinic);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadReading(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	//////////////////////// End of Interface /////////////////////////////////////

}

