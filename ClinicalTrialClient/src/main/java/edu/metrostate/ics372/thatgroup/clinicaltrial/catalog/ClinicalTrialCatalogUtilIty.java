/**
 * File: ClinicalTrialCatalogUtility.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The ClinicalTrialCatalogUtilIty is used to find the specific storage path for
 * the trial catalog(s), get connections to the catalogs, and write and
 * initialize new trial catalogs in the directory. The storage directory is
 * dependent upon which operating system the user is currently using. The
 * relevant storage paths are as follows:
 * <ul>
 * <li>Windows: Users\*user*\AppData\Roaming\That Group\Trial Catalogs
 * <li>Linux: $HOME\.local\share\That Group\Clinical Trial Catalogs
 * <li>MAC: Users\*user*\Library\Application Support\That Group\Clinical Trial
 * Catalogs
 * </ul>
 * 
 * 
 * @author That Group
 *
 */
public class ClinicalTrialCatalogUtilIty {
	private static String catalogStoragePath = getEnvironmentSpecificStoragePath();
	public final static String CATALOG_EXTENSION = ".db";
	private static String currentCatalogName;

	/**
	 * Returns the name of the currently selected/connected trial catalog.
	 * 
	 * @return the currentCatalogName the name of the currently selected/connected
	 *         trial catalog.
	 */
	public static String getCurrentCatalogName() {
		return currentCatalogName;
	}

	/**
	 * Gets a connection to a trial catalog for a specific trial depending on the
	 * name of the trial passed to the method.
	 * 
	 * @param trialName
	 *            the name of the trial whose catalog is to be connected to.
	 * @return a new connection to the specific trial catalog.
	 * @throws SQLException
	 */
	public static Connection getConnection(String trialName) throws SQLException {
		final String CONNECTOR_PREFIX = "jdbc:sqlite:";
		Connection answer = null;
		String connectionUrl = CONNECTOR_PREFIX + catalogStoragePath + trialName.concat(CATALOG_EXTENSION);
		try {
			answer = DriverManager.getConnection(connectionUrl);
			currentCatalogName = trialName;
		} catch (SQLException e) {
			throw e;
		}
		return answer;
	}

	/**
	 * Gets the storage path of the trial catalog(s) depending on the specific
	 * operating system the user is using. Supports specific paths for Windows,
	 * Linux, and OS X.
	 * 
	 * @return path the environment specific storage path to store the trial
	 *         catalog(s).
	 */
	public static String getEnvironmentSpecificStoragePath() {
		Path path = null;
		final String WINDOWS = "WIN";
		final String MAC = "MAC";
		final String LINUX = "NUX";
		final String HOME = System.getProperty("user.home");
		final String STORAGE_DIR = Paths.get("That Group", "Clinical Trial Client", "Trial Catalogs").toString();
		String environment = System.getProperty("os.name").toUpperCase();

		if (environment.contains(WINDOWS)) {
			path = Paths.get(System.getenv("APPDATA"), STORAGE_DIR);
		} else if (environment.contains(MAC)) {
			path = Paths.get(HOME, "Library", "Application Support", STORAGE_DIR);
		} else if (environment.contains(LINUX)) {
			if (System.getenv("XDG_DATA_HOME") != null) {
				path = Paths.get(System.getenv("XDG_DATA_HOME"), STORAGE_DIR);
				System.out.println(Paths.get(System.getenv("XDG_DATA_HOME")));
			} else {
				path = Paths.get(HOME, STORAGE_DIR);
			}
		}
		return (path + File.separator).toString();
	}

	/**
	 * Creates/writes a new trial catalog file and initializes the catalog database
	 * tables.
	 * 
	 * @param catalogFile
	 *            the trial catalog file to be created.
	 * @param trialName
	 *            the name of the trial/catalog.
	 * @return true if the trial catalog was written and initialized, else false.
	 * @throws SQLException
	 * @throws IOException
	 */
	static boolean writeAndInitializeCatalogFile(File catalogFile, String trialName) throws SQLException, IOException {
		boolean answer = false;
		String[] initializationStatements = new String[] { ClinicalStatement.CREATE_TABLE_TRIAL,
				ClinicalStatement.CREATE_TABLE_CLINICS, ClinicalStatement.CREATE_TABLE_PATIENTS,
				ClinicalStatement.CREATE_TABLE_READINGS, };
		try {
			if (catalogFile.createNewFile()) {
				answer = true;
				for (String statement : initializationStatements) {
					try (Connection conn = getConnection(trialName); Statement stmt = conn.createStatement();) {
						stmt.execute(statement);
					} catch (SQLException e) {
						throw e;
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
		return answer;
	}
}
