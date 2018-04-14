/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.util.HashMap;
import java.util.Map;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The <code>TrialManager</code> is responsible for returning a Singleton
 * instance of a this class. When the <code>TrialManager</code> instance is
 * instantiated, a new instance of ClinicalTrialCatalog is created, associated
 * with this <code>TrialManager</code> and can accessed via the
 * <code>TrialManager.getTrialCatalog()</code> method.
 * 
 * @author That Group
 */
public class TrialManager {
	private Map<Trial, TrialCatalog> catalogs;

	private TrialManager() {
		catalogs = new HashMap<Trial, TrialCatalog>();
	}

	/**
	 * Returns a new Singleton instance of <code>TrialManager</code>.
	 * 
	 * @return a new instance of <code>TrialManager</code>
	 */
	public static TrialManager getInstance() {
		return TrialManagerHolder.INSTANCE;
	}

	private static class TrialManagerHolder {

		private static final TrialManager INSTANCE = new TrialManager();
	}

	/**
	 * Gets the instance of the <code>TrialCatalog</code> that is associated with
	 * this <code>TrialManager</code> instance. If a catalog hasn't been initialized
	 * it will be initialized. If a catalog is already initialized an exception is
	 * thrown.
	 * 
	 * @param trial the trial to get an instance of.
	 * @return the <code>TrialCatalog</code> that is associated with
	 * this <code>TrialManager</code> instance.
	 * @throws TrialCatalogException indicates the catalog is already initialized.
	 */
	public TrialCatalog getTrialCatalog(Trial trial) throws TrialCatalogException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_TRIAL_INVALID);
		}
		
		TrialCatalog catalog = null;
		
		if (catalogs.containsKey(trial)) {
			catalog = catalogs.get(trial);
		} else {
			catalog = new ClinicalTrialCatalog();
			catalogs.put(trial, catalog);
		}
		
		initCatalog(catalog, trial);
		
		return catalog;
	}

	public boolean exists(Trial trial) throws TrialCatalogException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_TRIAL_INVALID);
		}
		
		AbstractClinicalTrialCatalog catalog = new ClinicalTrialCatalog();
		
		return catalog.catalogExists(trial);
	}
	
	public boolean remove(Trial trial) throws TrialCatalogException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_TRIAL_INVALID);
		}
		
		boolean answer = true;
		
		if (exists(trial)) {
			AbstractClinicalTrialCatalog catalog = new ClinicalTrialCatalog();
			
			answer = catalog.removeCatalog(trial);
		}
		
		return answer;
	}
	
	public void uninitialize() {
		catalogs.clear();
	}
	
	private void initCatalog(TrialCatalog catalog, Trial trial) throws TrialCatalogException {
		if (!catalog.isInit()) {
			if (!catalog.init(trial)) {
				throw new TrialCatalogException(Strings.ERR_CATALOG_INIT);
			}
		} else {
			throw new TrialCatalogException(Strings.ERR_CATALOG_ALREADY_INIT);
		}
	}
}
