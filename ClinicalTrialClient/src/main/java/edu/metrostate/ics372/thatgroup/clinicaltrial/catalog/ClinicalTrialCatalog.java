package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;

/**
 * The ClinicalTrialCatalog serves as the central database used to store all of
 * the information for a trial. This class provides CRUD data access to
 * Clinic(s), Reading(s), and Patient(s). That is you are able to insert, get,
 * update, and delete a Clinic, Reading, or Patient from the catalog. At
 * instantiation of this class, the <code>init()</code> method will check to see
 * if the catalog storage directory exists, if not it will be created and a new
 * default trial catalog will be created and initialized. If the catalog storage
 * directory does already exist, but the directory is void of any trial catalogs
 * or a default trial catalog, a new default trial catalog will be created and
 * initialized. Otherwise it will be assumed the directory already exists and
 * there is a default trial catalog present in the directory. The catalog
 * storage paths for Windows, Linux, and OS X are as follows:
 * 
 * <ul>
 * <li>Windows: Users\*user*\AppData\Roaming\That Group\Trial Catalogs
 * <li>Linux: $HOME\.local\share\That Group\Clinical Trial Catalogs
 * <li>MAC: Users\*user*\Library\Application Support\That Group\Clinical Trial
 * Catalogs
 * </ul>
 * 
 * @author That Group
 *
 */
public class ClinicalTrialCatalog extends AbstractClinicalTrialCatalog {
	protected boolean createTrialCatalog(Trial trial) throws TrialCatalogException {
		boolean answer = false;
		String catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		String trialName = trial.getId();
		String catalogFilePath = catalogStoragePath + trialName.concat(ClinicalTrialCatalogUtilIty.CATALOG_EXTENSION);

		if (!catalogExists(trial)) {
			if (ClinicalTrialCatalogUtilIty.writeAndInitializeCatalogFile(Paths.get(catalogFilePath).toFile(),
					trialName)) {
				answer = true;
			}
		} else {
			answer = true;
		}

		return answer;
	}

	public boolean catalogExists(Trial trial) {
		boolean answer = false;

		String trialName = trial.getId();
		String catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		String catalogFilePath = catalogStoragePath + trialName.concat(ClinicalTrialCatalogUtilIty.CATALOG_EXTENSION);

		if (Files.exists(Paths.get(catalogFilePath))) {
			answer = true;
		}
		
		return answer;
	}
	
	public boolean removeCatalog(Trial trial) {
		boolean answer = false;

		String trialName = trial.getId();
		String catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();
		String catalogFilePath = catalogStoragePath + trialName.concat(ClinicalTrialCatalogUtilIty.CATALOG_EXTENSION);

		Path path = Paths.get(catalogFilePath);
		
		if (Files.exists(path) && !Files.isDirectory(path)) {
			try {
				Files.delete(path);
				answer = true;
			} catch (IOException e) {
			}			
		}
		
		return answer;
	}
	
	protected Connection getConnection() throws SQLException {
		return ClinicalTrialCatalogUtilIty.getConnection(getActiveId());
	}


	///////////////////////// Begin Interface //////////////////////////////////////
	@Override
	public boolean init(Trial trial) throws TrialCatalogException {
		validateParam(trial);

		boolean answer = true;

		String catalogStoragePath = ClinicalTrialCatalogUtilIty.getEnvironmentSpecificStoragePath();

		// Create directory structure?
		if (!Files.exists(Paths.get(catalogStoragePath))) {
			if (!Paths.get(catalogStoragePath).toFile().mkdirs()) {
				answer = false;
			}
		} 
		
		// Directory structure in place?
		if (answer) {			
			// Create the database?
			if (!catalogExists(trial)) {
				answer = createTrialCatalog(trial);
			} 
			
			if (answer && ClinicalTrialCatalogUtilIty.databaseNeedsUpgrade(trial.getId())) {
				answer = ClinicalTrialCatalogUtilIty.firstMigrationCatalogFile(trial.getId());
			}
			
			if (answer) {
				this.trial = trial.clone();

				Trial temp = get(trial);

				if (temp == null) {
					if (insert(trial)) {
						this.trial = get(trial);
					}
				}

				answer = this.trial != null;
			}			
		}
		
		return answer;
	}

}
