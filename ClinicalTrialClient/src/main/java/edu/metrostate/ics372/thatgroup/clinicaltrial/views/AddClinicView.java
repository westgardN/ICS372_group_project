/**
 * File: AddClinicView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * This view is responsible for displaying the form to add a new clinic to the
 * system
 * 
 * @author That Group
 *
 */

public class AddClinicView extends AnchorPane implements Initializable {
	@FXML
	private TextField clinicId;
	@FXML
	private TextField clinicName;
	@FXML
	private Button addButton;
	private ClinicalTrialModel model;
	private boolean clear;

	/**
	 * Constructs a new AddClinicView instance
	 */
	public AddClinicView() {
		model = null;
		clear = false;
		try (InputStream stream = getClass().getResourceAsStream(Strings.ADD_CLINIC_VIEW_FXML)) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException exception) {
			throw new RuntimeException(exception);
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
	 * @param model model to set
	 */
	public void setModel(ClinicalTrialModel model) {
		this.model = model;
	}
	
	/**
	 * Adds a new clinic to the system
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	@FXML
	public void addClinic(ActionEvent event){
		try {
			if (model.addClinic(clinicId.getText().trim(),clinicName.getText().trim())) {
				PopupNotification.showPopupMessage(Strings.CLINIC_ADDED_MSG, getScene());
				clear = true;
				clinicId.setText(Strings.EMPTY);
				clinicName.setText(Strings.EMPTY);
			} else {
				PopupNotification.showPopupMessage(Strings.CLINIC_NOT_ADDED_MSG, getScene());
			}
		} catch (TrialCatalogException e) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(Strings.CLINIC_NOT_ADDED_MSG);
			sb.append("\nReceived Error: ");
			sb.append(e.getMessage());
			PopupNotification.showPopupMessage(sb.toString(), getScene());
		}
	}
	/**
	 * Initializes this view and sets action events and listeners to the new clinic
	 * ID TextField, new clinic name TextField and the Add Button
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addButton.setDisable(true);

		clinicId.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				if (validate(clinicId.getText())) {
					addClinic(null);
				}
			}
		});
		clinicId.textProperty().addListener((observable, oldValue, newValue) -> {
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
	/**
	 * Checks the string text to see if only contains letters or numbers.
	 * 
	 * @param text string to be validated
	 * @return answer true if text passed validation, false if it does not.
	 */
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
