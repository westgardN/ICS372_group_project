/**
 * File: TrialModelException.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions;

/**
 * @author That Group
 *
 */
public class TrialModelException extends TrialException {
	private static final long serialVersionUID = 2389511877225824165L;

	/**
	 * 
	 */
	public TrialModelException() {
		super();
	}

	/**
	 * @param message
	 */
	public TrialModelException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TrialModelException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TrialModelException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TrialModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
