/**
 * File: Strings.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.resources;

/**
 * Holds string literals used in the implementation 
 *
 * @author That Group
 */
public class Strings {
	public static final String ERR_READING_FACTORY_UNKNOWN_READING_TYPE = "Unknown reading type: ";
	public static final String ERR_READING_FACTORY_NULL_OR_UNKNOWN_READING = "reading cannot be null or is an unknown type";
	public static final String ERR_BLOOD_PRESSURE_INVALID_STRING_VALUE = "value must be a string in systolic/diastolic format or it must be a BloodPressureValue";
	public static final String ERR_BLOOD_PRESSURE_INVALID_FORMAT_STRING_VALUE = "Invalid formatted string. value must be a string in systolic/diastolic format";
	public static final String ERR_BLOOD_PRESSURE_UNKNOWN_STRING_VALUE = "Unknown bloodpressure value.";
	public static final String ERR_BLOOD_PRESSURE_ILLEGAL_VALUE = "systolic and diastolic values must be greater than or equal to zero.";
	public static final String ERR_BLOOD_PRESSURE_ILLEGAL_DIASTOLIC_VALUE = "diastolic value must be greater than or equal to zero.";
	public static final String ERR_BLOOD_PRESSURE_ILLEGAL_SYSTOLIC_VALUE = "systolic value must be greater than or equal to zero.";
	public static final String ERR_BLOOD_PRESSURE_VALUE_CLONE_NOT_SUPPORTED = "BloodPressureValue.clone(): This class does not implement Cloneable.";
	public static final String ERR_TRIAL_CLONE_NOT_SUPPOERTED = "Trial.clone(): This class doe not implement Cloneable.";
	public static final String ERR_VALUE_NEGATIVE = "value must be greater than or equal to zero.";
	public static final String ERR_VALUE_NAN = "value must be a number.";
	public static final String ERR_UNIT_VALUE_INVALID_FORMAT_STRING_VALUE = "Invalid formatted string. value must be a string in number unit format";
	public static final String ERR_UNIT_VALUE_NULL_VALUE = "numValue cannot be null.";
	public static final String ERR_WEIGHT_NAN = "value must be a number.";
	public static final String PROMPT_UNIT = "Unit";
	public static final String PROMPT_DIASTOLIC = "Diastolic";
	public static final String PROMPT_SYSTOLIC = "Systolic";
	public static final String PROMPT_VALUE = "Value";
	public static final String ADD = "Add";
	public static final String UPDATE = "Update" ;
	public static final String LOGO_PATH = "/img/logov2_256x256.png";
	public static final String CLINICAL_TRIAL_VIEW_FXML = "/fxml/ClinicalTrialView.fxml";
	public static final String PATIENTS_VIEW_FXML = "/fxml/PatientsView.fxml";
	public static final String READING_VIEW_FXML = "/fxml/ReadingView.fxml";
	public static final String READINGS_VIEW_FXML = "/fxml/ReadingsView.fxml";
	public static final String CLINICS_VIEW_FXML = "/fxml/ClinicsView.fxml";	
	public static final String CSS_PATH = "/css/styling.css";
	public static final String EMPTY = "";
	public static final String ERR_DATE_MSG = "Date can not be empty and must be on or after this patient's start date up to today's date.";
	public static final String ERR_TIME_MSG = "Invalid time detected.  Please check the time input fields for errors.";
	public static final String ERR_DATE_TIME_MSG = "Please check the date and time fields to ensure that it falls between this patient's start date and now.";
	public static final String ERR_ID_MSG = "Invalid reading id detected. Please check the reading id input fields for errors and that there is less then 32 characters.";
	public static final String ERR_BLOOD_PRESSURE_MSG = "Invalid blood pressure detected.  Please check the blood pressure input fields for errors.";
	public static final String ERR_TEMP_MSG = "Invalid temperature or unit detected. Please check the value and unit fields for errors.";
	public static final String ERR_WEIGHT_MSG = "Invalid weight or unit detected. Please check the value and unit fields for errors.";
	public static final String ERR_VALUE_MSG = "Invalid input detected. Please check the reading value input field for errors.";
	public static final String ERR_RECEIVED_MSG = "Received error: ";
	public static final String PATIENT_ADDED_MSG = "New Patient Added";
	public static final String INACTIVE_PATIENT_MSG = "This patient is not active in the clinical trial.";
	public static final String PATIENT_NOT_ADDED_MSG = "Unable to add patient to the trial.";
	public static final String READING_ADDED_MSG = "Reading has been added.";
	public static final String READING_UPDATED_MSG = "Reading has been updated.";
	public static final String READING_NOT_ADDED_MSG  = "Unable to add the reading. Please verify the Reading ID is unique for this patient.";
	public static final String READING_NOT_UPDATED_MSG  = "Unable to update the reading. Please verify the Reading ID is unique for this patient.";
	public static final String CLINIC_NOT_ADDED_MSG = "Unable to add the clinic. Please verify that Clinic ID is unique.";
	public static final String CLINIC_NOT_UPDATED_MSG = "Unable to update the clinic.";
	public static final String CLINIC_ADDED_MSG = "Clinic has been added.";
	public static final String CLINIC_UPDATED_MSG = "Clinic has been updated.";
	public static final String SPECIAL_CHAR_MSG = "No special characters allowed or maximum text length exceeded.";
	public static final String SELECT_PATIENT_TRIAL_DATE_TITLE_FMT = "Select Trial %s Date for Patient %s";
	public static final String SELECT_PATIENT_TRIAL_DATE_LABEL_FMT = "Please select the date patient %s %s the trial";
	public static final String SELECT_PATIENT_TRIAL_DATE_STATUS_FMT = "Please aldo select the reason patient %s is ending the trial";
	public static final String ERR_TRIAL_DATA_EXPORTER_UNSUPPORTED_MSG = "%s is not a supported export type.";
	public static final String ERR_TRIAL_DATA_IMPORTER_EXPORTER_NULL_FILE = "filename cannot be null.";
	public static final String ERR_TRIAL_DATA_IMPORTER_UNSUPPORTED_MSG = "%s is not a supported export type.";
	public static final String ERR_TRIAL_DATA_IMPORTER_BAD_STREAM = "Unable to read from the input stream:\n ";
	public static final String ERR_TRIAL_DATA_EXPORTER_BAD_STREAM = "Unable to write to the output stream:\n ";
	public static final String ERR_TRIAL_DATA_IMPORTER_BAD_TRIAL = "trial must be non null have a valid id.";
	public static final String ERR_TRIAL_DATA_EXPORTER_NO_DATA = "Nothing to export.";
	public static final String ERR_TRIAL_DATA_IMPORTER_PARSING_STREAM = "An error was encountered while parsing the import stream:\n ";
	public static final String ERR_TRIAL_DATA_IMPORTER_BAD_TRIAL_AND_IS = "trial cannot be null and must have a valid id. is must be a valid InputSteam";
	public static final String ERR_TRIAL_DATA_IMPORTER_BAD_XML = "Unable to process import file as it does not appear to be a valid XML import file.";
	public static final String ERR_FILE_NOT_IMPORTED = "The file was not imported.";
	public static final String SUCCESS_FILE_IMPORTED_EXPORTED = "%S %s clinic(s), %d patient(s), and %d reading(s).";
	public static final String ERR_CATALOG_INIT = "Unable to initialize the catalog";
	public static final String ERR_CATALOG_ALREADY_INIT = "Catalog already initialized.";
	public static final String ERR_CATALOG_TRIAL_INVALID = "trial cannot be null and must have a valid id.";
	public static final String ERR_CATALOG_CLINIC_INVALID = "clinic cannot be null and must have a valid id.";
	public static final String ERR_CATALOG_PATIENT_INVALID = "patient cannot be null and must have a valid id.";
	public static final String ERR_CATALOG_PATIENT_STATUS_INVALID = "patient status cannot be null and must have a valid id.";
	public static final String ERR_CATALOG_READING_INVALID = "reading cannot be null and must have a valid id.";
	public static final String ERR_CATALOG_STORAGE_PATH = "the catalog storage path is invalid.";
	public static final String ERR_CATALOG_NO_ACTIVE_TRIAL = "There is no active trial. Please call init with a valid Trial.";
	public static final String ERR_CATALOG_HAS_BEEN_DELETED = "The database for the active trial has been deleted.";
	public static final String ERR_TYPE_CLINIC_PATIENT_MSG = "Please select a valid clinic, patient, and type for the reading.";
	public static final String PROMPT_SELECT_IMPORT_FILE = "Select Import File";
	public static final String PROMPT_SELECT_EXPORT_FILE = "Select Export File";
	public static final String PROMPT_EXTENSION_ALL = "*.*";
	public static final String PROMPT_ALL_FILES = "All Files";
	public static final String PROMPT_JSON_FILES = "JSON Files";
	public static final String PROMPT_XML_FILES = "XML Files";
	public static final String PROMPT_EXTENSION_JSON = "*.json";
	public static final String PROMPT_EXTENSION_IMPORT_FILE = "*.json;*.xml";
	public static final String PROMPT_EXPORT_FILES = "Export Files";
	public static final String PROMPT_IMPORT_FILES = "Import Files";
	public static final String PROMPT_EXTENSION_XML = "*.xml";
	public static final String MSG_EXPORTED = "Exported";
	public static final String MSG_IMPORTED = "Imported";
	public static final String MSG_START_EXPORTING = "Exporting...";
	public static final String MSG_START_IMPORTING = "Importing...";
	public static final String SYS_PROP_USER_DIR = "user.dir";
	
}
