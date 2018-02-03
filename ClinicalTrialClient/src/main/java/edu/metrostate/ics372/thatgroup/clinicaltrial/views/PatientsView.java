/**
 * File: PatientsView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.time.LocalDate;
import java.io.InputStream;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class PatientsView extends AnchorPane {
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
		endPtTrial.setDisable(true);
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
		
		this.model.getTrial().addPropertyChangeListener((event) -> {
			System.out.println(event);
		});
		
		listView.itemsProperty().bind(patientsProperty);
		
		listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() >= 0) {
				model.setSelectedPatient(listView.getItems().get(newValue.intValue()));
			}
		});
	}
	public void startPtTrial(ActionEvent e){
		LocalDate startDate = LocalDate.now();
		model.getSelectedPatient().setTrialStartDate(startDate);


	}
	public void endPtTrial(ActionEvent e){
		LocalDate endDate = LocalDate.now();
		model.getSelectedPatient().setTrialStartDate(endDate);
	}
	
	
}

