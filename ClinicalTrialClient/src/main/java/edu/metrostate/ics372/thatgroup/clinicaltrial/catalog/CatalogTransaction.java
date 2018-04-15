package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements.InsertStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements.RemoveStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements.SelectStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.statements.UpdateStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;

public class CatalogTransaction {
	private static final String END_DATE = "end_date";
	private static final String START_DATE = "start_date";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String TRIAL_ID = "trial_id";
	private static final String TYPE = "type";
	private static final String PATIENT_ID = "patient_id";
	private static final String CLINIC_ID = "clinic_id";
	private static final String DATE = "date";
	private static final String VALUE = "value";
	private String activeId = "";
	
	public CatalogTransaction(String activeId) {
		this.activeId = activeId;
	}
	
	public boolean isValidated(Object bean) throws TrialCatalogException {
        return BeanValidator.isValidated(bean);
    }

    public boolean exists(Object bean) throws TrialCatalogException {
        boolean answer = false;
        
        if (isValidated(bean)) {
    		try (final Connection conn = ClinicalTrialCatalogUtilIty.getConnection(activeId);
    				PreparedStatement stmt = new SelectStatement(activeId).getStatementForBean(conn, bean);
    				ResultSet rs = stmt.executeQuery()) {
    			if (rs.next()) {
    				answer = true;
    			}
    		} catch (SQLException ex) {
    			throw new TrialCatalogException(ex.getMessage(), ex);
    		}
        }
        return answer;
    }

    public boolean insert(Object bean) throws TrialCatalogException {
        boolean answer = false;
        if (isValidated(bean)) {
            try (final Connection conn = ClinicalTrialCatalogUtilIty.getConnection(activeId);
                 PreparedStatement stmt = new InsertStatement(activeId).getStatementForBean(conn, bean);) {
                if (stmt.executeUpdate() == 1) {
                    answer = true;
                }
            } catch (SQLException ex) {
    			throw new TrialCatalogException(ex.getMessage(), ex);
    		}
        }
        return answer;
    }

    public Object get(Object bean) throws TrialCatalogException {
    	ResultSet rSet = null;
    	Object answer = null;
         if (isValidated(bean)) {
             try (final Connection conn = ClinicalTrialCatalogUtilIty.getConnection(activeId);
                  PreparedStatement stmt = new SelectStatement(activeId).getStatementForBean(conn, bean);
            		ResultSet tRs = stmt.executeQuery();) {
            	 rSet = tRs;
             } catch (SQLException ex) {
     			throw new TrialCatalogException(ex.getMessage(), ex);
     		}
         }
         try {
        	 if (bean instanceof Trial) {
            	 answer = loadTrial(rSet);
    		} else if (bean instanceof Trial) {
    			answer = loadClinic(rSet);
    		} else if (bean instanceof Trial) {
    			answer = loadPatient(rSet);
    		} else if (bean instanceof Trial) {
    			answer = loadReading(rSet);
    		}
		} catch (SQLException ex) {
			throw new TrialCatalogException(ex.getMessage(), ex);
		}
         return answer;
    };

    public boolean update(Object bean) throws TrialCatalogException {
    	boolean answer = false;
        if (isValidated(bean)) {
            try (final Connection conn = ClinicalTrialCatalogUtilIty.getConnection(activeId);
                 PreparedStatement stmt = new UpdateStatement().getStatementForBean(conn, bean);) {
                if (stmt.executeUpdate() == 1) {
                    answer = true;
                }
            } catch (SQLException ex) {
    			throw new TrialCatalogException(ex.getMessage(), ex);
    		}
        }
        return answer;
    }

    public boolean remove(Object bean) throws TrialCatalogException {
    	boolean answer = false;
        if (isValidated(bean)) {
            try (final Connection conn = ClinicalTrialCatalogUtilIty.getConnection(activeId);
                 PreparedStatement stmt = new RemoveStatement().getStatementForBean(conn, bean);) {
                if (stmt.executeUpdate() == 1) {
                    answer = true;
                }
            } catch (SQLException ex) {
    			throw new TrialCatalogException(ex.getMessage(), ex);
    		}
        }
        return answer;
    }
    
    public List<Clinic> getClinics() throws TrialCatalogException {
		List<Clinic> answer = new LinkedList<>();
		try (final Connection conn = ClinicalTrialCatalogUtilIty.getConnection(activeId);
				PreparedStatement pstmt = new;
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				answer.add(loadClinic(rs));
			}
		} catch (SQLException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}

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

    private static class BeanValidator {
        private static boolean isValidated(Object bean) throws TrialCatalogException {
            boolean answer;
            if (bean != null) {
                if (bean instanceof Trial) {
                    answer = validateParam(((Trial) bean));
                } else if (bean instanceof Clinic) {
                    answer = validateParam(((Clinic) bean));
                } else if (bean instanceof Patient) {
                    answer = validateParam(((Patient) bean));
                } else if(bean instanceof Reading) {
                    answer = validateParam(((Reading) bean));
                } else {
                    throw new TrialCatalogException("The transaction template bean must be a Trial, Clinic, Patient or Reading object");
                }
            } else {
                throw new TrialCatalogException("The transaction template bean cannot be null.");
            }
            return answer;
        }
        
        private static boolean isValidTrial(Trial trial) {
    		return trial != null && trial.getId() != null && !trial.getId().trim().isEmpty();
    	}

        private static boolean validateParam(Trial trial) throws TrialCatalogException {
        	boolean answer = false;
    		if (!isValidTrial(trial)) {
    			throw new TrialCatalogException(Strings.ERR_CATALOG_TRIAL_INVALID);
    		} else {
    			answer = true;
    		}
    		return answer;
    	}

        private static boolean validateParam(Clinic clinic) throws TrialCatalogException {
    		boolean answer = false;
    		if (clinic == null || clinic.getId() == null || clinic.getId().trim().isEmpty()) {
    			throw new TrialCatalogException(Strings.ERR_CATALOG_CLINIC_INVALID);
    		} else {
    			answer = true;
    		}
    		return answer;
    	}

        private static boolean validateParam(Patient patient) throws TrialCatalogException {
    		boolean answer = false;
    		if (patient == null || patient.getId() == null || patient.getId().trim().isEmpty()) {
    			throw new TrialCatalogException(Strings.ERR_CATALOG_PATIENT_INVALID);
    		} else {
    			answer = true;
    		}
    		return answer;
    	}

        private static boolean validateParam(Reading reading) throws TrialCatalogException {
    		boolean answer = false;
    		if (reading == null || reading.getId() == null || reading.getId().trim().isEmpty()) {
    			throw new TrialCatalogException(Strings.ERR_CATALOG_READING_INVALID);
    		} else {
    			answer = true;
    		}
    		return answer;
    	}
    }
}
