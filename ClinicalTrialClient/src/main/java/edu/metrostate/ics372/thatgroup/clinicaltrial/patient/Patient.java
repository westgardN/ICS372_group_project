package edu.metrostate.ics372.thatgroup.clinicaltrial.patient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

/**
 * 
 * @author 
 *
 */
public abstract class Patient implements Serializable {
	public static final String PROP_ID = "id";
	public static final String PROP_TRIAL_START_DATE = "trialStartDate";
	public static final String PROP_TRIAL_END_DATE = "trialEndDate";
	public static final String PROP_JOURNAL = "journal";
	public static final String PROP_JOURNAL_SIZE = "journalSize";
	private static final long serialVersionUID = 8450664877127813850L;
	protected transient final PropertyChangeSupport pcs;
	protected String id;
	protected String trialId;
	protected LocalDate trialStartDate;
	protected LocalDate trialEndDate;
	protected Set<Reading> journal;

	/**
	 * Initializes an empty patient.
	 */
	public Patient() {
		this(null, null, null, null, null);
	}

	/**
	 * Initializes a patient with the specified values.
	 * 
	 * @param id the id of this patient
	 * @param trialId the Trial this patient belongs to
	 * @param readings the readings for this patient
	 * @param start the date the patient started the trial
	 * @param end the date the patient left the trial
	 */
	public Patient(String id, String trialId, Set<Reading> readings, LocalDate start, LocalDate end) {
		super();
		this.id = id;
		this.trialId = trialId;
		if (readings != null) {
			setJournal(readings);
		} else {
			journal = new HashSet<>();
		}
		
		this.trialStartDate = start;
		this.trialEndDate = end;
		pcs = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
	
	/**
	 * Adds a reading to this patient's Journal. Behavior of this method is defined
	 * by the specific type of patient.
	 * @param reading
	 * @return
	 */
	public abstract boolean addReading(Reading reading);

	/**
	 * @return the id of this patient.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the new id for this patient.
	 */
	public void setId(String id) {
		if (!Objects.equals(this.id, id)) {
			String oldValue = this.id;
			this.id = id;
			pcs.firePropertyChange(PROP_ID, oldValue, this.id);
		}
	}

	/**
	 * @return the date this patient entered the trial.
	 */
	public LocalDate getTrialStartDate() {
		return trialStartDate;
	}

	/**
	 * @param trialStartDate the new trial start date for this patient.
	 */
	public void setTrialStartDate(LocalDate trialStartDate) {
		if (!Objects.equals(this.trialStartDate, trialStartDate)) {
			LocalDate oldValue = this.trialStartDate;
			this.trialStartDate = trialStartDate;
			pcs.firePropertyChange(PROP_TRIAL_START_DATE, oldValue, this.trialStartDate);
		}
	}

	/**
	 * @return the number of Readings in this patient's journal.
	 */
	public int getJournalSize() {
		return journal.size();
	}

	/**
	 * @return the date this patient left the trial.
	 */
	public LocalDate getTrialEndDate() {
		return trialEndDate;
	}

	/**
	 * @param trialEndDate the date this patient left the clinical trial. 
	 */
	public void setTrialEndDate(LocalDate trialEndDate) {
		if (!Objects.equals(this.trialEndDate, trialEndDate)) {
			LocalDate oldValue = this.trialEndDate;
			this.trialEndDate = trialEndDate;
			pcs.firePropertyChange(PROP_TRIAL_END_DATE, oldValue, this.trialEndDate);
		}
	}

	/**
	 * @return a reference to this patient's journal.
	 */
	public Set<Reading> getJournal() {
		return new HashSet<>(journal);
	}

	/**
	 * @param journal the new journal for this patient.
	 */
	protected void setJournal(Set<Reading> journal) {
		if (!Objects.equals(this.journal, journal)) {
			Set<Reading> oldValue = this.journal;
			this.journal = journal;
			pcs.firePropertyChange(PROP_JOURNAL, oldValue, this.journal);
		}
	}

	/**
	 * @param The reading to remove from this patient's journal.
	 */
	public void removeReading(Reading reading) {
		if (reading != null && journal.contains(reading)) {
			int oldValue = journal.size();
			if(journal.remove(reading)) {
				pcs.firePropertyChange(PROP_JOURNAL_SIZE, oldValue, journal.size());
			}
		}
	}

	/**
	 * Removes all of the readings from this patient's journal.
	 */
	public void removeAllReadings() {
		if (!journal.isEmpty()) {
			int oldValue = journal.size();
			journal.clear();
			pcs.firePropertyChange(PROP_JOURNAL_SIZE, oldValue, journal.size());
		}
	}

	/**
	 * @return true if the specified reading is contained in this patient's journal.
	 */
	public boolean containsReading(Reading reading) {
		return reading != null ? journal.contains(reading) : false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.toUpperCase().hashCode());
		result = prime * result + ((trialId == null) ? 0 : trialId.toUpperCase().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Patient)) {
			return false;
		}
		Patient other = (Patient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else {
			if (other.id != null) {
				if (id.compareToIgnoreCase(other.id) != 0 ) {
					return false; 
				}
			} else {
				return false;
			}
		}
		if (trialId == null) {
			if (other.trialId != null)
				return false;
		} else {
			if (other.trialId != null) {
				if (trialId.compareToIgnoreCase(other.trialId) != 0 ) {
					return false; 
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		StringBuilder builder = new StringBuilder();
		builder.append("Patient ");
		builder.append(id);
		builder.append(" (Trial ");
		builder.append(trialId);
		builder.append(") (");
		builder.append(trialStartDate.format(formatter));
		if (trialEndDate != null) {
			builder.append(" - ");
			builder.append(trialEndDate.format(formatter));
		}
		builder.append(") has ");
		builder.append(journal.size());
		builder.append(" reading");
		if (journal.size() != 1) {
			builder.append("s");
		}
		return builder.toString();
	}

	/**
	 * @return the trialId
	 */
	public String getTrialId() {
		return trialId;
	}

	/**
	 * @param trialId the trialId to set
	 */
	public void setTrialId(String trialId) {
		if (!Objects.equals(this.trialId, trialId)) {
			String oldValue = this.trialId;
			this.trialId = trialId;
			pcs.firePropertyChange("trialId", oldValue, this.trialId);
		}
	}
}