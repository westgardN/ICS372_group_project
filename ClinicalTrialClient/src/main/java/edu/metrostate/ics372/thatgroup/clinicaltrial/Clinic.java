package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.Serializable;

public class Clinic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6613024190196045827L;
	String id;
	String trialId;
	String name;
	
	public Clinic() {
		
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
		this.id = id;
	}

	/**
	 * @return the trialId
	 */
	public String getTrialId() {
		return trialId;
	}

	/**
	 * @param trialId the trialId to set
	 */
	public void setTrialId(String trialId) {
		this.trialId = trialId;
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
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Clinic [id=" + id + ", trialId=" + trialId + ", name=" + name + "]";
	}
	
	
}
