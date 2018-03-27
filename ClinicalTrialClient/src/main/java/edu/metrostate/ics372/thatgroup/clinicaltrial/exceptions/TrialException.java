/**
 * File: TrialException.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions;

/**
 * An exception class to wrap other exceptions and to provide application
 * specific exception messages.
 * 
 * @author That Group
 *
 */
public class TrialException extends Exception {
	private static final long serialVersionUID = 7045841730497544128L;

	/**
	 * @see java.lang.Exception
	 */
	public TrialException() {
		super();
	}

	/**
	 * @see java.lang.Exception
	 * 
	 * @param message the application specific message to wrap in the exception.
	 */
	public TrialException(String message) {
		super(message);
	}

	/**
	 * @see java.lang.Exception
	 * 
	 * @param cause the exception that caused us to throw an exception. 
	 */
	public TrialException(Throwable cause) {
		super(cause);
	}

	/**
	 * @see java.lang.Exception

	 * @param message the application specific message to wrap in the exception.
	 * @param cause the exception that caused us to throw an exception. 
	 */
	public TrialException(String message, Throwable cause) {
		super(message, cause);
	}
}
