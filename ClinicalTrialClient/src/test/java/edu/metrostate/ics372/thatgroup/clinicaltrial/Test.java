package edu.metrostate.ics372.thatgroup.clinicaltrial;

import static org.junit.Assert.*;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Test {

	@org.junit.Test
	public void test() {
		//int h = "", m = 12, s = 12;
		final String TIME = "(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)";
		System.out.println(String.format("%s:%s:%s", "", "", "").matches(TIME));
	}

}
