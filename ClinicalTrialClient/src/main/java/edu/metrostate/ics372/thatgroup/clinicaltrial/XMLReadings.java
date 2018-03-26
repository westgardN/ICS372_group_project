package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import javafx.scene.Node;
/**
 * 
 * @author That Group public class to parse XML readings.
 *
 */

public class XMLReadings {
	public static List<Reading> read(String filePath) throws IOException, TrialException {
	}
	//create readings
	private List<Reading> readingsList;
	/*
	 * @return boolean.
	 */
	public List<Reading> parseXML(String filePath) {
		boolean answer = false;
        
        try {
            File file = new File(filePath);
            
            // Validate that the provided file is a well-formed XML document and then load it.            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);

            SAXParser parser = factory.newSAXParser();

            XMLReader reader = parser.getXMLReader();
            ParseContentHandler handler = new ParseContentHandler();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new XMLErrorHandler());
            reader.parse(new InputSource(filePath));
            // We have a well-formed XML document and the layouts have been loaded;
            // otherwise the parser would have thrown an exception. So add the
            // layouts to our map!
            readingsList.putAll(handler.getReadings());
            answer = true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new TrialException("Unable to process XML import file.", ex);
        }
        
        return readingsList;
    }

	
}
