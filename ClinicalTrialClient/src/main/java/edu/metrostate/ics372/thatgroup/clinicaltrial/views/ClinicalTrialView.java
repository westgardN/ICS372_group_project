package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.JsonProcessor;
import edu.metrostate.ics372.thatgroup.clinicaltrial.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ClinicalTrialView implements Initializable {
	Stage stage;
	ClinicalTrialViewModel model;

	@FXML
	Menu menuFile;
	@FXML
	Menu menuTrial;
	@FXML
	Menu menuHelp;
	
	@FXML
	AddPatientView addPatientView;
	
	@FXML
	PatientsView patientsView;
	
	@FXML
	ReadingView readingView;
	
	@FXML
	ReadingsView readingsView;
	
	public void importReadings(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Graph File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("JSON Files", "*.json"),
                new ExtensionFilter("All Files", "*.*"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // user.dir is the directory the JVM was executed from.
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            try {
				List<Reading> readings = JsonProcessor.read(file.getAbsolutePath());
				Trial trial = model.getTrial();
				int count = 0;
				
				for (Reading reading : readings) {
					Patient patient = trial.getPatient(reading.getPatientId());
					
					if (patient == null) {
						if (model.addPatient(reading.getPatientId(), null)) {
							patient = trial.getPatient(reading.getPatientId());
						}
					}
					
					if (patient != null) {
						if (patient.addReading(reading)) {
							count++;
						}
					}
				}
				
				PopupNotification.showPopupMessage("Imported " + count + " reading(s)", stage.getScene());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	public void exportReadings(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Graph File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("JSON Files", "*.json"),
                new ExtensionFilter("All Files", "*.*"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // user.dir is the directory the JVM was executed from.
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
				Trial trial = model.getTrial();
				List<Reading> readings = new ArrayList<>();
				
				for (Patient patient : trial.getPatients()) {
					for (Reading reading : patient.getJournal()) {
						readings.add(reading);
					}
				}
				
				JsonProcessor.write(readings, file.getAbsolutePath());
				PopupNotification.showPopupMessage("Exported " + readings.size() + " reading(s)", stage.getScene());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Hide the Add/Edit/Delete input Form until the user clicks the Add/Edit/Delete
		// button
//		inputForm.setVisible(false);
		// Initialize the view Model
		model = new ClinicalTrialViewModel();
		
		addPatientView.setModel(model);
		patientsView.setModel(model);
		readingView.setModel(model);
		readingsView.setModel(model);
		
		/*
		 * Sets the action listener for addPatientButton which will send the information
		 * from the add new patient form to the model view to create and add a new
		 * patient to the trials list
		 */
//		addPatientBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				LocalDate startDate = newPatientStartDate.getValue();
//				model.addPatientToTrial(addPatientIDTxt.getText(), startDate);
//				patientList.setItems(model.getObservablePatients());
//			}
//		});

		/*
		 * Sets the action listener for the addReadingBtn which will show the add
		 * reading input form and also sets the patientIDTxt field to the currently
		 * selected patients ID
		 */
//		addReadingBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				inputForm.setVisible(true);
//				submitBtn.setText("Add Reading");
//				patientIDTxt.setText(patientList.getSelectionModel().getSelectedItem().getId());
//			}
//		});
//
//		/*
//		 * Not yet implemented TODO
//		 */
//		editReadingBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//
//			}
//		});
//
//		/*
//		 * Not yet implemented TODO
//		 */
//		deleteReadingBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//			}
//		});
//
		/*
		 * Sets the change listener for the reading type choice box and changes the
		 * input form to accommodate a blood pressure reading entry
		 */
//		readingTypeChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> observableValue, String oldSelection,
//					String newSelection) {
//				if (newSelection.toLowerCase().equals("blood pressure")) {
//					bloodPressStack.toFront();
//					bloodPressStack.setVisible(true);
//					readingValueTxt.setVisible(false);
//				} else {
//					bloodPressStack.toBack();
//					bloodPressStack.setVisible(false);
//					readingValueTxt.setVisible(true);
//				}
//			}
//		});

		/*
		 * Sets the action listener for the input forms submitBtn. Currently only
		 * supports the creation of a new reading TODO
		 */
//		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				model.addReadingForPatient(patientList.getSelectionModel().getSelectedIndex(),
//						readingIDTxt.getText(), readingTypeChoice.getSelectionModel().getSelectedItem().toString(),
//						readingValueTxt.getText(), readingDate.getValue());
//				fillReadingTable();
//				patientList.getItems().clear();
//				patientList.setItems(model.getObservablePatients());
//			}
//		});
	}

}