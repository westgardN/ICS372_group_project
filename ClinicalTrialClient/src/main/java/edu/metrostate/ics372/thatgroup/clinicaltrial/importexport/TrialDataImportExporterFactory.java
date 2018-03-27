/**
 * File: TrialDataImportExporterFactory.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.importexport;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The TrialDataImportExporterFactory is used to take the guess work out of which
 * TrialDataImporter or TrialDataExporter to instantiate.
 * 
 * @author That Group
 *
 */
public class TrialDataImportExporterFactory {
	private static final char PERIOD = '.';

	/**
	 * The file extension for an XML file
	 */
	public static final String XML = ".xml";
	
	/**
	 * The file extension for a JSON file
	 */
	public static final String JSON = ".json";
	
	/**
	 * Returns a TrialDataExporter based on the supplied filename.
	 * 
	 * @return a TrialDataExporter based on the supplied filename.
	 * @throws TrialException Indicates that filename is null or
	 * an unsupported type.
	 */
	public static TrialDataExporter getTrialExporter(String filename) throws TrialException {
		TrialDataExporter answer = null;
		
		if (filename != null) {
	        String ext = Strings.EMPTY;
	        
	        int extIndex = filename.lastIndexOf(PERIOD);
	        
	        if (extIndex >= 0) {
	            ext = filename.substring(extIndex).toLowerCase();
	        }
	        
	        switch(ext) {
	        	case JSON:
	        		answer = new TrialDataJsonImportExporter();
	        		break;
        		default:
        			throw new TrialException(String.format(Strings.ERR_TRIAL_DATA_EXPORTER_UNSUPPORTED_MSG, ext));
	        }

		} else {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_EXPORTER_NULL_FILE);
		}
		
		return answer;
	}

	/**
	 * Returns a TrialDataImporter based on the supplied filename.
	 * 
	 * @return a TrialDataImporter based on the supplied filename.
	 * @throws TrialException Indicates that filename is null or
	 * an unsupported type.
	 */
	public static TrialDataImporter getTrialImporter(String filename) throws TrialException {
		TrialDataImporter answer = null;
		
		if (filename != null) {
	        String ext = Strings.EMPTY;
	        
	        int extIndex = filename.lastIndexOf(PERIOD);
	        
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
        			throw new TrialException(String.format(Strings.ERR_TRIAL_DATA_IMPORTER_UNSUPPORTED_MSG, ext));
	        }

		} else {
			throw new TrialException(Strings.ERR_TRIAL_DATA_IMPORTER_EXPORTER_NULL_FILE);
		}
		
		return answer;
	}

}
