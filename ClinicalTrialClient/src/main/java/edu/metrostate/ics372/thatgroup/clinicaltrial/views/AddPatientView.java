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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * This view is responsible for displaying the form to add a new patient to the
 * system
 * 
 * @author That Group
 *
 */
public class AddPatientView extends AnchorPane implements Initializable {
	@FXML
	private TextField textField;
	@FXML
	private Button addButton;
	private ClinicalTrialModel model;
	private boolean clear;

	/**
	 * Constructs a new AddPatientView instance
	 */
	public AddPatientView() {
		model = null;
		clear = false;
		try (InputStream stream = getClass().getResourceAsStream(Strings.ADD_PATIENT_VIEW_FXML)) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns the view model associated with this view
	 * 
	 * @return the model
	 */
	public ClinicalTrialModel getModel() {
		return model;
	}

	/**
	 * Sets the view model associated with this view
	 * 
	 * @param model
	 *            the model to set
	 */

	public void setModel(ClinicalTrialModel model) {
		this.model = model;
	}

	/**
	 * Adds a new patient to the system, but does not automatically add the patient
	 * to the trial as an active member
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	@FXML
	public void addPatient(ActionEvent event) {
		try {
			if (model.addPatient(textField.getText().trim())) {
				PopupNotification.showPopupMessage(Strings.PATIENT_ADDED_MSG, getScene());
				clear = true;
				textField.setText(Strings.EMPTY);
			} else {
				PopupNotification.showPopupMessage(Strings.PATIENT_NOT_ADDED_MSG, getScene());
			}
		} catch (TrialCatalogException e) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(Strings.PATIENT_NOT_ADDED_MSG);
			sb.append("\nReceived Error: ");
			sb.append(e.getMessage());
			PopupNotification.showPopupMessage(sb.toString(), getScene());
		}
	}

	/**
	 * Initializes this view and sets action events and listeners to the new patient
	 * ID TextField and the Add Button
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addButton.setDisable(true);

		textField.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				if (validate(textField.getText())) {
					addPatient(null);
				}
			}
		});
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (validate(newValue) && addButton.isDisabled()) {
				addButton.setDisable(false);
			} else if (!validate(newValue) && !addButton.isDisabled()) {
				addButton.setDisable(true);
				if (!clear) {
					PopupNotification.showPopupMessage(Strings.SPECIAL_CHAR_MSG, getScene());
				}
				clear = false;
			}
		});
	}

	private boolean validate(String text) {
		boolean answer = false;
		
		if (model != null && text != null && !text.trim().isEmpty()) {
			if (text.matches("^[A-Za-z0-9_]+$")) {
				answer = true;
			}
		}
		
		return answer;
	}
}
