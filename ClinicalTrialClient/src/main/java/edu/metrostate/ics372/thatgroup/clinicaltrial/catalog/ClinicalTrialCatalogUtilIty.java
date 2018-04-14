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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * The ClinicalTrialCatalogUtilIty is used to find the specific storage path for
 * the trial catalog(s), get connections to the catalogs, and write and
 * initialize new trial catalogs in the directory. The storage directory is
 * dependent upon which operating system the user is currently using. The
 * relevant storage paths are as follows:
 * <ul>
 * <li>Windows: Users\*user*\AppData\Roaming\That Group\Trial Catalogs
 * <li>Linux: $HOME\.local\share\That Group\Clinical Trial Catalogs
 * <li>MAC: Users\*user*\Library\That Group\Clinical Trial Catalogs
 * </ul>
 * 
 * 
 * @author That Group
 *
 */
public class ClinicalTrialCatalogUtilIty {
	private static final String ANDROID_CATALOG_STORAGE_PATH = "/data/data/edu.metrostate.ics372.thatgroup.clinicaltrial/databases/";
	private static final String OS_XDG_DATA_HOME = "XDG_DATA_HOME";
	private static final String OS_LIBRARY = "Library";
	private static final String OS_APPDATA = "APPDATA";
	private static final String SYS_PROP_OS_NAME = "os.name";
	private static final String SYS_PROP_VENDOR_URL = "java.vendor.url";
	private static final String SYS_PROP_VM_VENDOR = "java.vm.vendor";
	private static final String SYS_PROP_VENDOR = "java.vendor";
	private static final String ANDROID_PROP_VENDOR = "the android project";
	private static final String ANDROID_PROP_VENDOR_URL = "android.com";
	private static final String DB_FOLDER = "catalogs";
	private static final String APP_TITLE = "Clinical Trial Client";
	private static final String THAT_GROUP = "That Group";
	private static final String SYS_PROP_USER_HOME = "user.home";
	private static final String CONNECTOR_PREFIX = "jdbc:sqlite:";
	private static final String ANDROID_CONNECTOR_PREFIX = "jdbc:sqldroid:";	
	private static final String WINDOWS = "win";
	private static final String MAC = "mac";
	private static final String LINUX = "nux";
	private static final String PRAGMA_NAME_COLUMN = "name";
	private static final String MIGRATION_00_COLUMN_NAME = "status_id";
	private static String catalogStoragePath;
	public static final String ANDROID_CATALOG_EXTENSION = ".db3";
	public static final String CATALOG_EXTENSION = ".db";
	private static String currentCatalogName;

	static {
		 catalogStoragePath = getEnvironmentSpecificStoragePath();
	}
	
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
		Connection answer = null;
		String connectionUrl = Strings.EMPTY;
		
		if (isAndroid()) {
			connectionUrl = ANDROID_CONNECTOR_PREFIX + catalogStoragePath + trialName.concat(ANDROID_CATALOG_EXTENSION);
		} else {
			connectionUrl = CONNECTOR_PREFIX + catalogStoragePath + trialName.concat(CATALOG_EXTENSION);
		}
		
		try {
			answer = DriverManager.getConnection(connectionUrl);
			currentCatalogName = trialName;
		} catch (SQLException e) {
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
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
		String answer = Strings.EMPTY;
		boolean android = isAndroid();
		
		Path path = null;
		
		try {
			final String HOME = System.getProperty(SYS_PROP_USER_HOME);
			final String STORAGE_DIR = Paths.get(THAT_GROUP, APP_TITLE, DB_FOLDER).toString();
			String environment = System.getProperty(SYS_PROP_OS_NAME).toUpperCase();
			if (environment.toLowerCase().contains(WINDOWS)) {
				path = Paths.get(System.getenv(OS_APPDATA), STORAGE_DIR);
			} else if (environment.toLowerCase().contains(MAC)) {
				path = Paths.get(HOME, OS_LIBRARY, STORAGE_DIR);
			} else if (environment.toLowerCase().contains(LINUX) && !android) {
				if (System.getenv(OS_XDG_DATA_HOME) != null) {
					path = Paths.get(System.getenv(OS_XDG_DATA_HOME), STORAGE_DIR);
				} else {
					path = Paths.get(HOME, STORAGE_DIR);
				}
			} else if (android) {
				answer = ANDROID_CATALOG_STORAGE_PATH;
			}
		} catch (SecurityException ex) {
		}
		
		if (!android && path != null) {
			answer = (path + File.separator).toString();
		}
		
		return answer; 
	}

	/**
	 * Returns true if we are running on an Android bases operating system.
	 * 
	 * @return true if we are running on an Android bases operating system.
	 */
	public static boolean isAndroid() {
		boolean answer = false;
		
		try {
			String value = System.getProperty(SYS_PROP_VENDOR_URL);
			if (value.toLowerCase().contains(ANDROID_PROP_VENDOR_URL)) {
				answer = true;
			}
		} catch (SecurityException ex) {
		}

		if (!answer) {
			try {
				String value = System.getProperty(SYS_PROP_VM_VENDOR);
				if (value.toLowerCase().contains(ANDROID_PROP_VENDOR)) {
					answer = true;
				}
			} catch (SecurityException ex) {
			}
		}
		
		if (!answer) {
			try {
				String value = System.getProperty(SYS_PROP_VENDOR);
				if (value.toLowerCase().contains(ANDROID_PROP_VENDOR)) {
					answer = true;
				}
			} catch (SecurityException ex) {
			}
		}
		
		return answer;
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
	static boolean writeAndInitializeCatalogFile(File catalogFile, String trialName) throws TrialCatalogException {
		boolean answer = false;
		String[] initializationStatements = new String[] { ClinicalStatement.CREATE_TABLE_TRIAL,
				ClinicalStatement.CREATE_TABLE_CLINICS, ClinicalStatement.CREATE_TABLE_PATIENTS,
				ClinicalStatement.CREATE_TABLE_READINGS};
		try {
			if (catalogFile.createNewFile() ) {
				answer = executeSQLStatements(trialName, initializationStatements);
				if (answer) {
					answer = firstMigrationCatalogFile(trialName);
				}
			}
		} catch (IOException e) {
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return answer;
	}

	static boolean databaseNeedsUpgrade(String trialName) {
		boolean answer = false;
		
		try {
			answer = !patientsTableConstainsStatusField(trialName);
		} catch (TrialCatalogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return answer;

	}
	
	private static boolean patientsTableConstainsStatusField(String trialName) throws TrialCatalogException {
		boolean answer = false;
		try (Connection conn = getConnection(trialName); Statement stmt = conn.createStatement();) {
			boolean results = stmt.execute(ClinicalStatement.MIGRATION_PROJECT_3_00);
			
			if (results) {
				ResultSet rSet = stmt.getResultSet();
				
				while(rSet.next()) {
					int index = rSet.findColumn(PRAGMA_NAME_COLUMN);
					
					String name = rSet.getString(index);
					
					if (name != null && name.toLowerCase().equals(MIGRATION_00_COLUMN_NAME)) {
						answer = true;
						break;
					}
				}
				
				if (!rSet.isClosed()) {
					rSet.close();
				}
			}
		} catch (SQLException e) {
			answer = false;
			throw new TrialCatalogException(e.getMessage(), e);
		}
		return answer;
	}

	/**
	 * @param catalogFile
	 * @param trialName
	 * @param initializationStatements
	 * @param answer
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private static boolean executeSQLStatements(String trialName, String[] initializationStatements
			) throws TrialCatalogException {
		boolean answer = false;
		answer = true;
		for (String statement : initializationStatements) {
			try (Connection conn = getConnection(trialName); Statement stmt = conn.createStatement();) {
				stmt.execute(statement);
			} catch (SQLException e) {
				answer = false;
				throw new TrialCatalogException(e.getMessage(), e);
			}
		}
		return answer;
	}
	
	static boolean firstMigrationCatalogFile(String trialName)  throws TrialCatalogException {
		boolean answer = false;
		String[] initializationStatements = new String[] { ClinicalStatement.MIGRATION_PROJECT_3_01,
				ClinicalStatement.MIGRATION_PROJECT_3_02, ClinicalStatement.MIGRATION_PROJECT_3_03,
				ClinicalStatement.MIGRATION_PROJECT_3_04, ClinicalStatement.MIGRATION_PROJECT_3_05,
				ClinicalStatement.MIGRATION_PROJECT_3_06, ClinicalStatement.MIGRATION_PROJECT_3_07,
				ClinicalStatement.MIGRATION_PROJECT_3_08, ClinicalStatement.MIGRATION_PROJECT_3_09,
				ClinicalStatement.MIGRATION_PROJECT_3_10};
		answer = executeSQLStatements(trialName, initializationStatements);
		
		return answer;
	}
	
}
