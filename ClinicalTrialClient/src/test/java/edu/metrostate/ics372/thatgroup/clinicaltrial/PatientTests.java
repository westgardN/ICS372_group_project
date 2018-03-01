package edu.metrostate.ics372.thatgroup.clinicaltrial;

import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.mockito.Mockito;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.ClinicalPatient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.PatientFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.views.StringResource;

public class PatientTests {
	Patient patientSpy = Mockito.spy(Patient.class);
	Class<?>[] argTypes = new Class[] { String.class, String.class, Set.class, LocalDate.class, LocalDate.class };
	Patient clinicalPatient = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
	private static final String PATIENT_ID = "foo";
	private static final String TRIAL_ID = "bar";
	private static final String OTHER_PATIENT_ID = "abc";
	private static final String OTHER_TRIAL_ID = "baz";

	@Test
	public void testConstruction() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		assertTrue(isEmptyPatient(patientSpy));
		Constructor<?> constructor = patientSpy.getClass().getConstructor(argTypes);
		Set<Reading> mySet = null;
		Patient p1 = (Patient) constructor.newInstance(PATIENT_ID, TRIAL_ID, mySet, LocalDate.now(), LocalDate.now().plusDays(24));
		assertTrue(wasConstructedWithParams(p1));
		p1 = (Patient) constructor.newInstance(PATIENT_ID, TRIAL_ID, new HashSet<>(), LocalDate.now(),
				LocalDate.now().plusDays(24));
		assertTrue(wasConstructedWithParams(p1));
		assertTrue(isEmptyPatient(clinicalPatient));
		assertTrue(wasConstructedWithParams(
				new ClinicalPatient(PATIENT_ID, mySet, LocalDate.now(), LocalDate.now().plusDays(24))));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateIncorrectPatientType() {
		PatientFactory.getPatient("patient");
	}
	
	@Test
	public void testGetClinicalPatient() {
		assertTrue(new PatientFactory() instanceof PatientFactory);
		assertTrue(PatientFactory.getPatient("clinical") instanceof ClinicalPatient);
		assertTrue(PatientFactory.getPatient("CLINICAL") instanceof ClinicalPatient);
		assertTrue(PatientFactory.getPatient("ClInICal") instanceof ClinicalPatient);
	}
	
	@Test
	public void testGetPatientType() {
		Patient p1;
		p1 = PatientFactory.getPatient("clinical");
		assertEquals("clinical", PatientFactory.getPatientType(p1));
		assertTrue(p1 instanceof ClinicalPatient);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testgetIncorrectPatientType() {
		PatientFactory.getPatientType(patientSpy);
	}

	@Test
	public void testAddPropertyChangeSupport() throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException {
		Field pcs = Patient.class.getDeclaredField("pcs");
		pcs.setAccessible(true);
		pcs.set(patientSpy, null);
		Method getPcs = Patient.class.getDeclaredMethod("getPcs");
		getPcs.setAccessible(true);
		assertNotNull(getPcs);
		patientSpy.addPropertyChangeListener(null);
	}

	@Test
	public void testSetID() {
		patientSpy.setId(PATIENT_ID);
		assertEquals(PATIENT_ID, patientSpy.getId());
		patientSpy.setId(PATIENT_ID);
		assertEquals(PATIENT_ID, patientSpy.getId());
		patientSpy.setId(OTHER_PATIENT_ID);
		assertEquals(OTHER_PATIENT_ID, patientSpy.getId());
	}

	@Test
	public void testSetTrialId() {
		String sameId = "bar";
		clinicalPatient.setTrialId(TRIAL_ID);
		assertEquals(sameId, clinicalPatient.getTrialId());
		clinicalPatient.setTrialId(sameId);
		assertEquals(TRIAL_ID, clinicalPatient.getTrialId());
	}

	@Test
	public void testSetTrialStartDate() {
		patientSpy.setTrialStartDate(LocalDate.now());
		assertEquals(patientSpy.getTrialStartDate(), LocalDate.now());
		patientSpy.setTrialStartDate(LocalDate.now());
		assertEquals(patientSpy.getTrialStartDate(), LocalDate.now());
		patientSpy.setTrialStartDate(LocalDate.now().plusDays(24));
		assertEquals(patientSpy.getTrialStartDate(), LocalDate.now().plusDays(24));
	}

	@Test
	public void testSetTrialEndDate() {
		LocalDate now = LocalDate.now();
		LocalDate now1 = LocalDate.now();
		clinicalPatient.setTrialEndDate(now);
		clinicalPatient.setTrialEndDate(now1);
		assertEquals(now, clinicalPatient.getTrialEndDate());
	}

	@Test
	public void testAddreadingGetJournalSize() {
		Reading reading;
		int i = 0;
		new ReadingFactory();
		for (String type : ReadingFactory.getReadingTypes()) {
			reading = ReadingFactory.getReading(type);
			reading.setId(String.format("%d", i++));
			assertTrue(clinicalPatient.addReading(reading));
		}
		assertEquals(4, clinicalPatient.getJournalSize());
		reading = ReadingFactory.getReading("weight");
		reading.setId("0");
		assertFalse(clinicalPatient.addReading(reading));

		clinicalPatient.setTrialEndDate(LocalDate.now());
		reading = ReadingFactory.getReading("steps");
		reading.setId("foo");
		reading.setDate(LocalDateTime.now());
		assertTrue(clinicalPatient.addReading(reading));
	}

	@Test
	public void testRemoveAndContainsReading() {
		Reading r1 = ReadingFactory.getReading("blood_press");
		LocalDateTime now = LocalDateTime.now();
		r1.setId(OTHER_PATIENT_ID);
		r1.setValue("120/90");
		r1.setDate(now);
		assertTrue(clinicalPatient.addReading(r1));
		assertEquals(1, clinicalPatient.getJournalSize());

		Reading r2 = ReadingFactory.getReading("blood_press");
		r2.setId(OTHER_PATIENT_ID);
		r2.setValue("120/90");
		r2.setDate(now);
		assertTrue(clinicalPatient.containsReading(r2));
		assertFalse(clinicalPatient.containsReading(null));
		clinicalPatient.removeReading(r2);
		assertEquals(0, clinicalPatient.getJournalSize());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullReading() {
		clinicalPatient.addReading(null);
	}

	@Test
	public void testRemoveAllReadings() {
		clinicalPatient.removeAllReadings();
		Reading reading;
		int i = 0;
		new ReadingFactory();
		for (String type : ReadingFactory.getReadingTypes()) {
			reading = ReadingFactory.getReading(type);
			reading.setId(String.format("%d", i++));
			assertTrue(clinicalPatient.addReading(reading));
		}
		assertEquals(4, clinicalPatient.getJournalSize());
		clinicalPatient.removeAllReadings();
		assertEquals(0, clinicalPatient.getJournalSize());
	}

	@Test
	public void testHashCode() {
		Patient p1 = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
		p1.setId(PATIENT_ID);
		p1.setTrialId(TRIAL_ID);
		int p1HashCode = p1.hashCode();

		Patient p2 = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
		p1.setId(OTHER_PATIENT_ID);
		p1.setTrialId(OTHER_TRIAL_ID);
		int p2HashCode = p2.hashCode();
		assertFalse(p1HashCode == p2HashCode);
	}

	@Test
	public void testEquals() {

		Patient thisPatient = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
		Patient thatPatient = thisPatient;
		assertTrue(thisPatient.equals(thatPatient));

		thatPatient = null;
		assertFalse(thisPatient.equals(thatPatient));

		assertFalse(thisPatient.equals(new Object()));

		thisPatient.setId(null);
		thatPatient = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
		thatPatient.setId(PATIENT_ID);
		assertFalse(thisPatient.equals(thatPatient));

		thisPatient.setId(PATIENT_ID);
		thatPatient.setId(OTHER_PATIENT_ID);
		assertFalse(thisPatient.equals(thatPatient));

		thatPatient.setId(null);
		assertFalse(thisPatient.equals(thatPatient));

		thatPatient.setId(PATIENT_ID);
		thisPatient.setTrialId(null);
		thatPatient.setTrialId(TRIAL_ID);
		assertFalse(thisPatient.equals(thatPatient));

		thisPatient.setTrialId(OTHER_TRIAL_ID);
		assertFalse(thisPatient.equals(thatPatient));

		thisPatient.setTrialId(TRIAL_ID);
		thatPatient.setTrialId(null);
		assertFalse(thisPatient.equals(thatPatient));

		thisPatient.setTrialId(TRIAL_ID);
		thatPatient.setTrialId(TRIAL_ID);
		assertTrue(thisPatient.equals(thatPatient));
	}

	@Test
	public void testToString()
			throws NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Constructor<?> constructor = patientSpy.getClass().getConstructor(argTypes);
		Set<Reading> readings = new HashSet<>();
		Reading reading;
		int i = 0;
		new ReadingFactory();
		for (String type : ReadingFactory.getReadingTypes()) {
			reading = ReadingFactory.getReading(type);
			reading.setId(String.format("%d", i++));
			readings.add(reading);
		}
		Patient p1 = (Patient) constructor.newInstance(PATIENT_ID, TRIAL_ID, readings, LocalDate.now(),
				LocalDate.now().plusDays(24));
		assertEquals(createReadingString(p1), p1.toString());
		for (int j = 0; j < 3; j++) {
			Reading r = p1.getJournal().iterator().next();
			p1.removeReading(r);
		}
		assertEquals(createReadingString(p1), p1.toString());
		p1.setTrialEndDate(null);
		assertEquals(createReadingString(p1), p1.toString());
	}

	@Test
	public void testClinicalPatientToString() {
		Patient p1 = PatientFactory.getPatient(PatientFactory.PATIENT_CLINICAL);
		new ReadingFactory();
		int i = 0;
		for (String type : ReadingFactory.getReadingTypes()) {
			Reading reading = ReadingFactory.getReading(type);
			p1.setId(String.format("%d", i++));
			p1.addReading(reading);
		}
		p1.setTrialStartDate(LocalDate.now());
		assertEquals(createReadingString(p1), p1.toString());
		for (int j = 0; j < 3; j++) {
			Reading r = p1.getJournal().iterator().next();
			p1.removeReading(r);
		}
		assertEquals(createReadingString(p1), p1.toString());
		p1.setTrialEndDate(LocalDate.now());
		assertEquals(createReadingString(p1), p1.toString());
		p1.setTrialStartDate(null);
		assertEquals(createReadingString(p1), p1.toString());
	}

	private String createReadingString(Patient patient) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		String result = "";
		if (patient instanceof Patient) {
			result = String.format("Patient %s (Trial %s) (%s%s) has %d reading%s", patient.getId(),
					patient.getTrialId(),
					patient.getTrialStartDate() != null ? patient.getTrialStartDate().format(formatter)
							: StringResource.EMPTY.get(),
					patient.getTrialEndDate() != null ? " - " + patient.getTrialEndDate().format(formatter)
							: StringResource.EMPTY.get(),
					patient.getJournalSize(), patient.getJournalSize() != 1 ? "s" : "");
		}
		if (patient instanceof ClinicalPatient) {
			result = String.format("%s%s", patient.getId(), patient.getTrialStartDate() != null
					? String.format("%s%d%s", patient.getTrialEndDate() == null
							? String.format(": active %s has ", patient.getTrialStartDate().format(formatter))
							: String.format(": inactive (%s - %s) has ", patient.getTrialStartDate().format(formatter),
									patient.getTrialEndDate().format(formatter)),
							patient.getJournalSize(), String.format(" reading%s", patient.getJournalSize() != 1 ? "s" : StringResource.EMPTY.get()))
					: " has not started the trial");
		}
		System.out.println(result);
		return result;
	}

	private boolean isEmptyPatient(Patient patient) {
		return patient != null && patient.getId() == null && patient.getJournal().isEmpty()
				&& patient.getTrialStartDate() == null && patient.getTrialEndDate() == null;
	}

	private boolean wasConstructedWithParams(Patient patient) {
		return !isEmptyPatient(patient) && patient.getId().equals(PATIENT_ID) && patient.getTrialId() == null ? true
				: patient.getTrialId().equals(TRIAL_ID) && HashSet.class.equals(patient.getJournal().getClass())
						&& LocalDate.now().equals(patient.getTrialStartDate())
						&& LocalDate.now().plusDays(24).equals(patient.getTrialEndDate());
	}
}
