package edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions;

/**
 * An exception class to wrap other exceptions and to provide application
 * specific exception messages.
 *
 * @author That Group
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
     * @param message the application specific message to wrap in the exception.
     * @see java.lang.Exception
     */
    public TrialException(String message) {
        super(message);
    }

    /**
     * @param cause the exception that caused us to throw an exception.
     * @see java.lang.Exception
     */
    public TrialException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message the application specific message to wrap in the exception.
     * @param cause   the exception that caused us to throw an exception.
     * @see java.lang.Exception
     */
    public TrialException(String message, Throwable cause) {
        super(message, cause);
    }
}