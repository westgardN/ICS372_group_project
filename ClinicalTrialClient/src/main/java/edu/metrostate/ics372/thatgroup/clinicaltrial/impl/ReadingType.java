package edu.metrostate.ics372.thatgroup.clinicaltrial.impl;

import javafx.beans.property.SimpleStringProperty;

public enum ReadingType {
	WEIGHT("weight"),
	STEPS("steps"),
	TEMP("temp"),
	BLOOD_PRESS("blood_press");
	
	private String type;
	
	private ReadingType(String type) {
		this.type = type;
	}
	
	public String get() {
		return type;
	}
	
	public SimpleStringProperty getObservable() {
		SimpleStringProperty observable = new SimpleStringProperty(type);
		return observable;
	}
}
