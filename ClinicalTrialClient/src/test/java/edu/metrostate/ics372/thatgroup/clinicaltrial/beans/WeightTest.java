/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * @author That group
 *
 */
public class WeightTest {
	private static final String DEFAULT_PATIENT_ID = "test";
	private static final String DEFAULT_ID = "test";
	private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
	private static final Long LONG_VALUE = 99L;
	private static final String DEFAULT_UNIT = "lbs";
	private static final String STRING_VALUE = "99 lbs";
	private static final String DEFAULT_CLINIC_ID = "test";

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Weight#toString()}.
	 */
	@Test
	public void testToString() {
		Weight weight = new Weight();
		weight.setClinicId(DEFAULT_CLINIC_ID);
		weight.setPatientId(DEFAULT_PATIENT_ID);
		weight.setId(DEFAULT_ID);
		weight.setDate(DEFAULT_DATE);
		weight.setValue(STRING_VALUE);
		
		System.out.println(weight);
		
		UnitValue value = (UnitValue) weight.getValue();
		Long longVal = (Long) value.getNumberValue();
		String strUnit = value.getUnit();
		
		assertEquals(LONG_VALUE, longVal);
		assertEquals(DEFAULT_UNIT, strUnit);
		
		UnitValue newVal = new UnitValue(longVal, strUnit);
		
		assertEquals(newVal, value);
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Weight#getValue()} and 
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Weight#setValue(java.lang.Object)}..
	 */
	@Test
	public void testWeightLongValue() {
		Weight weight = new Weight();
		
		weight.setValue(LONG_VALUE);
		
		assertEquals(LONG_VALUE, weight.getValue());
		
	}
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Weight#getValue()} and 
	 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Weight#setValue(java.lang.Object)}..
	 */
	@Test
	public void testWeightStringValue() {
		Weight weight = new Weight();
		
		weight.setValue(STRING_VALUE);
		
		assertEquals(STRING_VALUE, weight.getValue());
		
	}
	

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Weight#Weight()}.
	 */
	@Test
	public void testWeight() {
		Weight weight = new Weight(null, null, null, STRING_VALUE, null);
		weight.setPatientId(DEFAULT_PATIENT_ID);
		
		assertNotNull(weight);
		assertEquals(DEFAULT_PATIENT_ID, weight.getPatientId());
		assertNull(weight.getId());
		assertNull(weight.getDate());
		assertNotNull( weight.getValue());
		assertNull(weight.getClinicId());
		
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Weight#Weight(java.lang.String, java.lang.String, java.time.LocalDateTime, java.lang.Object, java.lang.String)}.
	 */
	@Test
	public void testWeightStringStringLocalDateTimeObjectString() {
		Weight weight = new Weight(DEFAULT_PATIENT_ID, DEFAULT_ID, DEFAULT_DATE, LONG_VALUE, DEFAULT_CLINIC_ID);
		weight.setPatientId(DEFAULT_PATIENT_ID);
		
		assertNotNull(weight);
		assertEquals(DEFAULT_PATIENT_ID, weight.getPatientId());
		assertEquals(DEFAULT_ID,  weight.getId());
		assertEquals(DEFAULT_DATE, weight.getDate());
		assertEquals(LONG_VALUE, weight.getValue());
		assertEquals(DEFAULT_CLINIC_ID, weight.getClinicId());
	}

}
