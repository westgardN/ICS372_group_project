package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * 
 */

/**
 * @author That Group
 *String patientId, String id, LocalDateTime date, Object value, String clinicId)
 */
public class TempTest {
	private static final String DEFAULT_PATIENT_ID = "test";
	private static final String DEFAULT_ID = "test";
	private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
	private static final Long LONG_VALUE = 99L;
	private static final String DEFAULT_UNIT = "lbs";
	private static final String STRING_VALUE = "99 lbs";
	private static final String DEFAULT_CLINIC_ID = "test";
	
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Temp#toString()}.
	 */
	@Test
	public final void testToString() {
		Temp temp= new Temp();
		temp.setClinicId(DEFAULT_CLINIC_ID);
		temp.setPatientId(DEFAULT_PATIENT_ID);
		temp.setId(DEFAULT_ID);
		temp.setDate(DEFAULT_DATE);
		temp.setValue(STRING_VALUE);
		
		System.out.println(temp);
		
		UnitValue value = (UnitValue) temp.getValue();
		Long longVal = (Long) value.getNumberValue();
		String strUnit = value.getUnit();
		
		assertEquals(LONG_VALUE, longVal);
		assertEquals(DEFAULT_UNIT, strUnit);
		
		UnitValue newVal = new UnitValue(longVal, strUnit);
		
		assertEquals(newVal, value);
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Temp#getValue()} and
	 *  {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Temp#setValue(java.lang.Object)}.
	 */
	@Test
	public final void testLongValue() {
		Temp temp= new Temp();
		
		temp.setValue(LONG_VALUE);
		
		
		UnitValue value = (UnitValue) temp.getValue();
		Long longVal = (Long) value.getNumberValue();
		String strUnit = value.getUnit();
		UnitValue newVal = new UnitValue(longVal, strUnit);
		
		assertEquals(newVal, value);
		//assertEquals(LONG_VALUE, temp.getValue());
	}
	
	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Temp#getValue()} and
	 *  {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Temp#setValue(java.lang.Object)}.
	 */
	@Test
	public final void testStringValue() {
		Temp temp= new Temp();
		
		temp.setValue(STRING_VALUE);
		
		UnitValue value = (UnitValue) temp.getValue();
		Long longVal = (Long) value.getNumberValue();
		String strUnit = value.getUnit();
		UnitValue newVal = new UnitValue(longVal, strUnit);
		
		assertEquals(newVal, value);
		//assertEquals(STRING_VALUE, temp.getValue());
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Temp#Temp()}.
	 */
	@Test
	public final void testNoArgsConstrcutedTempTest() {
		Temp temp = new Temp(null, null, null, STRING_VALUE, null);
		temp.setPatientId(DEFAULT_PATIENT_ID);
		
		assertNotNull(temp);
		assertEquals(DEFAULT_PATIENT_ID, temp.getPatientId());
		assertNull(temp.getId());
		assertNull(temp.getDate());
		assertNotNull(temp.getValue());
		assertNull(temp.getClinicId());
	}

	/**
	 * Test method for {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Temp#Temp(java.lang.String, java.lang.String, java.time.LocalDateTime, java.lang.Object, java.lang.String)}.
	 */
	@Test
	public final void testTempStringStringLocalDateTimeObjectString() {
		Temp temp= new Temp(DEFAULT_PATIENT_ID, DEFAULT_ID, DEFAULT_DATE, LONG_VALUE, DEFAULT_CLINIC_ID);
		temp.setPatientId(DEFAULT_PATIENT_ID);
		
		UnitValue value = (UnitValue) temp.getValue();
		Long longVal = (Long) value.getNumberValue();
		String strUnit = value.getUnit();
		UnitValue newVal = new UnitValue(longVal, strUnit);
		
		//assertEquals(LONG_VALUE, temp.getValue());
		assertEquals(newVal, value);//for unit value
		assertEquals(newVal, value);	
		assertNotNull(temp);
		assertEquals(DEFAULT_PATIENT_ID, temp.getPatientId());
		assertEquals(DEFAULT_ID,  temp.getId());
		assertEquals(DEFAULT_DATE, temp.getDate());
			
	
		assertEquals(DEFAULT_CLINIC_ID, temp.getClinicId());
	}

}
