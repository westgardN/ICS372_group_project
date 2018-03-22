/**
 * File: TrialException.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions;

/**
 * @author That Group
 *
 */
public class TrialException extends Exception {
	private static final long serialVersionUID = 7045841730497544128L;

	/**
	 * 
	 */
	public TrialException() {
		super();
	}

	/**
	 * @param message
	 */
	public TrialException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TrialException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TrialException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TrialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
