package edu.metrostate.ics372.thatgroup.clinicaltrial.impl;

import java.time.LocalDateTime;

public class Reading {
	private String patient_id;
	private ReadingType reading_type;
	private String reading_value;
	private String reading_id;
	private LocalDateTime reading_date;
	
	public Reading() {
	}

	/**
	 * Creates a new Reading object with the specified parameters
	 * 
	 * @param patient_id
	 *            the patient_id to be used
	 * @param reading_type
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
	public Reading(String patient_id, ReadingType reading_type, String reading_id, String reading_value, LocalDateTime reading_date) {
		this.patient_id = patient_id;
		this.reading_type = reading_type;
		this.reading_value = reading_value;
		this.reading_id = reading_id;
		this.reading_date = reading_date;
	}

	/**
	 * Returns this Reading's patient ID
	 * 
	 * @return the patient_id
	 */
	public String getPatient_id() {
		return patient_id;
	}

	/**
	 * Sets this Reading's patient ID
	 * 
	 * @param patient_id
	 *            the patient_id to set
	 */
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	/**
	 * Returns this Reading's reading type
	 * 
	 * @return the reading_type
	 */
	public ReadingType getReading_type() {
		return reading_type;
	}

	/**
	 * Sets this Reading's reading type
	 * 
	 * @param reading_type
	 *            the reading_type to set
	 */
	public void setReading_type(ReadingType reading_type) {
		this.reading_type = reading_type;
	}

	/**
	 * Returns this Reading's reading value
	 * 
	 * @return the reading_value
	 */
	public String getReading_value() {
		return reading_value;
	}

	/**
	 * Sets this Reading's reading value
	 * 
	 * @param reading_value
	 *            the reading_value to set
	 */
	public void setReading_value(String reading_value) {
		this.reading_value = reading_value;
	}

	/**
	 * Returns this Reading's reading ID
	 * 
	 * @return the reading_id
	 */
	public String getReading_id() {
		return reading_id;
	}

	/**
	 * Sets this Reading's reading typ
	 * 
	 * @param reading_id
	 *            the reading_id to set
	 */
	public void setReading_id(String reading_id) {
		this.reading_id = reading_id;
	}

	/**
	 * Returns this Reading's reading date
	 * 
	 * @return the reading_date
	 */
	public LocalDateTime getReading_date() {
		return reading_date;
	}

	/**
	 * Sets this Reading's reading date
	 * 
	 * @param reading_date
	 *            the reading_date to set
	 */
	public void setReading_date(LocalDateTime reading_date) {
		this.reading_date = reading_date;
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
		result = prime * result + ((patient_id == null) ? 0 : patient_id.hashCode());
		result = prime * result + ((reading_date == null) ? 0 : reading_date.hashCode());
		result = prime * result + ((reading_id == null) ? 0 : reading_id.hashCode());
		result = prime * result + ((reading_type == null) ? 0 : reading_type.hashCode());
		result = prime * result + ((reading_value == null) ? 0 : reading_value.hashCode());
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
		if (patient_id == null) {
			if (other.patient_id != null)
				return false;
		} else if (!patient_id.equals(other.patient_id))
			return false;
		if (reading_date == null) {
			if (other.reading_date != null)
				return false;
		} else if (!reading_date.equals(other.reading_date))
			return false;
		if (reading_id == null) {
			if (other.reading_id != null)
				return false;
		} else if (!reading_id.equals(other.reading_id))
			return false;
		if (reading_type != other.reading_type)
			return false;
		if (reading_value == null) {
			if (other.reading_value != null)
				return false;
		} else if (!reading_value.equals(other.reading_value))
			return false;
		return true;
	}

	/**
	 * Prints the parameters of this Reading in a meaningful way
	 */
	@Override
	public String toString() {
		return "Reading ["
				+ "\npatient_id=" + patient_id 
				+ "\nreading_type=" + reading_type 
				+ "\nreading_value="+ reading_value 
				+ "\nreading_id=" + reading_id 
				+ "\nreading_date=" + reading_date + "]";
	}
}