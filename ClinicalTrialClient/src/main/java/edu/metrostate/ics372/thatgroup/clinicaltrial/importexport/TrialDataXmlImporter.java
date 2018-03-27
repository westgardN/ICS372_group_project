/**
 * File: TrialDataXmlImporter.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.UnitValue;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The TrialDataJsonImportExporter is used for importing
 * trial data from an XML file. It includes a method to read
 * from an XML file.
 * 
 * @author That Group
 *
 */
public class TrialDataXmlImporter extends DefaultHandler implements TrialDataImporter {
	private static final String LOG_INVALID_READING_DATE = " invalid reading date.";
	private static final String LOG_READING_ID = "Reading ID: ";
	private static final String XML_DATE = "Date";
	private static final String XML_PATIENT = "Patient";
	private static final String XML_UNIT = "unit";
	private static final String XML_VALUE = "Value";
	private static final String XML_TYPE = "type";
	private static final String XML_READING = "Reading";
	private static final String XML_ID = "id";
	private static final String XML_CLINIC = "Clinic";
	private static final String XML_READING_SET = "ReadingSet";
	private Trial trial;
	private Clinic clinic;
	private Reading reading;
	private UnitValue unitValue;
	private StringBuilder sb; // Will hold string data for the elements.
	private List<Reading> readings;
	private List<Patient> patients;
	private List<Clinic> clinics;
	
	/**
	 * Initializes a new TrialDataXmlImporter with no trial and empty lists of Reading, Clinic
	 * and Patient. 
	 */
	public TrialDataXmlImporter() {
		readings = new LinkedList<>();
		patients = new LinkedList<>();
		clinics = new LinkedList<>();
		
		trial = null;
	}
	
	//////////////////////////// Begin ContentHandler Interface
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (qName.equalsIgnoreCase(XML_READING_SET)) {
			clinic = null;
			reading = null;
			sb = null;
		} else if (qName.equalsIgnoreCase(XML_CLINIC)) {
			sb = new StringBuilder();
			clinic = new Clinic();
			clinic.setTrialId(trial.getId());
			int attsSize = atts != null ? atts.getLength() : 0;
			if (attsSize > 0) {
				clinic.setId(atts.getValue(Strings.EMPTY, XML_ID));
			}
		} else if (qName.equalsIgnoreCase(XML_READING)) {
			int attsSize = atts != null ? atts.getLength() : 0;
			if (attsSize > 0) {
				String id = atts.getValue(Strings.EMPTY, XML_ID);
				String type = atts.getValue(Strings.EMPTY, XML_TYPE);

				if (id != null && type != null) {
					reading = ReadingFactory.getReading(type);
					reading.setId(id);
					if (clinic != null) {
						reading.setClinicId(clinic.getId());
					}
				}
			}
		} else if (qName.equalsIgnoreCase(XML_VALUE)) {
			sb = new StringBuilder();
			int attsSize = atts != null ? atts.getLength() : 0;
			if (attsSize > 0) {
				String unit = atts.getValue(Strings.EMPTY, XML_UNIT);
				
				if (unit != null) {
					unitValue = new UnitValue();
					unitValue.setUnit(unit);
				}
			} else {
				unitValue = null;
			}
		} else if (qName.equalsIgnoreCase(XML_PATIENT) || qName.equalsIgnoreCase(XML_DATE)) {
			sb = new StringBuilder();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(XML_READING_SET)) {
		} else if (qName.equalsIgnoreCase(XML_CLINIC)) {
			if (clinic != null) {
				clinic.setName(sb.toString().trim());
				if (!clinics.contains(clinic)) {
					clinics.add(clinic);
				}
			}
		} else if (qName.equalsIgnoreCase(XML_PATIENT)) {
			String patientId = sb.toString().trim();
			
			if (reading != null && patientId != null) {
				reading.setPatientId(patientId);
				Patient p = new Patient(patientId, trial != null ? trial.getId() : null, null, null);
				if (!patients.contains(p)) {
					patients.add(p);
				}
			}
		} else if (qName.equalsIgnoreCase(XML_DATE)) {
			if (reading != null) {
				try {
					String strLongDate = sb.toString().trim();
					
					if (strLongDate != null) {
						long longDate = Long.parseLong(strLongDate);
						
						LocalDateTime date = Instant.ofEpochMilli(longDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
						
						reading.setDate(date);
					} else {
						reading.setDate(LocalDateTime.now());
					}
				} catch (NumberFormatException ex) {
					reading.setDate(LocalDateTime.now());
					Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.INFO, LOG_READING_ID + reading.getId() + LOG_INVALID_READING_DATE);
				}
			}
		} else if (qName.equalsIgnoreCase(XML_VALUE)) {
			if (reading != null) {
				String unit = unitValue != null ? unitValue.getUnit() : Strings.EMPTY;
				
				if (unit == Strings.EMPTY) {
					reading.setValue(sb.toString().trim());
				} else {
					reading.setValue(new UnitValue(sb.toString().trim() + UnitValue.DELIM + unit));
				}
				
			}
		} else if (qName.equalsIgnoreCase(XML_READING)) {
			if (reading.getDate() == null) {
				reading.setDate(LocalDateTime.now());
			}
			if (!readings.contains(reading)) {
				readings.add(reading);
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (sb != null) {
			String string = new String(ch, start, length);
			sb.append(string.trim());
		}
	}

	////////////////////////////// End ContentHandler Interface ////////////////////////////
	///////////////////////////// Begin ErrorHandler Interface	////////////////////////////
	@Override
	public void warning(SAXParseException ex) throws SAXException {
		Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
	}

	@Override
	public void error(SAXParseException ex) throws SAXException {
		Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
	}

	@Override
	public void fatalError(SAXParseException ex) throws SAXException {
		Logger.getLogger(TrialDataXmlImporter.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
	}

	///////////////////////////// End ErrorHandler Interface ///////////////////////////
	///////////////////////////// Begin TrialDataImporter Interface ////////////////////

	@Override
	public List<Reading> getReadings() {
		return readings;
	}

	@Override
	public List<Patient> getPatients() {
		return patients;
	}

	@Override
	public List<Clinic> getClinics() {
		return clinics;
	}

	/**
	 * Reads the specified XML file and returns true if the import was successful.
	 * If the import was successful, then the lists for the imported objects will be
	 * valid after this method returns.
	 * 
	 * @param trial the trial that the data is being imported into. Cannot be null.
	 * @param is the ImportStream to read the data from.
	 * 
	 * @return true if the import was successful; otherwise false is returned.
	 * 
	 * @throws TrialException indicates an error occurred while operating on the file. 
	 */
	@Override
	public boolean read(Trial trial, InputStream is) throws TrialException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty() || is == null) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_BAD_TRIAL_AND_IS);
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
			this.trial = trial;
			reader.parse(new InputSource(is));
			answer = !readings.isEmpty();
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_BAD_XML, ex);
		}

		return answer;
	}

}