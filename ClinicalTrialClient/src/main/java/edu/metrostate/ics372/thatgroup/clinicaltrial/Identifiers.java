package edu.metrostate.ics372.thatgroup.clinicaltrial;

public enum Identifiers {
	ARRAY_NAME("patient_readings"), PATIENT_ID("patient_id"), READING_TYPE("reading_type"), READING_ID(
			"reading_id"), READING_VALUE("reading_value"), READING_DATE("reading_date");

	private String identifier;

	private Identifiers(String identifier) {
		this.identifier = identifier;
	}

	public String get() {
		return identifier;
	}
}
