/**
 * File: AddPatientView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class AddPatientView extends VBox {
	@FXML private TextField textField;
	@FXML private DatePicker datePicker;
	@FXML private Button addButton;
	private Collection<Patient> patients;
	
	public AddPatientView() {
		patients = null;
		
		InputStream stream = getClass().getResourceAsStream("AddPatient.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException exception) {
			throw new RuntimeException(exception);
		}
		
		addButton.setDisable(true);
	}
	
	@FXML
	public void addPatient(ActionEvent event) {
		System.out.println("The button was clicked!");
	}
}
