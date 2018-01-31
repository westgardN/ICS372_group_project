package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ClinicalTrialView implements Initializable {
	Patient currentPatient;
	ClinicalTrialViewModel viewModel;

	@FXML
	Menu menuFile;
	@FXML
	Menu menuTrial;
	@FXML
	Menu menuHelp;
	
//	@FXML
//	AddPatientView addPatientView;
	
//	@FXML
//	VBox inputForm;
//
//	@FXML
//	StackPane editStackPane;
	// Patient and Reading ListViews
//	@FXML
//	ListView<Patient> patientList;
	@FXML
	TableView<Reading> readingTable;

	@FXML
	TableColumn<Reading, String> dateTimeCol;
	@FXML
	TableColumn<Reading, String> readingTypeCol;
	@FXML
	TableColumn<Reading, String> valueCol;
	@FXML
	TableColumn<Reading, String> readingIDCol;
	// Actions Section
//	@FXML
//	Button addReadingBtn;
//	@FXML
//	Button editReadingBtn;
//	@FXML
//	Button deleteReadingBtn;
	// Input Form Add/Edit/Delete
//	@FXML
//	TextField patientIDTxt;
//	@FXML
//	ChoiceBox<String> readingTypeChoice;
//	@FXML
//	TextField readingIDTxt;
//	@FXML
//	TextField readingValueTxt;
//	@FXML
//	StackPane bloodPressStack;
//	@FXML
//	TextField systolic;
//	@FXML
//	TextField diastolic;
//	@FXML
//	DatePicker readingDate;
//	@FXML
//	Button submitBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Hide the Add/Edit/Delete input Form until the user clicks the Add/Edit/Delete
		// button
//		inputForm.setVisible(false);
		// Initialize the view Model
		viewModel = new ClinicalTrialViewModel();
		// Add patients to the patient list if there are any already existing in the
		// trial
//		patientList.setItems(viewModel.getObservablePatients());
		// Initialize the reading type choice box
//		readingTypeChoice.setItems(viewModel.getReadingTypeChoices());
//		// Set the reading type choice box default value
//		readingTypeChoice.getSelectionModel().selectFirst();

		// Creates a change listener for the patient list
//		patientList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
//			@Override
//			public void changed(ObservableValue<? extends Patient> observable, Patient oldPatient, Patient newPatient) {
//				/*
//				 * Sets the view model's currently observable patient journal to the one
//				 * currently selected in the patient list
//				 */
//				if (newPatient != null) {
//					viewModel.setObservablePatientJournal(newPatient);
//				}
//				/*
//				 * When a new patient is selected from the list, set currentPatient to the
//				 * currently selected patient to be used with fillReading() as it is used to
//				 * find the proper patient journal to display in readings table
//				 */
//				if (patientList.getSelectionModel().getSelectedItem() != null) {
//					currentPatient = patientList.getSelectionModel().getSelectedItem();
//					patientIDTxt.setText(currentPatient.getId());
//				}
//				/*
//				 * Fill the reading table with the proper patient journal of the currently
//				 * selected patient
//				 */
//				fillReadingTable();
//			}
//		});
//
		/*
		 * Sets the action listener for addPatientButton which will send the information
		 * from the add new patient form to the model view to create and add a new
		 * patient to the trials list
		 */
//		addPatientBtn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				LocalDate startDate = newPatientStartDate.getValue();
//				viewModel.addPatientToTrial(addPatientIDTxt.getText(), startDate);
//				patientList.setItems(viewModel.getObservablePatients());
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
//				viewModel.addReadingForPatient(patientList.getSelectionModel().getSelectedIndex(),
//						readingIDTxt.getText(), readingTypeChoice.getSelectionModel().getSelectedItem().toString(),
//						readingValueTxt.getText(), readingDate.getValue());
//				fillReadingTable();
//				patientList.getItems().clear();
//				patientList.setItems(viewModel.getObservablePatients());
//			}
//		});
	}

	/*
	 * Fills the reading table columns with the proper information based on the
	 * currently selected patient and their respective readings
	 */
	private void fillReadingTable() {
		readingTable.setItems(viewModel.getPatientJournal(currentPatient));
		readingIDCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getId().toString()));
		readingTypeCol.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(ReadingFactory.getReadingType(cellData.getValue())));
		valueCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
		dateTimeCol
				.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDate().toString()));
	}
}