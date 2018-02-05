/**
 * File: PatientsView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
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
				if (event.getNewValue() instanceof Patient) {
					Patient patient = (Patient)event.getNewValue();
					
					if (event.getOldValue() == null) {
						updateButtons(patient, false);
					} else {
						updateButtons(patient, true);
					}
				}
			} else if (prop.equals("updatePatient")) {
				if (event.getNewValue() instanceof Patient) {
					updatePatient((Patient)event.getNewValue());
				}
			}
			
		});
		
		listView.itemsProperty().bind(patientsProperty);
		
		listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			int index = newValue.intValue();
			
			if (index >= 0 && index < patientsProperty.size() && patientsProperty.get(index) instanceof Patient) {
				Patient patient = patientsProperty.get(index);
				if (!Objects.equals(patient, model.getSelectedPatient())) {
					model.setSelectedPatient(patient);
				}
				
			} else {
				model.setSelectedPatient(null);
			}
		});
	}
	
	private void updateButtons(Patient patient, boolean updatePatient) {
		if (updatePatient && patient != null) {
			updatePatient(patient);
		}
		
		if (!model.hasPatientStartedTrial(patient)) {
			startPtTrial.setDisable(false);
			endPtTrial.setDisable(true);
		} else if (model.isPatientInTrial(patient)) {
			endPtTrial.setDisable(false);
			startPtTrial.setDisable(true);
		}
	}

	/**
	 * @param patient
	 */
	private void updatePatient(Patient patient) {
		int index = patientsProperty.indexOf(patient);
		
		if (index >= 0) {
			int selected = listView.getSelectionModel().getSelectedIndex();
			patientsProperty.set(index, null);
			patientsProperty.set(index, patient);
			listView.getSelectionModel().select(selected);
		}
	}

	public void startPtTrial(ActionEvent e){
		Patient patient = model.getSelectedPatient();
		if (patient != null) {
			LocalDate startDate = LocalDate.now();
			patient.setTrialStartDate(startDate);
			patient.setTrialEndDate(null);
			updateButtons(patient, true);
		}
	}
	
	public void endPtTrial(ActionEvent e){
		Patient patient = model.getSelectedPatient();
		if (patient != null) {
			LocalDate endDate = LocalDate.now();
			patient.setTrialEndDate(endDate);
			updateButtons(patient, true);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startPtTrial.setDisable(true);
		endPtTrial.setDisable(true);		
	}
}

