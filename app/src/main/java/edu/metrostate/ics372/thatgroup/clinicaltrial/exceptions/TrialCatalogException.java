package edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions;

/**
 * Used by the TrialCatalog and implementing classes to indicate the error
 * is related to a TrialCatalog operation.
 *
 * @author That Group
 */
public class TrialCatalogException extends TrialException {
    private static final long serialVersionUID = 9154916113014091785L;

    /**
     * @see java.lang.Exception
     */
    public TrialCatalogException() {
        super();
    }

    /**
     * @param message the application specific message to wrap in the exception.
     * @see java.lang.Exception
     */
    public TrialCatalogException(String message) {
        super(message);
    }

    /**
     * @param cause the exception that caused us to throw an exception.
     * @see java.lang.Exception
     */
    public TrialCatalogException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message the application specific message to wrap in the exception.
     * @param cause   the exception that caused us to throw an exception.
     * @see java.lang.Exception
     */
    public TrialCatalogException(String message, Throwable cause) {
        super(message, cause);
    }
}