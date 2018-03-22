/**
 * File: TrialCatalogException.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions;

/**
 * @author That Group
 *
 */
public class TrialCatalogException extends TrialException {
	private static final long serialVersionUID = 9154916113014091785L;

	/**
	 * 
	 */
	public TrialCatalogException() {
		super();
	}

	/**
	 * @param message
	 */
	public TrialCatalogException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TrialCatalogException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TrialCatalogException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TrialCatalogException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
