package edu.metrostate.ics372.thatgroup.clinicaltrial.impl;

public enum ReadingType {
	WEIGHT("weight"),
	STEPS("steps"),
	TEMP("temp"),
	BLOOD_PRESS("blood_press");
	
	private String type;
	
	private ReadingType(String type) {
		this.type = type;
	}
	
	public String Get() {
		return type;
	}
}
