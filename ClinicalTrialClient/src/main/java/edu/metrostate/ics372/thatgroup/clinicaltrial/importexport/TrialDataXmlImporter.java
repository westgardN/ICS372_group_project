package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import edu.metrostate.ics372.thatgroup.clinicaltrial.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

/**
 * A private class used for processing the layouts.xml file.
 */
public class TrialDataXmlImporter extends DefaultHandler implements TrialDataImporter {
	private Trial trial;
	private Clinic clinic;
	private Reading reading;
	private StringBuilder sb; // Will hold string data to be processed and added to our layout.
	private List<Reading> readings;
	private List<Patient> patients;
	private List<Clinic> clinics;
	/*
	 * constructor
	 */
	public TrialDataXmlImporter() {
		readings = new LinkedList<>();
		patients = new LinkedList<>();
		clinics = new LinkedList<>();
		
		trial = null;
	}

	public List<Reading> getReadings() {
		return readings;
	}


	public List<Patient> getPatients() {
		return patients;
	}

	public List<Clinic> getClinics() {
		return clinics;
	}

	//////////////////////////// Begin ContentHandler Interface
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (qName.equalsIgnoreCase("ReadingSet")) {
			clinic = null;
			reading = null;
			sb = null;
		} else if (qName.equalsIgnoreCase("Clinic")) {
			sb = new StringBuilder();
			clinic = new Clinic();
			clinic.setTrialId(trial.getId());
			int attsSize = atts != null ? atts.getLength() : 0;
			if (attsSize > 0) {
				clinic.setId(atts.getValue("", "id"));
			}
		} else if (qName.equalsIgnoreCase("Reading")) {
			int attsSize = atts != null ? atts.getLength() : 0;
			if (attsSize > 0) {
				String id = atts.getValue("", "id");
				String type = atts.getValue("", "type");

				if (id != null && type != null) {
					reading = ReadingFactory.getReading(type);
					reading.setId(id);
					if (clinic != null) {
						reading.setClinicId(clinic.getId());
					}
				}
			}
		} else if (qName.equalsIgnoreCase("Value")) {
			sb = new StringBuilder();
			int attsSize = atts != null ? atts.getLength() : 0;
			if (attsSize > 0) {
				String unit = atts.getValue("", "unit");
				Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.SEVERE, null, "Ignoring the value's unit: " + unit);
			}
		} else if (qName.equalsIgnoreCase("Patient")) {
			sb = new StringBuilder();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("ReadingSet")) {
		} else if (qName.equalsIgnoreCase("Clinc")) {
			if (clinic != null) {
				clinic.setName(sb.toString().trim());
				clinics.add(clinic);
			}
		} else if (qName.equalsIgnoreCase("Patient")) {
			String patientId = sb.toString().trim();
			
			if (reading != null && patientId != null) {
				reading.setPatientId(patientId);
				patients.add(new Patient(patientId, trial != null ? trial.getId() : null, null, null));
			}
		} else if (qName.equalsIgnoreCase("Value")) {
			if (reading != null) {
				reading.setValue(sb.toString().trim());
			}
		} else if (qName.equalsIgnoreCase("Reading")) {
			readings.add(reading);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (sb != null) {
			String string = new String(ch, start, length);
			sb.append(string.trim());
		}
	}

	////////////////////////////// End ContentHandler Interface
	////////////////////////////// ////////////////////////////////////
	///////////////////////////// Begin ErrorHandler Interface
	////////////////////////////// ///////////////////////////////////
	@Override
	public void warning(SAXParseException ex) throws SAXException {
		Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.SEVERE, null, ex);
	}

	@Override
	public void error(SAXParseException ex) throws SAXException {
		Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.SEVERE, null, ex);
	}

	@Override
	public void fatalError(SAXParseException ex) throws SAXException {
		Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.SEVERE, null, ex);
	}

	///////////////////////////// End ErrorHandler Interface
	///////////////////////////// ///////////////////////////////////
	///////////////////////////// Begin TrialDataImporter Interface
	///////////////////////////// ////////////////////////////
	@Override
	public boolean read(Trial trial, InputStream is) throws TrialException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty() || is == null) {
			throw new TrialException("trial cannot be null and must have a valid id. is must be a valid InputSteam");
		}
		
		boolean answer = false;

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);

			SAXParser parser = factory.newSAXParser();

			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(this);
			reader.setErrorHandler(this);
			reader.parse(new InputSource(is));
			// We have a well-formed XML document and the layouts have been loaded;
			// otherwise the parser would have thrown an exception. So add the
			// layouts to our map!
			answer = !readings.isEmpty();
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			throw new TrialException("Unable to process XML import file.", ex);
		}

		return answer;

	}

	///////////////////////////// End TrialDataImporter Interface
	///////////////////////////// //////////////////////////////
}