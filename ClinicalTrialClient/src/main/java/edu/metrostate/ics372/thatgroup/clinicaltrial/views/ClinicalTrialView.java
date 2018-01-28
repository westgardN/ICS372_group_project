package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.net.URL;
import java.util.ResourceBundle;
import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;



public class ClinicalTrialView implements Initializable {
	ClinicalTrialViewModel viewModel;
	
	// Patient and Reading ListViews
	@FXML
	ListView<Patient> patientList;
	@FXML
	ListView<Reading> readingList;
	
	// Labels
	
	
	// Actions Section
		//Add Reading Form
	@FXML
	TextField patientIDTxt;
	@FXML
	ChoiceBox<String> readingTypeChoice;
	@FXML
	TextField readingIDTxt;
	@FXML
	TextField readingValueTxt;
	@FXML
	DatePicker readingDateTxt;
	@FXML
	Button submitBtn;
	
		// Edit Reading
	@FXML
	TextField patientIDTxtEdit;
	@FXML
	ChoiceBox<String> readingTypeChoiceEdit;
	@FXML
	TextField readingIDTxtEdit;
	@FXML
	TextField readingValueTxtEdit;
	@FXML
	DatePicker readingDateTxtEdit;
	@FXML
	Button submitBtnEdit;
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		viewModel = new ClinicalTrialViewModel();
		patientList.setItems(viewModel.getObvservableSimPatients());
		readingTypeChoice.setItems(viewModel.getReadingTypeChoices());
		readingTypeChoice.getSelectionModel().selectFirst();

		patientList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
			@Override
			public void changed(ObservableValue<? extends Patient> observable, Patient oldPatient, Patient newPatient) {			
				patientIDTxt.setText(observable.getValue().toString());
				readingList.getItems().clear();
				readingList.setItems(viewModel.getPatientJournal(newPatient));
			}
		});
		
		readingList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reading>() {
			@Override
			public void changed(ObservableValue<? extends Reading> observable, Reading oldReading, Reading newReading) {
				// TODO Auto-generated method stub
			}
		});
	}
}