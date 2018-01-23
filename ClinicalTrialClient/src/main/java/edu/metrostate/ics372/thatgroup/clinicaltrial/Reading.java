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
	 * @param reading_id
	 *            the reading_id to be used
	 * @param reading_value
	 *            the reading_value to be used
	 * @param item_id
	 *            the item_id to be used
	 * @param reading_date
	 *            the reading_date to be used
	 */
	public Reading(String patientID, ReadingType readingType, String readingID, String readingValue, LocalDateTime readingDate) {
		this.patientID = patientID;
		this.readingType = readingType;
		this.readingID = readingID;
		this.readingValue = readingValue;
		this.readingDate = readingDate;
	}
	
	/**
	 * @return the patientID
	 */
	public String getPatientID() {
		return patientID;
	}

	/**
	 * @param patientID the patientID to set
	 */
	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	/**
	 * @return the readingType
	 */
	public ReadingType getReadingType() {
		return readingType;
	}

	/**
	 * @param readingType the readingType to set
	 */
	public void setReadingType(ReadingType readingType) {
		this.readingType = readingType;
	}

	/**
	 * @return the readingValue
	 */
	public String getReadingValue() {
		return readingValue;
	}

	/**
	 * @param readingValue the readingValue to set
	 */
	public void setReadingValue(String readingValue) {
		this.readingValue = readingValue;
	}

	/**
	 * @return the readingID
	 */
	public String getReadingID() {
		return readingID;
	}

	/**
	 * @param readingID the readingID to set
	 */
	public void setReadingID(String readingID) {
		this.readingID = readingID;
	}

	/**
	 * @return the readingDate
	 */
	public LocalDateTime getReadingDate() {
		return readingDate;
	}

	/**
	 * @param readingDate the readingDate to set
	 */
	public void setReadingDate(LocalDateTime readingDate) {
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
		return "{"
				+ "\n\"patient_id\":" + patientID
				+ "\n\"reading_type\":" + readingType
				+ "\n\"reading_id\":" + readingID
				+ "\n\"reading_value\":"+ readingValue
				+ "\n\"reading_date\":" + readingDate
				+ "\n},";
	}
}