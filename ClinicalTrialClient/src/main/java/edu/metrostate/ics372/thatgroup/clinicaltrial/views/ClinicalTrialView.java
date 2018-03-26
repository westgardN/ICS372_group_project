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

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.importexport.TrialDataExporter;
import edu.metrostate.ics372.thatgroup.clinicaltrial.importexport.TrialDataImportExporterFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.importexport.TrialDataImporter;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.views.AddClinicView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Menu;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
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
	Stage stage;
	ClinicalTrialModel model;

	@FXML
	Menu menuFile;

	@FXML
	SplitPane mainSplitter;
	
	@FXML
	Accordion accordion;
	
	@FXML
	TitledPane clinicPane;
	
	@FXML
	SplitPane splitter;
	
	@FXML
	AddPatientView addPatientView;

	@FXML
	PatientsView patientsView;

	@FXML
	ReadingView readingView;

	@FXML
	ReadingsView readingsView;
	
	@FXML
	AddClinicView addClinicView;
	
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
		fileChooser.setTitle("Select Import File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Import Files", "*.json;*.xml"),
				new ExtensionFilter("JSON Files", "*.json"),
				new ExtensionFilter("XML Files", "*.xml"),
				new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // user.dir is the directory the JVM
																					// was executed from.
		File file = fileChooser.showOpenDialog(stage);

		Patient selectedPatient = model.getSelectedPatient();
		Clinic selectedClinic = model.getSelectedClinic();

		if (file != null) {
			try (InputStream is = new FileInputStream(file)) {
				TrialDataImporter importer = TrialDataImportExporterFactory.getTrialImporter(file.getName());
				
				boolean success = importer.read(model.getTrial(), is);
				if (success) {
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
							model.addPatient(patient.getId(), patient.getTrialStartDate());
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
					PopupNotification.showPopupMessage(
							"Imported " + clinicCount + " clinic(s), " + patientCount + " patients(s) and " + readingCount + " reading(s)",
							stage.getScene());
				} else {
					PopupNotification.showPopupMessage("The file was not imported.", stage.getScene());
				}
				model.setSelectedClinic(selectedClinic, true);
				model.setSelectedPatient(selectedPatient, true);
				
			} catch (IOException | TrialException e) {
				PopupNotification.showPopupMessage(e.getMessage(), stage.getScene());
			}
		}
	}

	/**
	 * Exports readings to a JSON formatted file
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void exportReadings(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Export File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Export Files", "*.json"),
				new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // user.dir is the directory the JVM
																					// was executed from. We may want to
																					// change this to something else.
		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			try (OutputStream os = new FileOutputStream(file)){
				List<Clinic> clinics = model.getClinics();
				List<Reading> readings = model.getReadings();
				TrialDataExporter exporter = TrialDataImportExporterFactory.getTrialExporter(file.getName());
				exporter.setClinics(clinics);
				exporter.setReadings(readings);
				
				exporter.write(os);
				PopupNotification.showPopupMessage("Exported " + clinics.size() + " clinics(s)" + " and " + readings.size() + " reading(s)", stage.getScene());
			} catch (TrialException | IOException e) {
				PopupNotification.showPopupMessage(e.getMessage(), stage.getScene());
			}
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
			mainSplitter.setDividerPosition(0, 0.333);
			splitter.setDividerPosition(0, 0.43);
			accordion.setExpandedPane(clinicPane);
			addPatientView.setModel(model);
			patientsView.setModel(model);
			readingView.setModel(model);
			readingsView.setModel(model);
			addClinicView.setModel(model);
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