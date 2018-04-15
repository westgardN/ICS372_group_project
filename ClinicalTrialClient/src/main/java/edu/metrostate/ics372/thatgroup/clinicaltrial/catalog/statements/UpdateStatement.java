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

public class UpdateStatement implements TransactionStatement {

    @Override
    public PreparedStatement getStatementForBean(Connection conn, Object model) throws TrialCatalogException, SQLException {
        PreparedStatement statement = null;
        if (model != null) {
            if (model instanceof Clinic) {
                statement = getClinicUpdateStatement(conn, (Clinic) model);
            } else if (model instanceof Patient) {
                statement = getPatientUpdateStatement(conn, (Patient) model);
            } else if(model instanceof Reading) {
                statement = getReadingUpdateStatement(conn, (Reading) model);
            }
        } else {
            throw new TrialCatalogException("The transaction template model cannot be null");
        }
        return statement;
    }

    private PreparedStatement getClinicUpdateStatement(Connection conn, Clinic clinic) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.UPDATE_CLINIC);
		answer.setString(1, clinic.getTrialId());
		answer.setString(2, clinic.getName());
		answer.setString(3, clinic.getId());
		return answer;
    }

    private PreparedStatement getPatientUpdateStatement(Connection conn, Patient patient) throws SQLException {
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

    private PreparedStatement getReadingUpdateStatement(Connection conn, Reading reading) throws SQLException {
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
}
