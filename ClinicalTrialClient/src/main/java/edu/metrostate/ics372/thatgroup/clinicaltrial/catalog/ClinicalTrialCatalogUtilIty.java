/**
 * File: ClinicalTrialCatalogUtility.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	private static final String WINDOWS = "win";
	private static final String MAC = "mac";
	private static final String LINUX = "nux";
	
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
	
}
