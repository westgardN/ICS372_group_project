/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

/**
 * @author Gabriel
 *
 */
public class TrialCatalogException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2399998776253224586L;

	public TrialCatalogException() {
        super();
    }

    public TrialCatalogException(String msg) {
        super(msg);
    }
    
    public TrialCatalogException(String msg, Throwable ex) {
        super(msg, ex);
    }
}