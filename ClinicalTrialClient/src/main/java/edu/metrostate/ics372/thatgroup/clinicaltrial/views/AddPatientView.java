/**
 * File: AddPatientView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class AddPatientView extends AnchorPane implements Initializable {
	@FXML private TextField textField;
	@FXML private DatePicker datePicker;
	@FXML private Button addButton;
	private ObservableList<Patient> patients;
	
	public AddPatientView() {
		patients = null;
		
		try (InputStream stream = getClass().getResourceAsStream("AddPatientView.fxml")) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException exception) {
			throw new RuntimeException(exception);
		}
		
		addButton.setDisable(false);
	}
	
	@FXML
	public void addPatient(ActionEvent event) {
		System.out.println("The button was clicked!");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		datePicker.setValue(LocalDate.now());
		
	}
}
