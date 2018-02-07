/**
 * File: PatientFactory
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.patient;

/**
 * This factory is used to create patient objects of the type requested.
 * 
 * @author That Group
 *
 */
public class PatientFactory {
	/**
	 * The type for a clinical patient.
	 */
	public static final String PATIENT_CLINICAL = "clinical";

	/**
	 * Returns a new Patient object based on the type specified.
	 * If the specified type doesn't exist, an IllegalArgumentException is thrown.
	 * 
	 * @param type the type of patient to create
	 * @return a Patient reference for the desired type. 
	 * @throws IllegalArgumentException indicates that the requested type is not known.
	 */
	public static Patient getPatient(String type) {
		Patient answer = null;
		
		switch(type.toLowerCase()) {
		case PATIENT_CLINICAL:
			answer = new ClinicalPatient();
			break;
		default:
			throw new IllegalArgumentException("Unknown clinical type: " + type);
		}
		
		return answer;
	}

	/**
	 * Returns the type of patient object the specified patient refers to.
	 * 
	 * @param patient the patient object to get the type from
	 * 
	 * @return the type of patient this is.
	 * @throws IllegalArgumentException indicates that patient is of an unknown type. 
	 */
	public static String getPatientType(Patient patient) {
		String answer = null;
		
		if (patient instanceof ClinicalPatient) {
			answer = PATIENT_CLINICAL;
		} else {
			throw new IllegalArgumentException("patient cannot be null.");
		}
		
		return answer;
	}
}
