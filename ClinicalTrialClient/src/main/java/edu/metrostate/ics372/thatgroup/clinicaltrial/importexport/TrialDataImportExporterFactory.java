/**
 * File: TrialDataImportExporterFactory.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

/**
 * @author That Group
 *
 */
public class TrialDataImportExporterFactory {
	public static final String XML = ".xml";
	public static final String JSON = ".json";
	
	/**
	 * @throws TrialException 
	 * 
	 */
	public static TrialDataExporter getTrialExporter(String filename) throws TrialException {
		TrialDataExporter answer = null;
		
		if (filename != null) {
	        String ext = "";
	        
	        int extIndex = filename.lastIndexOf('.');
	        
	        if (extIndex >= 0) {
	            ext = filename.substring(extIndex).toLowerCase();
	        }
	        
	        switch(ext) {
	        	case JSON:
	        		answer = new TrialDataJsonImportExporter();
	        		break;
        		default:
        			throw new TrialException(ext + " is not a supported export type.");
	        }

		} else {
			throw new TrialException("filename cannot be null.");
		}
		
		return answer;
	}

	/**
	 * @throws TrialException 
	 * 
	 */
	public static TrialDataImporter getTrialImporter(String filename) throws TrialException {
		TrialDataImporter answer = null;
		
		if (filename != null) {
	        String ext = "";
	        
	        int extIndex = filename.lastIndexOf('.');
	        
	        if (extIndex >= 0) {
	            ext = filename.substring(extIndex).toLowerCase();
	        }
	        
	        switch(ext) {
	        	case JSON:
	        		answer = new TrialDataJsonImportExporter();
	        		break;
	        	case XML:
	        		answer = new TrialDataXmlImporter();
	        		break;
        		default:
        			throw new TrialException(ext + " is not a supported import type.");
	        }

		} else {
			throw new TrialException("filename cannot be null.");
		}
		
		return answer;
	}

}
