package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.time.LocalDateTime;
import java.util.List;
import java.util.jar.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

/**
 * A private class used for processing the layouts.xml file.
 */
class ParseContentHandler extends DefaultHandler {
    private StringBuilder sb; // Will hold string data to be processed and added to our layout.
	private String patientId;
	private String readingId;
	private LocalDateTime date;
	private String clinicId;
	private Object value;
	private List<Reading> readings;
    /*
     * constructor
     */
	public ParseContentHandler() {
		
	}
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (qName.equalsIgnoreCase("ReadingSet")) {
            //is start of document--necessary?
        } else if (qName.equalsIgnoreCase("Clinic id")) {
            //verify new
        	Clinic clinic = new Clinic();
        } else if (qName.equalsIgnoreCase("Reading id")) {
            //verify new
        	Reading rdg = new Reading();
        } else if (qName.equalsIgnoreCase("Patient id")) {
            //verify new 
        	Patient pt = new Patient();
        } else if (qName.equalsIgnoreCase("Type") || qName.equalsIgnoreCase("Value unit")
                || qName.equalsIgnoreCase("value") || qName.equalsIgnoreCase("date")
                || qName.equalsIgnoreCase("time")) {
            sb = new StringBuilder();
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Reading ID")) {
            // add it to the list
        	//readings.add();--how are we updating the things? Do I have to find the reading first? Yes. Deffo. 
        	//All Reading references below must be instances of reading and not reading itself.
        	//can below be nested?
        } else if (qName.equalsIgnoreCase("Patient ID")) {
            try {
               Reading.setPatientId(sb.toString().trim());
            } catch (TrialException ex) {
            	//handle exception--incorrect exception
            }
        } else if (qName.equalsIgnoreCase("Clinic ID")) {
            try {
                Reading.setClinicId(sb.toString().trim());
             } catch (TrialException ex) {
             	//handle exception--incorrect exception
             }
        } else if (qName.equalsIgnoreCase("Type")) {
       //     try {
               // Reading.setClinicId(sb.toString().trim());
        //     } catch (TrialException ex) {
             	//handle exception--incorrect exception
      //       }
        } else if (qName.equalsIgnoreCase("Value Unit")) {
            try {
                Reading.setPatientId(sb.toString().trim());
             } catch (TrialException ex) {
             	//handle exception--incorrect exception
             }
        } else if (qName.equalsIgnoreCase("Value")) {
            try {
                Reading.setValue(Integer.parseInt(sb.toString().trim()));
             } catch (NumberFormatException ex) {
             	//handle exception--incorrect exception
             }
            
            //elif for time? Necessary??
        
     
    }
 
     public List<Reading> getReadings() {
		return readings;
	}
}