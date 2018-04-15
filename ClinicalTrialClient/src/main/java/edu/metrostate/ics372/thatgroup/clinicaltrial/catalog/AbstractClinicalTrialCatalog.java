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
	protected Trial trial;

	abstract protected Connection getConnection() throws SQLException;
	
	abstract protected boolean databaseExists(Trial trial);
	
	abstract protected boolean createTrialCatalog(Trial trial, String path) throws TrialCatalogException;

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

	@Override
	public boolean isInit() {
		return isValidTrial(trial) && databaseExists(trial);
	}

	@Override
	public boolean exists(Clinic clinic) throws TrialCatalogException {
		return existsForBean(clinic);
	}

	@Override
	public boolean exists(Patient patient) throws TrialCatalogException {
		return existsForBean(patient);
	}

	@Override
	public boolean exists(Reading reading) throws TrialCatalogException {
		return existsForBean(reading);
	}
	
	private boolean existsForBean(Object bean) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).exists(bean);
	}

	@Override
	public boolean insert(Clinic clinic) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).insert(clinic);
	}

	@Override
	public Clinic get(Clinic clinic) throws TrialCatalogException {
		return (Clinic) new CatalogTransaction(getActiveId()).get(clinic);
	}

	@Override
	public Clinic getDefaultClinic() throws TrialCatalogException {
		Clinic clinic = new Clinic(Clinic.DEFAULT_ID, getActiveId(), Clinic.DEFAULT_ID);

		CatalogTransaction transaction = new CatalogTransaction(getActiveId());
		if (!transaction.exists(clinic)) {
			transaction.insert(clinic);
		}
		return (Clinic) transaction.get(clinic);
	}

	@Override
	public boolean update(Clinic clinic) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).update(clinic);
	}

	@Override
	public boolean remove(Clinic clinic) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).remove(clinic);
	}

	@Override
	public boolean insert(Patient patient) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).insert(patient);
	}

	@Override
	public Patient get(Patient patient) throws TrialCatalogException {
		return (Patient) new CatalogTransaction(getActiveId()).get(patient);
	}

	@Override
	public Patient getDefaultPatient() throws TrialCatalogException {
		Patient patient = new Patient(Patient.DEFAULT_ID, getActiveId(), getStartDate(), getEndDate());
		CatalogTransaction transaction = new CatalogTransaction(getActiveId());
		
		if (!transaction.exists(patient)) {
			transaction.insert(patient);
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
			transaction.update(pt);
		}

		return (Patient) transaction.get(patient);
	}

	@Override
	public boolean update(Patient patient) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).update(patient);
	}

	@Override
	public boolean remove(Patient patient) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).remove(patient);
	}

	@Override
	public boolean insert(Reading reading) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).insert(reading);
	}

	@Override
	public Reading get(Reading reading) throws TrialCatalogException {
		return (Reading) new CatalogTransaction(getActiveId()).get(reading);
	}

	@Override
	public boolean update(Reading reading) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).update(reading);
	}

	@Override
	public boolean remove(Reading reading) throws TrialCatalogException {
		return new CatalogTransaction(getActiveId()).remove(reading);
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
