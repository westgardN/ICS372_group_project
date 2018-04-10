/**
 * File Clinic.java
 */

package edu.metrostate.ics372.thatgroup.clinicaltrial.models;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Clinic class represents a clinic in a trial. A clinic has an
 * id, name, and associated trial id.
 *
 * @author That Group
 */
public class Clinic implements Serializable {
    /**
     * The default ID used for a new Clinic if one isn't provided.
     */
    public static final String DEFAULT_ID = "default";

    /**
     * The PROP_ID event is fired whenever the id of this clinic is changed.
     */
    public static final String PROP_ID = "id";

    /**
     * The PROP_TRIAL_ID event is fired whenever the trial id of this clinic is changed.
     */
    public static final String PROP_TRIAL_ID = "trialId";

    /**
     * The PROP_NAME event is fired whenever the name of this clinic is changed.
     */
    public static final String PROP_NAME = "name";
    private static final long serialVersionUID = 6613024190196045827L;
    protected transient PropertyChangeSupport pcs;
    protected String id;
    protected String trialId;
    protected String name;

    /**
     * Initializes an empty clinic.
     */
    public Clinic() {
        this(null, null, null);
    }

    /**
     * Initializes a clinic with the specified id, trial id, and name.
     *
     * @param id
     * @param trialId
     * @param name
     */
    public Clinic(String id, String trialId, String name) {
        this.id = id;
        this.trialId = trialId;
        this.name = name;
        pcs = new PropertyChangeSupport(this);
    }

    protected PropertyChangeSupport getPcs() {
        if (pcs == null) {
            pcs = new PropertyChangeSupport(this);
        }

        return pcs;
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
            getPcs().firePropertyChange(PROP_ID, oldValue, this.id);
        }
    }

    /**
     * @return the id of the trial that this patient belongs to.
     */
    public String getTrialId() {
        return trialId;
    }

    /**
     * @param trialId the new id of the trial that this patient belongs to.
     *                May be null is this patient doesn't belong to any trial.
     */
    public void setTrialId(String trialId) {
        if (!Objects.equals(this.trialId, trialId)) {
            String oldValue = this.trialId;
            this.trialId = trialId;
            getPcs().firePropertyChange(PROP_TRIAL_ID, oldValue, this.trialId);
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        if (!Objects.equals(this.name, name)) {
            String oldValue = this.name;
            this.name = name;
            getPcs().firePropertyChange(PROP_NAME, oldValue, this.name);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((trialId == null) ? 0 : trialId.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Clinic)) {
            return false;
        }
        Clinic other = (Clinic) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equalsIgnoreCase(other.id)) {
            return false;
        }
        if (trialId == null) {
            return other.trialId == null;
        } else return trialId.equalsIgnoreCase(other.trialId);
    }

}
