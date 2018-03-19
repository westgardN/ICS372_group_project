/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;

/**
*
* @author Gabriel
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
		   catalog.init(trial);
	   } else {
		   throw new IllegalStateException("Catalog already initialized.");
	   }
	   
	   return catalog;
   }
}
