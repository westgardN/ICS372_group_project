/**
 * File: PatientsView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.io.InputStream;
import java.net.URL;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class PatientsView extends AnchorPane implements Initializable {
	@FXML 
	private ListView<Patient> listView;
	private ClinicalTrialViewModel model;
	private ListProperty<Patient> patientsProperty;
	@FXML private Button startPtTrial;
	@FXML private Button endPtTrial;
	
	public PatientsView() {
		model = null;
		patientsProperty = new SimpleListProperty<>();
		
		try (InputStream stream = getClass().getResourceAsStream("PatientsView.fxml")) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException exception) {
			throw new RuntimeException(exception);
		}
		
	}

	/**
	 * @return the model
	 */
	public ClinicalTrialViewModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(ClinicalTrialViewModel model) {
		this.model = model;
		
		patientsProperty.set(model.getPatients());
		
		this.model.addPropertyChangeListener((event) -> {
			String prop = event.getPropertyName();
			if (prop.equals("selectedPatient")) {
				updateButtons(this.model.getSelectedPatient());
			}
		});
		
		listView.itemsProperty().bind(patientsProperty);
		
		listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() >= 0) {
				model.setSelectedPatient(listView.getItems().get(newValue.intValue()));
			}
		});
	}
	
	private void updateButtons(Patient selectedPatient) {
		updateSelectedPatient(selectedPatient);
		
		if (!model.hasPatientStartedTrial(selectedPatient)) {
			startPtTrial.setDisable(false);
			endPtTrial.setDisable(true);
		} else if (model.isPatientInTrial(selectedPatient)) {
			endPtTrial.setDisable(false);
			startPtTrial.setDisable(true);
		}
	}

	/**
	 * @param selectedPatient
	 */
	private void updateSelectedPatient(Patient selectedPatient) {
		int index = patientsProperty.indexOf(selectedPatient);
		
		if (index >= 0) {
			patientsProperty.set(index, null);
			patientsProperty.set(index, selectedPatient);
		}
	}

	public void startPtTrial(ActionEvent e){
		LocalDate startDate = LocalDate.now();
		Patient patient = model.getSelectedPatient();
		patient.setTrialStartDate(startDate);
		patient.setTrialEndDate(null);
		updateSelectedPatient(patient);
		startPtTrial.setDisable(true);
		endPtTrial.setDisable(false);
	}
	
	public void endPtTrial(ActionEvent e){
		LocalDate endDate = LocalDate.now();
		Patient patient = model.getSelectedPatient();
		patient.setTrialEndDate(endDate);
		
		updateSelectedPatient(patient);
		startPtTrial.setDisable(false);
		endPtTrial.setDisable(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startPtTrial.setDisable(true);
		endPtTrial.setDisable(true);		
	}
}

