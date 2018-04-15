package edu.metrostate.ics372.thatgroup.clinicaltrial.beans;

import static org.junit.Assert.*;

import org.junit.Test;

public class PatientStatusTest {

		private final String ID_A = "id 1";
		private final String ID_B = "id 2";
		private final String STATUS_INACTIVE = "INACTIVE";
		private final String STATUS_ACTIVE = "ACTIVE";

		
		/**
		 * Test method for
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#hashCode()}
		 * and
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#equals(java.lang.Object)}.
		 */
		@Test
		public final void twoStatusThatAreNotEqualShouldNotBeConsideredEqual() {
			PatientStatus status1 = new PatientStatus(ID_A, STATUS_ACTIVE);
			PatientStatus status2 = new PatientStatus(ID_B, STATUS_ACTIVE);
			
			assertNotEquals(status1, status2);
		}
		
		/**
		 * Test method for
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#hashCode()}
		 * and
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#equals(java.lang.Object)}.
		 */
		@Test
		public final void twoStatusThatAreEqualNotHaveEqualHashCodes() {
			
			PatientStatus status1 = new PatientStatus(ID_A, STATUS_ACTIVE);
			PatientStatus status2 = new PatientStatus(ID_A, STATUS_ACTIVE);
			
			assertEquals(status1,status2);
			assertEquals(status1.hashCode(), status2.hashCode());
		}
		
		/**
		 * Test method for
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus()}.
		 */
		@Test
		public final void defaultConstructedShouldBeNotNull() {
			
			PatientStatus status1 = new PatientStatus();
			
			assertNotNull(status1);
			assertNull(status1.getId());
			assertNull(status1.getDisplayStatus());
		}
		
		/**
		 * Test method for
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#getId()()}
		 * and
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#getId()()}
		 */
		@Test
		public final void testGetId() {
			PatientStatus status1 = new PatientStatus();
			
			status1.setId(ID_A);
			assertEquals(ID_A, status1.getId());
			
			status1.setId(ID_B);
			assertEquals(ID_B, status1.getId());
		}
		
		/**
		 * Test method for
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#setDisplayStatus}.
		 * and
		 * {@link edu.metrostate.ics372.thatgroup.clinicaltrial.beans.PatientStatus#getDisplayStatus}.
		 */
		@Test
		public final void testPatientId() {
			PatientStatus status1 = new PatientStatus();
			
			status1.setDisplayStatus(STATUS_INACTIVE);
			assertEquals(STATUS_INACTIVE, status1.getDisplayStatus());
			
			status1.setDisplayStatus(STATUS_ACTIVE);
			assertEquals(STATUS_ACTIVE, status1.getDisplayStatus());
		}
}
