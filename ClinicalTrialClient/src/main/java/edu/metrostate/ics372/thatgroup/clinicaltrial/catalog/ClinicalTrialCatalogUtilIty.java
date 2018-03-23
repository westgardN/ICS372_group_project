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
 * 
 * @author That Group
 *
 */
public class ClinicalTrialCatalogUtilIty {
	private static String catalogStoragePath = getEnvironmentSpecificStoragePath();
	public final static String CATALOG_EXTENSION = ".db";
	private static String currentCatalogName;
	
	/**
	 * @return the currentCatalogName
	 */
	public static String getCurrentCatalogName() {
		return currentCatalogName;
	}


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
			path = Paths.get(HOME, "Library", "Application", STORAGE_DIR);
		} else if (environment.contains(LINUX)) {
			if (System.getenv("XDG_DATA_HOME") != null) {
				path = Paths.get(System.getenv("XDG_DATA_HOME"), STORAGE_DIR);
			} else {
				path = Paths.get(HOME, STORAGE_DIR);
			}
		}
		return (path + File.separator).toString();
	}

	 static boolean writeAndInitializeCatalogFile(File catalogFile, String trialName) throws SQLException, IOException {
		boolean answer = false;
		String[] initializationStatements = new String[] { 
				ClinicalStatement.CREATE_TABLE_TRIAL,
				ClinicalStatement.CREATE_TABLE_CLINICS,
				ClinicalStatement.CREATE_TABLE_PATIENTS,
				ClinicalStatement.CREATE_TABLE_READINGS,
				};
		try {
			if (catalogFile.createNewFile()) {
				answer = true;
				for (String statement : initializationStatements) {
					try (Connection conn = getConnection(trialName);
							Statement stmt = conn.createStatement();) {
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
