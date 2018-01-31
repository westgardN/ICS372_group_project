/**
 * File: PatientsView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class PatientsView extends AnchorPane implements Initializable {
	@FXML private ListView<Patient> listView;
	private ClinicalTrialViewModel model;
	
	public PatientsView() {
		model = null;
		
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
		
		listView.setItems(model.getPatients());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
