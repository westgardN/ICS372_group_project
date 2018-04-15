package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

public class InsertStatement implements TransactionStatement {
	private String activeId = "";
	public InsertStatement(String activeId) {
		this.activeId = activeId;
	}

    @Override
    public PreparedStatement getStatementForBean(Connection conn, Object model) throws SQLException, TrialCatalogException {
        PreparedStatement statement = null;
        if (model != null) {
            if (model instanceof Trial) {
                statement = getTrialInsertStatement(conn, (Trial) model);
            } else if (model instanceof Clinic) {
                statement = getClinicInsertStatement(conn, (Clinic) model);
            } else if (model instanceof Patient) {
                statement = getPatientInsertStatement(conn, (Patient) model);
            } else if(model instanceof Reading) {
                statement = getReadingInsertStatement(conn, (Reading) model);
            }
        } else {
            throw new TrialCatalogException("The transaction template model cannot be null");
        }
        return statement;
    }

    private PreparedStatement getTrialInsertStatement(Connection conn, Trial trial) throws SQLException {
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

    private PreparedStatement getClinicInsertStatement(Connection conn, Clinic clinic) throws SQLException {
        PreparedStatement answer = conn.prepareStatement(ClinicalStatement.INSERT_CLINIC);
		answer.setString(1, clinic.getId());
		answer.setString(2, clinic.getName());
		answer.setString(3, activeId);
		return answer;
    }

    private PreparedStatement getPatientInsertStatement(Connection conn, Patient patient) throws SQLException {
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

		answer.setString(4, activeId);

		return answer;
    }

    private PreparedStatement getReadingInsertStatement(Connection conn, Reading reading) throws SQLException {
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
}
