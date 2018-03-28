/**
 * File: ClinicalTrialView
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.importexport.TrialDataExporter;
import edu.metrostate.ics372.thatgroup.clinicaltrial.importexport.TrialDataImportExporterFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.importexport.TrialDataImporter;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * This View is the main wrapper for the user interface and is responsible for
 * holding the the various views
 * 
 * @author That Group
 */
public class ClinicalTrialView implements Initializable {
	private Stage stage;
	private ClinicalTrialModel model;

	@FXML
	Menu menuFile;

	@FXML
	PatientsView patientsView;

	@FXML
	ReadingView readingView;

	@FXML
	ReadingsView readingsView;
	
	@FXML
	ClinicsView clinicsView;

	/**
	 * Imports readings from a JSON formatted file
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void importReadings(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(Strings.PROMPT_SELECT_IMPORT_FILE);
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Strings.PROMPT_IMPORT_FILES, Strings.PROMPT_EXTENSION_IMPORT_FILE),
				new ExtensionFilter(Strings.PROMPT_JSON_FILES, Strings.PROMPT_EXTENSION_JSON),
				new ExtensionFilter(Strings.PROMPT_XML_FILES, Strings.PROMPT_EXTENSION_XML),
				new ExtensionFilter(Strings.PROMPT_ALL_FILES, Strings.PROMPT_EXTENSION_ALL));
		fileChooser.setInitialDirectory(new File(System.getProperty(Strings.SYS_PROP_USER_DIR))); // user.dir is the directory the JVM
																					// was executed from.
		File file = fileChooser.showOpenDialog(stage);

		Patient selectedPatient = model.getSelectedPatient();
		Clinic selectedClinic = model.getSelectedClinic();
		
		if (file != null) {
			PopupNotification.showPopupMessage(Strings.MSG_START_IMPORTING, stage.getScene());
			setDisable(true);
			ExecutorService executor = Executors.newCachedThreadPool();
			
			executor.submit(() -> {
				try {
					model.setImporting(true);
					TrialDataImporter importer = TrialDataImportExporterFactory.getTrialImporter(file.getName());

					try (InputStream is = new FileInputStream(file)) {					
						boolean success = importer.read(model.getTrial(), is);
						if (success) {
							model.setSelectedClinic(null);
							model.setSelectedPatient(null);
							int readingCount = 0;
							int patientCount = 0;
							int clinicCount = 0;
							List<Reading> readings = importer.getReadings();
							List<Clinic> clinics = importer.getClinics();
							List<Patient> patients = importer.getPatients();
							
							for (Clinic clinic : clinics) {
								if (model.getClinic(clinic.getId()) == null) {
									model.addClinic(clinic.getId(), clinic.getName() == null ? clinic.getId() : clinic.getName());
									clinicCount++;
								}
							}
							
							for (Patient patient : patients) {
								if (model.getPatient(patient.getId()) == null) {
									model.addPatient(patient.getId(), patient.getTrialStartDate(), patient.getTrialEndDate());
									patientCount++;
								}
							}
			
							for (Reading reading : readings) {
								if (reading.getClinicId() == null || reading.getClinicId().trim().isEmpty()) {
									reading.setClinicId(model.getSelectedOrDefaultClinic().getId());
								}
								
								Clinic clinic = model.getClinic(reading.getClinicId());
								
								if (clinic == null) {
									if (model.addClinic(reading.getClinicId(), reading.getClinicId())) {
										++clinicCount;
										clinic = model.getClinic(reading.getClinicId());
									}
								}
								
								Patient patient = model.getPatient(reading.getPatientId());					
			
								if (patient == null) {
									if (model.addPatient(reading.getPatientId(), LocalDate.now())) {
										++patientCount;
										patient = model.getPatient(reading.getPatientId());
									}
								}
			
								if (patient != null && clinic != null) {
									if (model.importReading(reading)) {
										readingCount++;
									}
								}
							}
							model.setSelectedClinic(selectedClinic, true);
							model.setSelectedPatient(selectedPatient, true);
							final int cCount = clinicCount;
							final int pCount = patientCount;
							final int rCount = readingCount;
							Platform.runLater(() -> {
								model.setImporting(false);
								PopupNotification.showPopupMessage(
										String.format(Strings.SUCCESS_FILE_IMPORTED_EXPORTED, Strings.MSG_IMPORTED, cCount, pCount, rCount),
										stage.getScene());
								setDisable(false);
							});
						} else {
							Platform.runLater(() -> {
								model.setImporting(false);
								PopupNotification.showPopupMessage(Strings.ERR_FILE_NOT_IMPORTED, stage.getScene());
								setDisable(false);
							});
						}
					}
				} catch (IOException | TrialException e) {
					Platform.runLater(() -> {
						model.setImporting(false);
						PopupNotification.showPopupMessage(e.getMessage(), stage.getScene());
						setDisable(false);
					});
				}
			});
		}
	}

	private void setDisable(boolean value) {
		menuFile.setDisable(value);
		clinicsView.setDisable(value);
		patientsView.setDisable(value);
		readingsView.setDisable(value);
		readingView.setDisable(value);
	}

	/**
	 * Exports readings to a JSON formatted file
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void exportReadings(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(Strings.PROMPT_SELECT_EXPORT_FILE);
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(Strings.PROMPT_EXPORT_FILES, Strings.PROMPT_EXTENSION_JSON),
				new ExtensionFilter(Strings.PROMPT_ALL_FILES, Strings.PROMPT_EXTENSION_ALL));
		fileChooser.setInitialDirectory(new File(System.getProperty(Strings.SYS_PROP_USER_DIR))); // user.dir is the directory the JVM
																					// was executed from. We may want to
																					// change this to something else.
		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			PopupNotification.showPopupMessage(Strings.MSG_START_EXPORTING, stage.getScene());
			setDisable(true);
			ExecutorService executor = Executors.newCachedThreadPool();
			
			executor.submit(() -> {
				try (OutputStream os = new FileOutputStream(file)){
					List<Patient> patients = model.getPatients();
					List<Clinic> clinics = model.getClinics();
					List<Reading> readings = model.getReadings();
					TrialDataExporter exporter = TrialDataImportExporterFactory.getTrialExporter(file.getName());
					exporter.setPatients(patients);
					exporter.setClinics(clinics);
					exporter.setReadings(readings);
					
					exporter.write(os);
					Platform.runLater(() -> {
						PopupNotification.showPopupMessage(
								String.format(Strings.SUCCESS_FILE_IMPORTED_EXPORTED, Strings.MSG_EXPORTED, clinics.size(), patients.size(), readings.size()),
								stage.getScene());
						setDisable(false);
					});
				} catch (TrialException | IOException e) {
					Platform.runLater(() -> {
						PopupNotification.showPopupMessage(e.getMessage(), stage.getScene());
						setDisable(false);
					});
				}				
			}); 
		}
	}

	/**
	 * Terminates the program
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void exit(ActionEvent event) {
		Platform.exit();
	}

	/**
	 * Returns the Stage
	 * 
	 * @return stage the visible Stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Sets the stage for the view and registers an OnCloseRequest() handler to
	 * save the trial's state and exit the application.
	 * 
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
		
		stage.setOnCloseRequest((event) -> {
			exit(null);
		});
	}

	/**
	 * Initializes this view and sets the view-model for the Add Patient View,
	 * Patients View, ReadingView, and Readings View. If the application's state
	 * has been written to the file system, it is loaded and the views are updated
	 * to reflect the change in the model.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			model = new ClinicalTrialModel();
			patientsView.setModel(model);
			readingView.setModel(model);
			readingsView.setModel(model);
			clinicsView.setModel(model);
		} catch (TrialCatalogException e) {
			if (stage != null) {
				PopupNotification.showPopupMessage(e.getMessage(), stage.getScene());
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
}