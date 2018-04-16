package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

public class PatientStatus implements Serializable {
	
	/**
	 * The PROP_STATUS_ID event is fired whenever the status id is changed.
	 */
	public static final String PROP_STATUS_ID = "statusId";
	
	/**
	 * The PROP_DISPLAY_STATUS event is fired whenever the status o is changed.
	 */
	public static final String PROP_DISPLAY_STATUS = "displayStatus";
	public static final String INACTIVE_ID = "INACTIVE";
	public static final String ACTIVE_ID = "ACTIVE";
	public static final String WITHDRAWN_ID = "WITHDRAWN";
	public static final String FAILED_ID = "FAILED";
	public static final String COMPLETED_ID = "COMPLETED";
	
	private static final long serialVersionUID = 3590803871919635415L;


	private String id;
	private String displayStatus;
	protected transient PropertyChangeSupport pcs;
	
	public PatientStatus() {
		this(null, null);
	}
	
	public PatientStatus(String id, String displayStatus) {
		this.id = id;
		this.displayStatus = displayStatus;
		pcs = new PropertyChangeSupport(this);
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		if (!Objects.equals(this.id, id)) {
			String oldValue = this.id;
			this.id = id;
			getPcs().firePropertyChange(PROP_STATUS_ID, oldValue, this.id);
		}
	}
	
	/**
	 * @return the displayStatus
	 */
	public String getDisplayStatus() {
		return displayStatus;
	}
	
	/**
	 * @param displayStatus the displayStatus to set
	 */
	public void setDisplayStatus(String displayStatus) {
		if (!Objects.equals(this.displayStatus, displayStatus)) {
			String oldValue = this.displayStatus;
			this.displayStatus = displayStatus;
			getPcs().firePropertyChange(PROP_DISPLAY_STATUS, oldValue, this.displayStatus);
		}
	}
	
	protected PropertyChangeSupport getPcs() {
		if (pcs == null) {
			pcs = new PropertyChangeSupport(this);
		}
		return pcs;
	}
	
	/**
	 * Add a PropertyChangeListener to the listener list. The listener is registered
	 * for all properties. The same listener object may be added more than once, and
	 * will be called as many times as it is added. If listener is null, no
	 * exception is thrown and no action is taken.
	 * 
	 * @param listener
	 *            - The PropertyChangeListener to be added
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		getPcs().addPropertyChangeListener(listener);
    }
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		getPcs().removePropertyChangeListener(listener);
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		PatientStatus other = (PatientStatus) obj;
		if (!Objects.equals(this.id, other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return displayStatus;
	}
	
	
}
