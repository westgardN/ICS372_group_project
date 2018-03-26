package edu.metrostate.ics372.thatgroup.clinicaltrial;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;

class XMLErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException ex) throws SAXException {
            throw new TrialException("XML Warning:", ex);
        }

        @Override
        public void error(SAXParseException ex) throws SAXException {
        	throw new TrialException("XML Error:", ex);
        }

        @Override
        public void fatalError(SAXParseException ex) throws SAXException {
				throw new TrialException("XML Fatal Error:", ex);
		}
}
}

