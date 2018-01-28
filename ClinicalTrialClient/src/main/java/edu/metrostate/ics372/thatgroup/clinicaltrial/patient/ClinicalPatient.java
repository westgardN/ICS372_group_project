package edu.metrostate.ics372.thatgroup.clinicaltrial.patient;

import java.time.LocalDate;
import java.util.Set;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;

/**
 * The Patient class represents a patient in a clinical trial. Because of this, the patient
 * contains an id and a journal that contains all of the Readings for the patient. Additionally,
 * the patient has a trial start and end date to track when they enter and leave the clinical trial.
 * <p>
 * <b>Notes:</b>
 * <ul><li>If the trial end date has been set for the patient, then when you activate the add
 * method, the date of the reading must be on or before the end date of the trial.</li></ul>
 *  
 * @author 
 *
 */
public class ClinicalPatient extends Patient {
	private static final long serialVersionUID = 8755342577713670301L;

	/**
	 * Initializes a new patient with no id an empty journal, a trial start date set to the 
	 * current date and a null trial end date.
	 */
	public ClinicalPatient() {
		super(null, null, LocalDate.now(), null);
	}
	
	/**
	 * Creates a new Patient object with the specified parameters
	 * 
	 * @param id
	 *            the new id for this patient
	 * @param journal
	 *            the new journal for this patient.
	 * @param startDate
	 * 			  the date this patient entered the trial 
	 * @param endDate
	 * 			  the date this patient left the trial 
	 */
	public ClinicalPatient(String id, Set<Reading> journal, LocalDate startDate, LocalDate endDate) {
		super(id, journal, startDate, endDate);
	}	

	/**
	 * Adds a new Reading to this patient's journal.
	 * <p>
	 * <b>Notes:</b>
	 * <ul><li>If the trial end date has been set for the patient, then when you activate the add
	 * method, the date of the reading must be on or before the end date of the trial.</li><li>
	 * The patient id of the reading is updated to be equal to this patient's id before it is added to the
	 * journal.</li></ul>
	 * 
	 * @param reading The reading to add to this patient's journal. If the reading
	 * is already in the journal it will not be added again. The reading must also not
	 * be null and must have a valid date.
	 * 
	 * @return true if the reading was added to this patient's journal; otherwise false is returned.
	 */
	@Override
	public boolean addReading(Reading reading) {
		if (reading == null) {
			throw new IllegalArgumentException("reading cannot be null!");
		}
		
		boolean answer = false;
		
		if (!journal.contains(reading)) {
			if (trialEndDate != null) {
				if (trialEndDate.compareTo(reading.getDate().toLocalDate()) >= 0) {
					reading.setPatientId(id);
					answer = journal.add(reading);
				}
			} else {
				reading.setPatientId(id);
				answer = journal.add(reading);
			}
		}
		
		return answer;
	}
	
	
}