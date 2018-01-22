package edu.metrostate.ics372.thatgroup.clinicaltrial.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.metrostate.ics372.thatgroup.clinicaltrial.impl.ExampleClass;

public class ExampleTest {

	@Test
	public void test() {
		ExampleClass ex = new ExampleClass();
		assertEquals("Hello, That Group", ex.getFoo());
	}

}
