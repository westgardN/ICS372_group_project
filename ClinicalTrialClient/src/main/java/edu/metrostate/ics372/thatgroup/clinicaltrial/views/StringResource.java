package edu.metrostate.ics372.thatgroup.clinicaltrial.views;


/**
 * Holds string literals used in the implementation of the user interface
 *
 */
public enum StringResource {
	EMPTY(""),
	
	// Blood pressure and temp type values from the choicebox on the add reading form
	BP_VALUE("blood pressure"),
	BP_JSON("blood_press"),
	TEMP_VALUE("temp"),
	
	// Messages 
		// Error Messages
	ERR_DATE_MSG("Date can not be empty."),
	ERR_TIME_MSG("Invalid time detected.  Please check the time input fields for errors."),
	ERR_ID_MSG("Invalid reading id detected.  Please check the reading id input fields for errors."),
	ERR_BLOOD_PRESSURE_MSG("Invalid blood pressure detected.  Please check the blood pressure input fields for errors."),
	ERR_TEMP_MSG("Invalid temperature detected.  Please check the reading value input field for errors."),
	ERR_VALUE_MSG("Invalid input detected.  Please check the reading value input field for errors."),
	INACTIVE_PATIENT_MSG("This patient is not active in the clinical trial."),
	PATIENT_NOT_ADDED_MSG("Unable to add patient to the trial."),
	READING_NOT_ADDED_MSG ("Unable to add the reading. Please verify the Reading ID is unique for this patient."),
		// Confirmation messages
	PATIENT_ADDED_MSG("New Patient Added"),
	READING_ADDED_MSG("Reading has been added."),
	SPECIAL_CHAR_MSG("No special characters allowed.");
	
	private String literal;
	
	private StringResource(String literal ) {	
		this.literal = literal;
	}
	
	public String get() {
		return literal;
	}
}
