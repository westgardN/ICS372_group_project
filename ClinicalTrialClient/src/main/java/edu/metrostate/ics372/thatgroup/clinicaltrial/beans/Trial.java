package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;

/**
 * The Trial bean is used to hold information about a clinical trial. The trial
 * contains a list of all the patients that have and that currently are, a part
 * of it.
 * 
 * The trial class also supports property change notification.
 * 
 * @author That Group
 *
 */
public class Trial implements Serializable, Cloneable {
	/**
	 * The default trial id. 
	 */
	public static final String DEFAULT_ID = "default";

	/**
	 * The id property of the trial. Used to uniquely identify a trial
	 */
	public static final String PROP_ID = "id";

	/**
	 * The start date property of the trial. Used to denote when the trial began
	 */
	public static final String PROP_START_DATE = "startDate";

	/**
	 * The end date property of the trial. Used to denote when the trial ended
	 */
	public static final String PROP_END_DATE = "endDate";

	private static final long serialVersionUID = 4128763071142480689L;
	private transient PropertyChangeSupport pcs;
	private String id;
	private LocalDate startDate;
	private LocalDate endDate;
	

	/**
	 * Initializes this trial with no id.
	 */
	public Trial() {
		this(DEFAULT_ID);
	}

	/**
	 * Initializes this trial with the specified id.
	 * 
	 * @param trialId
	 *            the id of this trial. Cannot be null.
	 */
	public Trial(String trialId) {
		this.id = trialId;
		this.startDate = LocalDate.now();
		pcs = new PropertyChangeSupport(this);
	}

	/**
	 * Trials have an id. Each trial has its own unique Id.
	 * 
	 * @return the id of this trial.
	 */
	public String getId() {
		return id;
	}

	private PropertyChangeSupport getPcs() {
		if (pcs == null) {
			pcs = new PropertyChangeSupport(this);
		}
		
		return pcs;
	}
	
	/**
	 * Sets the Id of a trial. If the id is different from what was
	 * currently set, a change notification is fired.
	 * 
	 * @param trialId
	 *            the new id for this trial. Cannot be null.
	 */
	public void setId(String trialId) {
		if (!Objects.equals(id, trialId)) {
			String oldId = this.id;
			this.id = trialId;
			getPcs().firePropertyChange(PROP_ID, oldId, this.id);
		}
	}

	/**
	 * Returns the start date of this trial, which may be null.
	 * @return the start date of this trial, which may be null.
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Sets the start date of this trial. If the start date is different from
	 * what was currently set, a change notification is fired.
	 * @param localDate the start date for this trial.
	 */
	public void setStartDate(LocalDate localDate) {
		if (!Objects.equals(this.startDate, localDate)) {
			LocalDate old = this.startDate;
			this.startDate = localDate;
			getPcs().firePropertyChange(PROP_START_DATE, old, this.startDate);
		}		
	}
	
	/**
	 * Returns the end date of this trial, which may be null.
	 * @return the end date of this trial, which may be null.
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Sets the end date of this trial. If the end date is different from
	 * what was currently set, a change notification is fired.
	 * @param localDate the end date for this trial.
	 */
	public void setEndDate(LocalDate localDate) {
		if (!Objects.equals(this.endDate, localDate)) {
			LocalDate old = this.endDate;
			this.endDate = localDate;
			getPcs().firePropertyChange(PROP_END_DATE, old, this.endDate);
		}		
	}
	
	/**
	 * Add a PropertyChangeListener to the listener list. The listener is registered
	 * for all properties. The same listener object may be added more than once, and
	 * will be called as many times as it is added. If listener is null, no
	 * exception is thrown and no action is taken.
	 * 
	 * @param listener
	 *            - The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		getPcs().addPropertyChangeListener(listener);
	}

	/**
	 * Returns true if the specified patient has started this trial
	 * 
	 * @param patient
	 *            The patient to check. Cannot be null.
	 * @return answer true if the specified patient has started this trial
	 */
	public boolean hasPatientStartedTrial(Patient patient) {
		boolean answer = false;

		if (Objects.equals(patient.getTrialId(), id) && patient.getTrialStartDate() != null && patient.getTrialEndDate() == null) {
			answer = true;
		}

		return answer;
	}

	/**
	 * Returns true if the patient is considered to be in the trial. Please note
	 * that a return value of true does not mean the patient is currently active in
	 * the trial, it just means that the patient is considered to be or have been a
	 * part of this trial.
	 * 
	 * @param patient
	 *            The patient to check. Cannot be null.
	 * @return true if the patient is considered to be in the trial;
	 *         otherwise false.
	 */
	public boolean isPatientInTrial(Patient patient) {
		boolean answer = false;

		if (Objects.equals(patient.getTrialId(), id) && patient.getTrialStartDate() != null) {
			answer = true;
		}

		return answer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.toUpperCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Trial))
			return false;
		Trial other = (Trial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else {
			if (other.id != null) {
				if (id.compareToIgnoreCase(other.id) != 0) {
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		StringBuilder builder = new StringBuilder();
		if (endDate == null) {
		builder.append("Trial ");
		builder.append(id);
		builder.append(" (");
		builder.append(startDate.format(formatter));
		}
		if (endDate != null) {
			builder.append("Trial ");
			builder.append(id);
			builder.append(" (");
			builder.append(startDate.format(formatter));
			builder.append(" - ");
			builder.append(endDate.format(formatter));
		}
		builder.append(")");
		return builder.toString();
	}

    @Override
    public Trial clone() {
    	Trial answer;
        
        try {
            answer = (Trial) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Trial.clone(): This class doe not implement Cloneable.");
        }
        
        answer.id = this.id;
        if (startDate != null) {
        		answer.startDate = LocalDate.from(startDate);
        }
        if (endDate != null) {
        		answer.endDate = LocalDate.from(endDate);
        }
        
        return answer;
    }

}
