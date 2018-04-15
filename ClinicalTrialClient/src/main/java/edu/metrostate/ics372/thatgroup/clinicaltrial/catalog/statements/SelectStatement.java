package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;

public class SelectStatement implements TransactionStatement {
	private String activeId = "";
	
	public SelectStatement(String activeId) {
		this.activeId = activeId;
	}
	
    @Override
    public PreparedStatement getStatementForBean(Connection conn, Object model) throws TrialCatalogException, SQLException {
        PreparedStatement statement = null;
        if (model != null) {
            if (model instanceof Trial) {
                statement = getTrialSelectStatement(conn);
            } else if (model instanceof Clinic) {
                statement = getClinicSelectStatement(conn, (Clinic) model);
            } else if (model instanceof Patient) {
                statement = getPatientSelectStatement(conn, (Patient) model);
            } else if(model instanceof Reading) {
                statement = getReadingSelectStatement(conn, (Reading) model);
            }
        } else {
            throw new TrialCatalogException("The transaction template model cannot be null");
        }
        return statement;
    }

    private PreparedStatement getTrialSelectStatement(Connection conn) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_TRIAL);
		answer.setString(1, activeId);
		return answer;
    }
    
    private PreparedStatement getClinicSelectStatement(Connection conn, Clinic clinic) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_CLINIC);
		answer.setString(1, clinic.getId());
		answer.setString(2, activeId);
		return answer;
    }

    private PreparedStatement getPatientSelectStatement(Connection conn, Patient patient) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_PATIENT);
		answer.setString(1, patient.getId());
		return answer;
    }

    private PreparedStatement getReadingSelectStatement(Connection conn, Reading reading) throws SQLException {
    	PreparedStatement answer = conn.prepareStatement(ClinicalStatement.GET_READING);
		answer.setString(1, reading.getId());
		return answer;
    }
}
