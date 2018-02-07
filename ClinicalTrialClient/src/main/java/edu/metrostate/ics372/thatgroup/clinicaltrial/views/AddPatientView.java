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
 * This view is responsible for displaying the form to add a new patient to the
 * system
 * 
 * @author Vincent J. Palodichuk
 *
 */
public class AddPatientView extends AnchorPane implements Initializable {
	@FXML
	private TextField textField;
	@FXML
	private Button addButton;
	private ClinicalTrialViewModel model;

	/**
	 * Constructs a new AddPatientView instance
	 */
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
	 * Returns the view model associated with this view
	 * 
	 * @return the model
	 */
	public ClinicalTrialViewModel getModel() {
		return model;
	}

	/**
	 * Sets the view model associated with this view
	 * 
	 * @param model
	 *            the model to set
	 */

	public void setModel(ClinicalTrialViewModel model) {
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
		if (model.addPatient(textField.getText(), null)) {
			PopupNotification.showPopupMessage(StringResource.PATIENT_ADDED_MSG.get(), getScene());
			textField.setText(StringResource.EMPTY.get());
		} else {
			PopupNotification.showPopupMessage(StringResource.PATIENT_NOT_ADDED_MSG.get(), getScene());
		}
	}

	/**
	 * Initializes this view and sets action events and listeners to the new patient
	 * ID TextField and the Add Button
	 */
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
			} else if ((oldValue != null && !oldValue.trim().isEmpty())
					&& (newValue == null || newValue.trim().isEmpty())) {
				addButton.setDisable(true);
			}
		});
	}

	private boolean canEnableAddButton() {
		return model != null && textField.getText() != null && !textField.getText().trim().isEmpty();
	}
}
