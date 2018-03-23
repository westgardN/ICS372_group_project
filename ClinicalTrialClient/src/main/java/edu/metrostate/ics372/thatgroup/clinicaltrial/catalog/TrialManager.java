/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;

/**
*
* @author That Group
*/
public class TrialManager {
   private TrialCatalog catalog;
   
   private TrialManager() {
	   catalog = new ClinicalTrialCatalog();
   }
   
   public static TrialManager getInstance() {
       return TrialManagerHolder.INSTANCE;
   }
   
   private static class TrialManagerHolder {

       private static final TrialManager INSTANCE = new TrialManager();
   }
   
   public TrialCatalog getTrialCatalog(Trial trial) throws TrialCatalogException {
	   if (!catalog.isInit()) {		   
		   if (!catalog.init(trial)) {
			   throw new TrialCatalogException("Unable to initialize the catalog");
		   }
	   } else {
		   throw new TrialCatalogException("Catalog already initialized.");
	   }
	   
	   return catalog;
   }
}
