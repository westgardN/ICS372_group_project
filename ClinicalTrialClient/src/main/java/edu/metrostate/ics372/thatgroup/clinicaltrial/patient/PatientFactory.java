/**
 * File: PatientFactory
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.patient;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class PatientFactory {

	public static Patient getPatient(String type) {
		Patient answer = null;
		
		switch(type.toLowerCase()) {
		case "clinical":
			answer = new ClinicalPatient();
			break;
		default:
			throw new IllegalArgumentException("Unknown clinical type: " + type);
		}
		
		return answer;
	}

	public static String getPatientType(Patient patient) {
		String answer = null;
		
		if (patient instanceof ClinicalPatient) {
			answer = "clinical";
		} else {
			throw new IllegalArgumentException("patient cannot be null.");
		}
		
		return answer;
	}
}
