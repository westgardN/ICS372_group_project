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
	protected Map<Trial, TrialCatalog> catalogs;
	protected String storagePath;

	private TrialManager() {
		catalogs = new HashMap<Trial, TrialCatalog>();
		storagePath = null;
	}

	/**
	 * Returns a new Singleton instance of <code>TrialManager</code>.
	 * 
	 * @return a new instance of <code>TrialManager</code>
	 */
	public static TrialManager getInstance() {
		return getInstance(ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath());
	}
	
	/**
	 * Returns a new Singleton instance of <code>TrialManager</code>.
	 * 
	 * @return a new instance of <code>TrialManager</code>
	 */
	public static TrialManager getInstance(String storagePath) {
		TrialManager tm = TrialManagerHolder.INSTANCE;
		
		if (storagePath != null) {
			tm.storagePath = storagePath;
		}
		
		return tm;
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
			catalog = new ClinicalTrialCatalog(trial, storagePath);
			catalogs.put(trial, catalog);
		}
		
		initCatalog(catalog);
		
		return catalog;
	}

	public boolean exists(Trial trial) throws TrialCatalogException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_TRIAL_INVALID);
		}
		
		ClinicalTrialCatalog catalog = new ClinicalTrialCatalog(trial, storagePath);
		
		return catalog.catalogExists();
	}
	
	public boolean remove(Trial trial) throws TrialCatalogException {
		if (trial == null || trial.getId() == null || trial.getId().trim().isEmpty()) {
			throw new TrialCatalogException(Strings.ERR_CATALOG_TRIAL_INVALID);
		}
		
		boolean answer = true;
		
		if (exists(trial)) {
			ClinicalTrialCatalog catalog = new ClinicalTrialCatalog(trial, storagePath);
			
			answer = catalog.removeCatalog();
		}
		
		return answer;
	}
	
	public void uninitialize() {
		catalogs.clear();
	}
	
	private void initCatalog(TrialCatalog catalog) throws TrialCatalogException {
		if (!catalog.isInit()) {
			if (!catalog.init()) {
				throw new TrialCatalogException(Strings.ERR_CATALOG_INIT);
			}
		} else {
			throw new TrialCatalogException(Strings.ERR_CATALOG_ALREADY_INIT);
		}
	}
}
