/**
 * File: AddPatientView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Vincent J. Palodichuk
 *
 */
public class AddPatientView extends AnchorPane implements Initializable {
	@FXML private TextField textField;
	@FXML private Button addButton;
	private ClinicalTrialViewModel model;
	
	public AddPatientView() {
		model = null;
		
		try (InputStream stream = getClass().getResourceAsStream("AddPatientView.fxml")) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException exception) {
			throw new RuntimeException(exception);
		}
		
		addButton.setDisable(true);
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
	}

	@FXML
	public void addPatient(ActionEvent event) {
		if (model.addPatient(textField.getText(), null)) {
			PopupNotification.showPopupMessage("New Patient Added", getScene());
			textField.setText("");
		} else {
			PopupNotification.showPopupMessage("Unable to add patient to the trial.", getScene());
		}		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		textField.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				if (canEnableAddButton()) {
					addPatient(null);
				}
			}
		});
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if ((oldValue == null || oldValue.trim().isEmpty()) && (newValue != null && !newValue.trim().isEmpty())) {
				if (canEnableAddButton()) {
					addButton.setDisable(false);
				}
			} else if ((oldValue != null && !oldValue.trim().isEmpty()) && (newValue == null || newValue.trim().isEmpty())) {
				addButton.setDisable(true);
			}
		});
	}
	
	private boolean canEnableAddButton() {
		return model != null && textField.getText() != null && !textField.getText().trim().isEmpty();
	}
}
