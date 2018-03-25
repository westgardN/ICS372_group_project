package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;

import edu.metrostate.ics372.thatgroup.clinicaltrial.JsonReadings.JsonReading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import javafx.scene.Node;
import net.bytebuddy.dynamic.scaffold.MethodGraph.NodeList;

public class XMLReadings {
	
	private List<XMLReading> patient_readings;
	
	
	public parseXML(string fileName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	try {	
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fileName);
		NodeList clinicList = doc.getElementsbyTagName("Clinic_ID");
		for(int i = 0; i<clinicList.getLenth();i++) {
			Node c = clinicList.items(i);
			if(c.getNodeType()==Node.ELEMENT_NODE) {
				Element clinic = (Element) c;
				String id = clinic.getAttribute("id");
				NodeList readingList = clinic.getChildNodes();
				for(int j =0; j<readingList.getlength(); j++) {
					Node n = readingList.items(j);
					if(n.getNodeType() ==Node.ELEMENT_NODE) {
						Element reading = (Element) r;
						 
					}
					
				}
				
				
				
				
			}
		}
	}catch (ParserConfigurationException e) {
		e.printStackTrace();
	}catch (SAXException e) {
		e.printStackTrace();
	}
	
	}
}
