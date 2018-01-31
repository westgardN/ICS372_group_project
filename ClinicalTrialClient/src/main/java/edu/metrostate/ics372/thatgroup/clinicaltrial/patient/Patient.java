package edu.metrostate.ics372.thatgroup.clinicaltrial.patient;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

/**
 * 
 * @author 
 *
 */
public abstract class Patient implements Serializable {
	private static final long serialVersionUID = 8450664877127813850L;
	protected String id;
	protected String trialId;
	protected LocalDate trialStartDate;
	protected LocalDate trialEndDate;
	protected Set<Reading> journal = new HashSet<>();

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
		setJournal(journal);
		this.trialStartDate = start;
		this.trialEndDate = end;
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
		this.id = id;
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
		this.trialStartDate = trialStartDate;
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
		this.trialEndDate = trialEndDate;
	}

	/**
	 * @return a reference to this patient's journal.
	 */
	public Set<Reading> getJournal() {
		return journal;
	}

	/**
	 * @param journal the new journal for this patient.
	 */
	public void setJournal(Set<Reading> journal) {
		this.journal = journal == null ? new HashSet<>() : new HashSet<>(journal); // Create our own copy of the set.
	}

	/**
	 * @param The reading to remove from this patient's journal.
	 */
	public void removeReading(Reading reading) {
		if (reading != null && journal.contains(reading)) {
			journal.remove(reading);
		}
	}

	/**
	 * Removes all of the readings from this patient's journal.
	 */
	public void removeAllReadings() {
		journal.clear();
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((trialId == null) ? 0 : trialId.hashCode());
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
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (trialId == null) {
			if (other.trialId != null) {
				return false;
			}
		} else if (!trialId.equals(other.trialId)) {
			return false;
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
		this.trialId = trialId;
	}
}