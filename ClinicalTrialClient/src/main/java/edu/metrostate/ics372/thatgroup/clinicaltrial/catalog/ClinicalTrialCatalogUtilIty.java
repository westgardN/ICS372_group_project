package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ClinicalTrialCatalogUtilIty {
	static String catalogStoragePath = getEnvironmentSpecificStoragePath();
	final static String CATALOG_EXTENSION = ".db";
	static Connection currentCatalogConnection;
	public static String currentCatalogName;

	public static Connection connectToTrialCatalog(String trialName) {
		final String CONNECTOR_PREFIX = "jdbc:sqlite:";
		String connectionUrl = CONNECTOR_PREFIX + catalogStoragePath + trialName.concat(CATALOG_EXTENSION);
			try {
				if (currentCatalogConnection != null && !currentCatalogConnection.isClosed()) {
					disconnectFromCatalog();
				} else {
					currentCatalogConnection = DriverManager.getConnection(connectionUrl);
					currentCatalogName = trialName;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return currentCatalogConnection;
	}
	
	public static void disconnectFromCatalog() {
		try {
			currentCatalogConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	 static boolean writeAndInitializeCatalogFile(File catalogFile, String trialName) {
		boolean answer = false;
		String[] initializationStatements = new String[] { 
				ClinicalStatement.CREATE_TABLE_TRIAL.getStatement(),
				ClinicalStatement.CREATE_TABLE_CLINICS.getStatement(),
				ClinicalStatement.CREATE_TABLE_PATIENTS.getStatement(),
				ClinicalStatement.CREATE_TABLE_READINGS.getStatement(),
				};
		try {
			if (catalogFile.createNewFile()) {
				answer = true;
				for (String statement : initializationStatements) {
					try {
						currentCatalogConnection = connectToTrialCatalog(trialName);
						Statement stmt = currentCatalogConnection.createStatement();
						stmt.execute(statement);
						disconnectFromCatalog();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
	 static class HibernateUtil {
		 
		 // /ClinicalTrialClient/src/
	 }
}
