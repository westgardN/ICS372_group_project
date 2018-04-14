/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * @author That Group
 *
 */
public abstract class AbstractClinicalTrialCatalog implements TrialCatalog {
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
	protected Trial trial;

	abstract protected Connection getConnection() throws SQLException;
	
	abstract protected boolean databaseExists(Trial trial);
	
	abstract protected boolean createTrialCatalog(Trial trial) throws TrialCatalogException;
	
	protected void validateParam(Trial trial) throws TrialCatalogException {
		if (!isValidTrial(trial)) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_TRIAL_INVALID);
		}
	}

	protected void validateParam(Clinic clinic) throws TrialCatalogException {
		if (clinic == null || clinic.getId() == null || clinic.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_CLINIC_INVALID);
		}
	}

	protected void validateParam(PatientStatus patientStatus) throws TrialCatalogException {
		if (patientStatus == null || patientStatus.getId() == null || patientStatus.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_PATIENT_STATUS_INVALID);
		}
	}

	protected void validateParam(Patient patient) throws TrialCatalogException {
		if (patient == null || patient.getId() == null || patient.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_PATIENT_INVALID);
		}
	}

	protected void validateParam(Reading reading) throws TrialCatalogException {
		if (reading == null || reading.getId() == null || reading.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_READING_INVALID);
		}
	}

	protected void validateIsInit() throws TrialCatalogException {
		if (!isValidTrial(trial)) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_NO_ACTIVE_TRIAL);
		}

		if (!databaseExists(trial)) {
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
			answer.setDate(2, Date.valueOf(trial.getStartDate()));
		} else {
			answer.setDate(2, null);
		}
		if (trial.getEndDate() != null) {
			answer.setDate(3, Date.valueOf(trial.getEndDate()));
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
			answer.setTimestamp(6, Timestamp.valueOf(reading.getDate()));
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
			answer.setDate(2, Date.valueOf(patient.getTrialStartDate()));
		} else {
			answer.setDate(2, null);
		}

		if (patient.getTrialEndDate() != null) {
			answer.setDate(3, Date.valueOf(patient.getTrialEndDate()));
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
			answer.setTimestamp(5, Timestamp.valueOf(reading.getDate()));
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
		return isValidTrial(trial) && databaseExists(trial);
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
				PreparedStatement pstmt = getPreparedSelectAll(conn, ClinicalStatement.GET_ALL_PATIENT_STATUSES);
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
}
