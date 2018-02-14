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
 * The patient contains an id and a journal that contains all of the Readings for the patient. Additionally,
 * the patient has a trial start and end date to track when they enter and leave the clinical trial.
 * 
 * @author That Group 
 *
 */
public abstract class Patient implements Serializable {
	public static final String PROP_ID = "id";
	public static final String PROP_TRIAL_START_DATE = "trialStartDate";
	public static final String PROP_TRIAL_END_DATE = "trialEndDate";
	public static final String PROP_JOURNAL = "journal";
	public static final String PROP_JOURNAL_SIZE = "journalSize";
	private static final long serialVersionUID = 8450664877127813850L;
	protected transient PropertyChangeSupport pcs;
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


	protected PropertyChangeSupport getPcs() {
		if (pcs == null) {
			pcs = new PropertyChangeSupport(this);
		}
		
		return pcs;
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
	 * Adds a reading to this patient's Journal. Behavior of this method is defined
	 * by the specific type of patient.
	 * 
	 * @param reading The reading to add
	 * @return True if the reading was added to the journal; Otherwise false.
	 */
	public abstract boolean addReading(Reading reading);

	/**
	 * @return the id of this patient.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the new id for this patient. Cannot be null.
	 */
	public void setId(String id) {
		if (!Objects.equals(this.id, id)) {
			String oldValue = this.id;
			this.id = id;
			getPcs().firePropertyChange(PROP_ID, oldValue, this.id);
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
			getPcs().firePropertyChange(PROP_TRIAL_START_DATE, oldValue, this.trialStartDate);
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
			getPcs().firePropertyChange(PROP_TRIAL_END_DATE, oldValue, this.trialEndDate);
		}
	}

	/**
	 * @return an independent set to this patient's journal. Modifications made to 
	 * the returned set do not affect this patient's journal.
	 */
	public Set<Reading> getJournal() {
		return new HashSet<>(journal);
	}

	/**
	 * @param journal the new journal for this patient. Cannot be null.
	 */
	protected void setJournal(Set<Reading> journal) {
		if (!Objects.equals(this.journal, journal)) {
			Set<Reading> oldValue = this.journal;
			this.journal = journal;
			getPcs().firePropertyChange(PROP_JOURNAL, oldValue, this.journal);
		}
	}

	/**
	 * Removes the specified reading from this patient's journal if it exists.
	 * 
	 * @param reading The reading to remove from this patient's journal.
	 */
	public void removeReading(Reading reading) {
		if (reading != null && journal.contains(reading)) {
			int oldValue = journal.size();
			if(journal.remove(reading)) {
				getPcs().firePropertyChange(PROP_JOURNAL_SIZE, oldValue, journal.size());
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
			getPcs().firePropertyChange(PROP_JOURNAL_SIZE, oldValue, journal.size());
		}
	}

	/**
	 * Returns true if the specified reading is in this patient's journal
	 * 
	 * @param reading the reading to check if it exists in this patient's journal
	 * 
	 * @return true if the specified reading is contained in this patient's journal; otherwise false.
	 */
	public boolean containsReading(Reading reading) {
		return reading != null ? journal.contains(reading) : false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.toUpperCase().hashCode());
		result = prime * result + ((trialId == null) ? 0 : trialId.toUpperCase().hashCode());
		return result;
	}

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
	 * @return the id of the trial that this patient belongs to.
	 */
	public String getTrialId() {
		return trialId;
	}

	/**
	 * @param trialId the new id of the trial that this patient belongs to.
	 * May be null is this patient doesn't belong to any trial.
	 */
	public void setTrialId(String trialId) {
		if (!Objects.equals(this.trialId, trialId)) {
			String oldValue = this.trialId;
			this.trialId = trialId;
			getPcs().firePropertyChange("trialId", oldValue, this.trialId);
		}
	}
}