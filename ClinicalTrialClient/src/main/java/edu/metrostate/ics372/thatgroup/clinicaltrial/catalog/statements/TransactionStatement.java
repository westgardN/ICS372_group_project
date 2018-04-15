package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;

public interface TransactionStatement {
    public PreparedStatement getStatementForBean(Connection conn, Object model) throws SQLException, TrialCatalogException;
}
