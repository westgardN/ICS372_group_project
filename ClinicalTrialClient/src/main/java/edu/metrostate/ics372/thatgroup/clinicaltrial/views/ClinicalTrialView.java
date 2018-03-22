/**
 * File: ClinicalTrialView
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.JsonProcessor;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
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
	Stage stage;
	ClinicalTrialModel model;

	@FXML
	Menu menuFile;

	@FXML
	AddPatientView addPatientView;

	@FXML
	PatientsView patientsView;

	@FXML
	ReadingView readingView;

	@FXML
	ReadingsView readingsView;

	/**
	 * Imports readings from a JSON formatted file
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void importReadings(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Import File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"),
				new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // user.dir is the directory the JVM
																					// was executed from.
		File file = fileChooser.showOpenDialog(stage);

		Patient selected = model.getSelectedPatient();

		if (file != null) {
			try {
				List<Reading> readings = JsonProcessor.read(file.getAbsolutePath());
				int readingCount = 0;
				int patientCount = 0;

				for (Reading reading : readings) {
					reading.setClinicId(model.getDefaultClinic().getId());
					Patient patient = model.getPatient(reading.getPatientId());					

					if (patient == null) {
						if (model.addPatient(reading.getPatientId(), LocalDate.now())) {
							++patientCount;
							patient = model.getPatient(reading.getPatientId());
						}
					}

					if (patient != null) {
						model.setSelectedPatient(patient, false);
						if (model.importReading(reading)) {
							readingCount++;
						}
					}
				}
				model.setSelectedPatient(selected, true);
				PopupNotification.showPopupMessage(
						"Imported " + patientCount + " patients(s) and " + readingCount + " reading(s)",
						stage.getScene());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TrialCatalogException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"),
				new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // user.dir is the directory the JVM
																					// was executed from. We may want to
																					// change this to something else.
		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			try {
				List<Reading> readings = new ArrayList<>();

				for (Patient patient : model.getPatients()) {
					for (Reading reading : model.getJournal(patient)) {
						readings.add(reading);
					}
				}

				JsonProcessor.write(readings, file.getAbsolutePath());
				PopupNotification.showPopupMessage("Exported " + readings.size() + " reading(s)", stage.getScene());
			} catch (TrialCatalogException | IOException e) {
				e.printStackTrace();
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
			addPatientView.setModel(model);
			patientsView.setModel(model);
			readingView.setModel(model);
			readingsView.setModel(model);
		} catch (TrialCatalogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}