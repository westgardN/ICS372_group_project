/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The ClinicalTrialCatalog serves as the central database used to store all of
 * the information for a trial. This class provides CRUD data access to
 * Clinic(s), Reading(s), and Patient(s). That is you are able to insert, get,
 * update, and delete a Clinic, Reading, or Patient from the catalog. At
 * instantiation of this class, the <code>init()</code> method will check to see
 * if the catalog storage directory exists, if not it will be created and a new
 * default trial catalog will be created and initialized. If the catalog storage
 * directory does already exist, but the directory is void of any trial catalogs
 * or a default trial catalog, a new default trial catalog will be created and
 * initialized. Otherwise it will be assumed the directory already exists and
 * there is a default trial catalog present in the directory. The catalog
 * storage paths for Windows, Linux, and OS X are as follows:
 * 
 * <ul>
 * <li>Android: package name
 * <li>MAC: Users\*user*\Library\That Group\catalogs
 * <li>Linux: $HOME\.local\share\That Group\catalogs
 * <li>Windows: Users\*user*\AppData\Roaming\catalogs
 * </ul>
 * 
 * @author That Group
 *
 */
public class ClinicalTrialCatalog implements TrialCatalog {
	protected static final String ANDROID_CATALOG_EXTENSION = ".db3";
	protected static final String CATALOG_EXTENSION = ".db";
	protected static final String PRAGMA_NAME_COLUMN = "name";
	protected static final String MIGRATION_00_COLUMN_NAME = "status_id";
	protected static final String CONNECTOR_PREFIX = "jdbc:sqlite:";
	protected static final String ANDROID_CONNECTOR_PREFIX = "jdbc:sqldroid:";	
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
	protected static final String STATUS_ID = "status_id";
	protected static final String DISPLAY_STATUS = "display_status";
	protected static final String DEFAULT_PATIENT_STATUS_ID  = "INACTIVE";

	protected final Trial trial;
	protected final String storagePath;
	protected final String catalogName;
	protected final String catalogExt;
	protected final String connectorPrefix;
	protected boolean init;
	
	protected static void registerSQLDroidDriver() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Failed to register SQLDroidDriver");
        }
	}
	
	public ClinicalTrialCatalog(Trial trial) {
		this(trial, ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath());
	}
	
	public ClinicalTrialCatalog(Trial trial, String storagePath) {
		validateParam(trial);
		validateParam(storagePath);
		
		this.trial = trial;
		this.storagePath = storagePath;
		this.catalogName = trial.getId();
		if (ClinicalTrialCatalogUtilIty.isAndroid()) {
			this.catalogExt = ANDROID_CATALOG_EXTENSION;
			this.connectorPrefix = ANDROID_CONNECTOR_PREFIX;
			registerSQLDroidDriver();
			
		} else {
			this.catalogExt = CATALOG_EXTENSION;
			this.connectorPrefix = CONNECTOR_PREFIX;
		}
		this.init = false;
	}
	
	/**
	 * Returns the name of the currently selected/connected trial catalog.
	 * 
	 * @return the currentCatalogName the name of the currently selected/connected
	 *         trial catalog.
	 */
	public String getName() {
		return catalogName;
	}
	
	/**
	 * Returns the storage path to this catalog.
	 * 
	 * @return the storage path to this catalog.
	 */
	public String getStoragePath() {
		return storagePath;
	}
	
	/**
	 * Gets a connection to a trial catalog for a specific trial depending on the
	 * name of the trial passed to the method.
	 * 
	 * @param trialName
	 *            the name of the trial whose catalog is to be connected to.
	 * @return a new connection to the specific trial catalog.
	 * @throws TrialCatalogException
	 */
	public Connection getConnection() throws TrialCatalogException {
		Connection answer = null;
		String connectionUrl = Strings.EMPTY;
		
		connectionUrl = connectorPrefix + storagePath + catalogName.concat(catalogExt);
		
		try {
			answer = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		
		return answer;
	}

	public boolean createTrialCatalog() throws TrialCatalogException {
		boolean answer = false;
		String filePath = storagePath + catalogName.concat(catalogExt);

		if (!catalogExists()) {
			if (writeAndInitializeCatalogFile(Paths.get(filePath).toFile())) {
				answer = true;
			}
		} else {
			answer = true;
		}

		return answer;
	}

	public boolean catalogExists() {
		boolean answer = false;

		String filePath = storagePath + catalogName.concat(catalogExt);

		if (Files.exists(Paths.get(filePath))) {
			answer = true;
		}
		
		return answer;
	}
	
	public boolean removeCatalog() {
		boolean answer = false;

		String filePath = storagePath + catalogName.concat(catalogExt);

		Path path = Paths.get(filePath);
		
		if (Files.exists(path) && !Files.isDirectory(path)) {
			try {
				Files.delete(path);
				answer = true;
			} catch (IOException e) {
			}			
		}
		
		return answer;
	}
	
	protected void validateParam(Trial trial) {
		if (!isValidTrial(trial)) {
			throw new IllegalArgumentException(Strings.ERR_CATALOG_TRIAL_INVALID);
		}
	}

	protected void validateParam(Clinic clinic) {
		if (clinic == null || clinic.getId() == null || clinic.getId().trim().isEmpty()) {
			throw new IllegalArgumentException(Strings.ERR_CATALOG_CLINIC_INVALID);
		}
	}

	protected void validateParam(PatientStatus patientStatus) {
		if (patientStatus == null || patientStatus.getId() == null || patientStatus.getId().trim().isEmpty()) {
			throw new IllegalArgumentException(Strings.ERR_CATALOG_PATIENT_STATUS_INVALID);
		}
	}

	protected void validateParam(Patient patient) {
		if (patient == null || patient.getId() == null || patient.getId().trim().isEmpty()) {
			throw new IllegalArgumentException(Strings.ERR_CATALOG_PATIENT_INVALID);
		}
	}

	protected void validateParam(Reading reading) {
		if (reading == null || reading.getId() == null || reading.getId().trim().isEmpty()) {
			throw new IllegalArgumentException(Strings.ERR_CATALOG_READING_INVALID);
		}
	}

	protected void validateParam(String string) {
		if (string == null || string.trim().isEmpty()) {
			throw new IllegalArgumentException(Strings.ERR_CATALOG_STORAGE_PATH);
		}
	}

	protected void validateIsInit() throws TrialCatalogException {
		if (!isValidTrial(trial)) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_NO_ACTIVE_TRIAL);
		}

		if (!catalogExists()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_HAS_BEEN_DELETED);
		}
	}

	protected boolean isValidTrial(Trial trial) {
		return trial != null && trial.getId() != null && !trial.getId().trim().isEmpty();
	}

	protected String getActiveId() {
		return trial != null ? trial.getId() : null;
	}

	protected LocalDate getStartDate() {
		return trial != null ? trial.getStartDate() : null;
	}

	protected LocalDate getEndDate() {
		return trial != null ? trial.getEndDate() : null;
	}

	protected PreparedStatement getPreparedSelect(final Connection conn, Trial trial) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_TRIAL);
		answer.setString(1, getActiveId());
		return answer;
	}

	protected PreparedStatement getPreparedSelect(final Connection conn, String id, String sql) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(sql);
		answer.setString(1, id);
		answer.setString(2, getActiveId());
		return answer;
	}

	protected PreparedStatement getPreparedSelect(final Connection conn, PatientStatus patientStatus) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_PATIENT_STATUS);
		answer.setString(1, patientStatus.getId());
		return answer;
	}

	protected PreparedStatement getPreparedSelect(final Connection conn, Reading reading) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_READING);
		answer.setString(1, reading.getId());
		return answer;
	}

	protected PreparedStatement getPreparedSelectAll(final Connection conn, String sql) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(sql);
		answer.setString(1, getActiveId());
		return answer;
	}

	protected PreparedStatement getPreparedSelectAllPatientStatuses(final Connection conn, String sql) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(sql);
		return answer;
	}
	
	protected PreparedStatement getPreparedSelectAllReadings(final Connection conn) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_ALL_READINGS);
		return answer;
	}

	protected PreparedStatement getPreparedSelectAllReadings(final Connection conn, String id, String sql)
			throws SQLException {
		PreparedStatement answer = conn.prepareStatement(sql);
		answer.setString(1, id);
		return answer;
	}

	protected PreparedStatement getPreparedInsert(final Connection conn, Trial trial) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.INSERT_TRIAL);
		answer.setString(1, trial.getId());
		if (trial.getStartDate() != null) {
			long epochMillis = trial.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
			answer.setDate(2, new java.sql.Date(epochMillis));
		} else {
			answer.setDate(2, null);
		}
		if (trial.getEndDate() != null) {
			long epochMillis = trial.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
			answer.setDate(3, new java.sql.Date(epochMillis));
		} else {
			answer.setDate(3, null);
		}
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
			long epochDays = patient.getTrialStartDate().toEpochDay();
			answer.setDate(2, new java.sql.Date(epochDays));
		} else {
			answer.setDate(2, null);
		}

		if (patient.getTrialEndDate() != null) {
			long epochDays = patient.getTrialEndDate().toEpochDay();
			answer.setDate(3, new java.sql.Date(epochDays));
		} else {
			answer.setDate(3, null);
		}

		answer.setString(4, getActiveId());
		answer.setString(5, patient.getStatusId());

		return answer;
	}

	protected PreparedStatement getPreparedInsert(final Connection conn, PatientStatus patientStatus) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.INSERT_PATIENT_STATUS);
		answer.setString(1, patientStatus.getId());

		answer.setString(2, patientStatus.getDisplayStatus());

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
			long epochMillis = reading.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			
			answer.setTimestamp(6, new Timestamp(epochMillis));
		} else {
			answer.setTimestamp(6, null);
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
			long epochDays = patient.getTrialStartDate().toEpochDay();
			answer.setDate(2, new java.sql.Date(epochDays));
		} else {
			answer.setDate(2, null);
		}

		if (patient.getTrialEndDate() != null) {
			long epochDays = patient.getTrialEndDate().toEpochDay();
			answer.setDate(3, new java.sql.Date(epochDays));
		} else {
			answer.setDate(3, null);
		}
		answer.setString(4, patient.getStatusId());
		
		answer.setString(5, patient.getId());
		return answer;
	}

	protected PreparedStatement getPreparedUpdate(final Connection conn, PatientStatus patientStatus) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.UPDATE_PATIENT_STATUS);

		answer.setString(1, patientStatus.getDisplayStatus());
		answer.setString(2, patientStatus.getId());
		
		return answer;
	}

	protected PreparedStatement getPreparedUpdate(final Connection conn, Reading reading) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.UPDATE_READING);
		answer.setString(1, reading.getPatientId());
		answer.setString(2, reading.getClinicId());
		answer.setString(3, ReadingFactory.getReadingType(reading));
		answer.setString(4, reading.getValue() != null ? reading.getValue().toString() : null);

		if (reading.getDate() != null) {
			long epochMillis = reading.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			
			answer.setTimestamp(5, new Timestamp(epochMillis));
		} else {
			answer.setTimestamp(5, null);
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

	protected PreparedStatement getPreparedDelete(final Connection conn, PatientStatus patientStatus) throws SQLException {
		PreparedStatement answer = conn.prepareStatement(ClinicalStatement.DELETE_PATIENT_STATUS);
		answer.setString(1, patientStatus.getId());
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
		answer.setStatusId(rs.getString(STATUS_ID));

		return answer;
	}

	protected PatientStatus loadPatientStatus(ResultSet rs) throws SQLException {
		PatientStatus answer = new PatientStatus();

		answer.setId(rs.getString(ID));
		answer.setDisplayStatus(rs.getString(DISPLAY_STATUS));

		return answer;
	}

	protected Reading loadReading(ResultSet rs) throws SQLException {
		String type = rs.getString(TYPE);
		Reading answer = ReadingFactory.getReading(type);

		answer.setId(rs.getString(ID));
		answer.setPatientId(rs.getString(PATIENT_ID));
		answer.setClinicId(rs.getString(CLINIC_ID));

		if (rs.getDate(DATE) != null) {
			Timestamp date = rs.getTimestamp(DATE);
			LocalDateTime ld = null;
			if (date != null) {
				ld = date.toLocalDateTime();
			}
			if (ld != null) {
				answer.setDate(ld);
			}
		}
		answer.setValue(rs.getString(VALUE));

		return answer;
	}

	protected Trial get(Trial trial) throws TrialCatalogException {
		validateParam(trial);
		Trial answer = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, trial);
				ResultSet rs = pstmt.executeQuery();) {

			if (rs.next()) {
				answer = loadTrial(rs);
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}

		return answer;
	}

	protected boolean insert(Trial trial) throws TrialCatalogException {
		validateIsInit();
		validateParam(trial);
		boolean answer = false;

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedInsert(conn, trial);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}

		return answer;
	}

	@Override
	public boolean isInit() {
		return isValidTrial(trial) && init && catalogExists();
	}

	@Override
	public boolean exists(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		boolean answer = false;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, clinic.getId(), ClinicalStatement.GET_CLINIC);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}

		return answer;
	}

	@Override
	public boolean exists(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		boolean answer = false;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, patient.getId(), ClinicalStatement.GET_PATIENT);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}

		return answer;
	}

	@Override
	public boolean exists(PatientStatus patientStatus) throws TrialCatalogException {
		validateIsInit();
		validateParam(patientStatus);
		boolean answer = false;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, patientStatus);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				answer = true;
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
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
				ResultSet rs = pstmt.executeQuery()) {
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

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedInsert(conn, clinic);) {
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

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, clinic.getId(), ClinicalStatement.GET_CLINIC);
				ResultSet rs = pstmt.executeQuery();) {

			if (rs.next()) {
				answer = loadClinic(rs);
			}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}

		return answer;
	}

	@Override
	public Clinic getDefaultClinic() throws TrialCatalogException {
		Clinic clinic = new Clinic(Clinic.DEFAULT_ID, getActiveId(), Clinic.DEFAULT_ID);

		if (!exists(clinic)) {
			insert(clinic);
		}

		return get(clinic);
	}

	@Override
	public boolean update(Clinic clinic) throws TrialCatalogException {
		validateIsInit();
		validateParam(clinic);
		boolean answer = false;

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedUpdate(conn, clinic);) {
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

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedDelete(conn, clinic);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
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
				PreparedStatement pstmt = getPreparedSelect(conn, patient.getId(), ClinicalStatement.GET_PATIENT);
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
	public Patient getDefaultPatient() throws TrialCatalogException {
		Patient patient = new Patient(Patient.DEFAULT_ID, getActiveId(), getStartDate(), getEndDate());

		if (!exists(patient)) {
			insert(patient);
		}

		Patient pt = get(patient);
		boolean update = false;

		if (pt.getTrialEndDate() != null) {
			pt.setTrialEndDate(null);
			update = true;
		}

		if (pt.getTrialStartDate().isAfter(getStartDate())) {
			pt.setTrialStartDate(getStartDate());
			update = true;
		}

		if (update) {
			update(pt);
		}

		return get(patient);
	}

	@Override
	public boolean update(Patient patient) throws TrialCatalogException {
		validateIsInit();
		validateParam(patient);
		boolean answer = false;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedUpdate(conn, patient);) {
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

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedDelete(conn, patient);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

		return answer;
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalog#insert(edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus)
	 */
	@Override
	public boolean insert(PatientStatus patientStatus) throws TrialCatalogException {
		validateIsInit();
		validateParam(patientStatus);
		boolean answer = false;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedInsert(conn, patientStatus);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

		return answer;
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalog#get(edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus)
	 */
	@Override
	public PatientStatus get(PatientStatus patientStatus) throws TrialCatalogException {
		validateIsInit();
		validateParam(patientStatus);
		PatientStatus answer = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelect(conn, patientStatus);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				answer = loadPatientStatus(rs);
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

		return answer;
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalog#getDefaultPatientStatusId()
	 */
	@Override
	public String getDefaultPatientStatusId() throws TrialCatalogException {
		return DEFAULT_PATIENT_STATUS_ID;
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalog#update(edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus)
	 */
	@Override
	public boolean update(PatientStatus patientStatus) throws TrialCatalogException {
		validateIsInit();
		validateParam(patientStatus);
		boolean answer = false;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedUpdate(conn, patientStatus);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

		return answer;
	}

	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalog#remove(edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus)
	 */
	@Override
	public boolean remove(PatientStatus patientStatus) throws TrialCatalogException {
		validateIsInit();
		validateParam(patientStatus);
		boolean answer = false;

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedDelete(conn, patientStatus);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

		return answer;
	}

	@Override
	public boolean insert(Reading reading) throws TrialCatalogException {
		validateIsInit();
		validateParam(reading);
		boolean answer = false;

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedInsert(conn, reading);) {
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

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedUpdate(conn, reading);) {
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

		try (Connection conn = getConnection(); PreparedStatement pstmt = getPreparedDelete(conn, reading);) {
			if (pstmt.executeUpdate() == 1) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

		return answer;
	}

	@Override
	public List<Clinic> getClinics() throws TrialCatalogException {
		validateIsInit();
		List<Clinic> answer = new LinkedList<>();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAll(conn, ClinicalStatement.GET_ALL_CLINICS);
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
				PreparedStatement pstmt = getPreparedSelectAll(conn, ClinicalStatement.GET_ALL_PATIENTS);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadPatient(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

		return answer;
	}

	
	/* (non-Javadoc)
	 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalog#getAllPatientStatus()
	 */
	@Override
	public List<PatientStatus> getPatientStatuses() throws TrialCatalogException {
		validateIsInit();
		List<PatientStatus> answer = new LinkedList<>();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = getPreparedSelectAllPatientStatuses(conn, ClinicalStatement.GET_ALL_PATIENT_STATUSES);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadPatientStatus(rs));
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
				PreparedStatement pstmt = getPreparedSelectAll(conn, ClinicalStatement.GET_ALL_ACTIVE_PATIENTS);
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
				PreparedStatement pstmt = getPreparedSelectAll(conn, ClinicalStatement.GET_ALL_INACTIVE_PATIENTS);
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
				PreparedStatement pstmt = getPreparedSelectAllReadings(conn, patient.getId(),
						ClinicalStatement.GET_PATIENT_READINGS);
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
				PreparedStatement pstmt = getPreparedSelectAllReadings(conn, clinic.getId(),
						ClinicalStatement.GET_CLINIC_READINGS);
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
	
	/**
	 * @param catalogFile
	 * @param trialName
	 * @param initializationStatements
	 * @param answer
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private boolean executeSQLStatements(String[] initializationStatements
			) throws TrialCatalogException {
		boolean answer = false;
		answer = true;
		for (String statement : initializationStatements) {
			try (Connection conn = getConnection(); Statement stmt = conn.createStatement();) {
				stmt.execute(statement);
			} catch (SQLException e) {
				answer = false;
				throw new TrialCatalogException(e.getMessage(), e);
			}
		}
		return answer;
	}
	
	public boolean firstMigrationCatalogFile()  throws TrialCatalogException {
		boolean answer = false;
		String[] initializationStatements = new String[] { ClinicalStatement.MIGRATION_PROJECT_3_01,
				ClinicalStatement.MIGRATION_PROJECT_3_02, ClinicalStatement.MIGRATION_PROJECT_3_03,
				ClinicalStatement.MIGRATION_PROJECT_3_04, ClinicalStatement.MIGRATION_PROJECT_3_05,
				ClinicalStatement.MIGRATION_PROJECT_3_06, ClinicalStatement.MIGRATION_PROJECT_3_07,
				ClinicalStatement.MIGRATION_PROJECT_3_08, ClinicalStatement.MIGRATION_PROJECT_3_09,
				ClinicalStatement.MIGRATION_PROJECT_3_10};
		answer = executeSQLStatements(initializationStatements);
		
		return answer;
	}
	
	/**
	 * Creates/writes a new trial catalog file and initializes the catalog database
	 * tables.
	 * 
	 * @param catalogFile
	 *            the trial catalog file to be created.
	 * @param trialName
	 *            the name of the trial/catalog.
	 * @return true if the trial catalog was written and initialized, else false.
	 * @throws SQLException
	 * @throws IOException
	 */
	protected boolean writeAndInitializeCatalogFile(File catalogFile) throws TrialCatalogException {
		boolean answer = false;
		String[] initializationStatements = new String[] { ClinicalStatement.CREATE_TABLE_TRIAL,
				ClinicalStatement.CREATE_TABLE_CLINICS, ClinicalStatement.CREATE_TABLE_PATIENTS,
				ClinicalStatement.CREATE_TABLE_READINGS};
		try {
			if (catalogFile.createNewFile() ) {
				answer = executeSQLStatements(initializationStatements);
			}
		} catch (IOException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return answer;
	}

	protected boolean databaseNeedsUpgrade() throws TrialCatalogException {
		boolean answer = false;
		
		answer = !patientsTableConstainsStatusField();
		
		return answer;

	}
	
	protected boolean patientsTableConstainsStatusField() throws TrialCatalogException {
		boolean answer = false;
		try (Connection conn = getConnection(); Statement stmt = conn.createStatement();) {
			boolean results = stmt.execute(ClinicalStatement.MIGRATION_PROJECT_3_00);
			
			if (results) {
				ResultSet rSet = stmt.getResultSet();
				
				while(rSet.next()) {
					int index = rSet.findColumn(PRAGMA_NAME_COLUMN);
					
					String name = rSet.getString(index);
					
					if (name != null && name.toLowerCase().equals(MIGRATION_00_COLUMN_NAME)) {
						answer = true;
						break;
					}
				}
				
				if (!rSet.isClosed()) {
					rSet.close();
				}
			}
		} catch (SQLException e) {
			answer = false;
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return answer;
	}

	///////////////////////// Begin Interface //////////////////////////////////////
	@Override
	public boolean init() throws TrialCatalogException {
		validateParam(trial);

		boolean answer = true;

		// Create directory structure?
		if (!Files.exists(Paths.get(storagePath))) {
			if (!Paths.get(storagePath).toFile().mkdirs()) {
				answer = false;
			}
		} 
		
		// Directory structure in place?
		if (answer) {			
			// Create the database?
			if (!catalogExists()) {
				answer = createTrialCatalog();
			} 
			
			if (answer && databaseNeedsUpgrade()) {
				answer = firstMigrationCatalogFile();
			}
			
			if (answer) {
				Trial temp = get(trial);

				if (temp == null) {
					answer = insert(trial);
				} else {
					trial.setId(temp.getId());
					trial.setStartDate(temp.getStartDate());
					trial.setEndDate(temp.getEndDate());
				}
			}			
		}
		
		init = answer;
		
		return init;
	}

}
