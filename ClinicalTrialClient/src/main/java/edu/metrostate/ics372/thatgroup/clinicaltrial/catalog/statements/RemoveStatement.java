package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;

public class RemoveStatement implements TransactionStatement {

    @Override
    public PreparedStatement getStatementForBean(Connection conn, Object model) throws SQLException, TrialCatalogException {
        PreparedStatement statement = null;
        if (model != null) {
             if (model instanceof Clinic) {
                statement = getClinicRemoveStatement(conn, (Clinic) model);
            } else if (model instanceof Patient) {
                statement = getPatientRemoveStatement(conn, (Patient) model);
            } else if(model instanceof Reading) {
                statement = getReadingRemoveStatement(conn, (Reading) model);
            }
        } else {
            throw new TrialCatalogException("The transaction template model cannot be null");
        }
        return statement;
    }

    private PreparedStatement getClinicRemoveStatement(Connection conn, Clinic clinic) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.DELETE_CLINIC);
		answer.setString(1, clinic.getId());
		answer.setString(2, clinic.getTrialId());
		return answer;
    }

    private PreparedStatement getPatientRemoveStatement(Connection conn, Patient patient) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.DELETE_PATIENT);
		answer.setString(1, patient.getId());
		answer.setString(2, patient.getTrialId());
		return answer;
    }

    private PreparedStatement getReadingRemoveStatement(Connection conn, Reading reading) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.DELETE_READING);
		answer.setString(1, reading.getId());
		answer.setString(2, reading.getPatientId());
		answer.setString(3, reading.getClinicId());
		return answer;
    }
}
