package edu.metrostate.ics372.thatgroup.clinicaltrial.patient;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

public abstract class Patient implements Serializable {
	private static final long serialVersionUID = 8450664877127813850L;
	protected String id;
	protected LocalDate trialStartDate;
	protected LocalDate trialEndDate;
	protected Set<Reading> journal = new HashSet<>();

	/**
	 * Initializes an empty patient.
	 */
	public Patient() {
		super();
	}

	/**
	 * Initializes an empty patient.
	 */
	public Patient(String id, Set<Reading> readings, LocalDate start, LocalDate end) {
		super();
		this.id = id;
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
	 * @return a copy of the patient's journal.
	 */
	public Set<Reading> getJournal() {
		return new HashSet<>(journal);
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

}