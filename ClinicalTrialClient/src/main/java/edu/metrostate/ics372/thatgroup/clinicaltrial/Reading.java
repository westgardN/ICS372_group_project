package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.time.LocalDateTime;

public class Reading {
	private String patientID;
	private ReadingType readingType;
	private String readingValue;
	private String readingID;
	private LocalDateTime readingDate;
	
	public Reading() {
	}

	/**
	 * Creates a new Reading object with the specified parameters
	 * 
	 * @param patient_id
	 *            the patient_id to be used
	 * @param readingType
	 *            the reading_type to be used
	 * @param reading_value
	 *            the reading_value to be used
	 * @param reading_id
	 *            the reading_id to be used
	 * @param item_id
	 *            the item_id to be used
	 * @param reading_date
	 *            the reading_date to be used
	 */
	public Reading(String patientID, ReadingType readingType, String readingValue, String readingID, LocalDateTime readingDate) {
		this.patientID = patientID;
		this.readingType = readingType;
		this.readingValue = readingValue;
		this.readingID = readingID;
		this.readingDate = readingDate;
	}

	/**
	 * Returns this Reading's patient ID
	 * 
	 * @return the patient_id
	 */
	public String getPatient_id() {
		return patientID;
	}

	/**
	 * Sets this Reading's patient ID
	 * 
	 * @param patient_id
	 *            the patient_id to set
	 */
	public void setPatient_id(String patientID) {
		this.patientID = patientID;
	}

	/**
	 * Returns this Reading's reading type
	 * 
	 * @return the reading_type
	 */
	public ReadingType getReading_type() {
		return readingType;
	}

	/**
	 * Sets this Reading's reading type
	 * 
	 * @param reading_type
	 *            the reading_type to set
	 */
	public void setReading_type(ReadingType readingType) {
		this.readingType = readingType;
	}

	/**
	 * Returns this Reading's reading value
	 * 
	 * @return the reading_value
	 */
	public String getReading_value() {
		return readingValue;
	}

	/**
	 * Sets this Reading's reading value
	 * 
	 * @param reading_value
	 *            the reading_value to set
	 */
	public void setReading_value(String readingValue) {
		this.readingValue = readingValue;
	}

	/**
	 * Returns this Reading's reading ID
	 * 
	 * @return the reading_id
	 */
	public String getReading_id() {
		return readingID;
	}

	/**
	 * Sets this Reading's reading typ
	 * 
	 * @param reading_id
	 *            the reading_id to set
	 */
	public void setReading_id(String readingID) {
		this.readingID = readingID;
	}

	/**
	 * Returns this Reading's reading date
	 * 
	 * @return the reading_date
	 */
	public LocalDateTime getReading_date() {
		return readingDate;
	}

	/**
	 * Sets this Reading's reading date
	 * 
	 * @param reading_date
	 *            the reading_date to set
	 */
	public void setReading_date(LocalDateTime readingDate) {
		this.readingDate = readingDate;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patientID == null) ? 0 : patientID.hashCode());
		result = prime * result + ((readingDate == null) ? 0 : readingDate.hashCode());
		result = prime * result + ((readingID == null) ? 0 : readingID.hashCode());
		result = prime * result + ((readingType == null) ? 0 : readingType.hashCode());
		result = prime * result + ((readingValue == null) ? 0 : readingValue.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reading other = (Reading) obj;
		if (patientID == null) {
			if (other.patientID != null)
				return false;
		} else if (!patientID.equals(other.patientID))
			return false;
		if (readingDate == null) {
			if (other.readingDate != null)
				return false;
		} else if (!readingDate.equals(other.readingDate))
			return false;
		if (readingID == null) {
			if (other.readingID != null)
				return false;
		} else if (!readingID.equals(other.readingID))
			return false;
		if (readingType != other.readingType)
			return false;
		if (readingValue == null) {
			if (other.readingValue != null)
				return false;
		} else if (!readingValue.equals(other.readingValue))
			return false;
		return true;
	}

	/**
	 * Prints the parameters of this Reading in a meaningful way
	 */
	@Override
	public String toString() {
		return "Reading ["
				+ "\npatient_id=" + patientID 
				+ "\nreading_type=" + readingType 
				+ "\nreading_value="+ readingValue 
				+ "\nreading_id=" + readingID 
				+ "\nreading_date=" + readingDate + "]";
	}
}