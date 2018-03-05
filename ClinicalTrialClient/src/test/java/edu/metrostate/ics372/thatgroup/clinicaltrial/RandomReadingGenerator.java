package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.time.LocalDateTime;
import java.util.Random;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;

class RandomReadingGenerator {
	
	protected static Reading getRandomReading(String type, Patient patient) {
		Random rand = new Random();
		int randId = rand.nextInt((99999 - 15000) + 1) + 15000;
		int randSys = rand.nextInt((250 - 90) + 1) + 90;
		int randDia = rand.nextInt((140 - 60) + 1) + 60;
		int randWeight = rand.nextInt((400 - 125) + 1) + 125;
		int randSteps = rand.nextInt((30000 - 200) + 1) + 200;
		int randTemp = rand.nextInt((99 - 97) + 1) + 97;
		
		Reading reading = ReadingFactory.getReading(type);
		reading.setId(String.format("%s", randId));
		switch (type) {
		case "blood_press":
			reading.setValue(String.format("%d/%d", randSys, randDia));
			break;
		case "weight":
			reading.setValue(String.format("%d", randWeight));
			break;
		case "steps":
			reading.setValue(String.format("%d", randSteps));
			break;
		case "temp":
			reading.setValue(String.format("%d", randTemp));
			break;
		}
		reading.setDate(LocalDateTime.now());
		if (patient != null) {
			reading.setPatientId(patient.getId());
		}
		return reading;
	}
}
