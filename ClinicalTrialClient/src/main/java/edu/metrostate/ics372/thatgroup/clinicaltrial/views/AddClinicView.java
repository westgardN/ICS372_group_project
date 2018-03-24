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
import javafx.scene.layout.VBox;

public class AddClinicView extends VBox implements Initializable {
	@FXML
	private TextField textField;
	@FXML
	private Button addButton;
	private ClinicalTrialModel model;
	private boolean clear;

	/**
	 * Constructs a new AddPatientView instance
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

	public ClinicalTrialModel getModel() {
		return model;
	}

	public void setModel(ClinicalTrialModel model) {
		this.model = model;
	}
	
	@FXML
	public void addClinic(ActionEvent event){
		try {
			if (model.addClinic(textField.getText().trim(),textField.getText().trim())) {
				PopupNotification.showPopupMessage(Strings.CLINIC_ADDED_MSG, getScene());
				clear = true;
				textField.setText(Strings.EMPTY);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addButton.setDisable(true);

//		textField.setOnKeyPressed((event) -> {
//			if (event.getCode() == KeyCode.ENTER) {
//				if (validate(textField.getText())) {
//					//addClinic(void);
//				}
//			}
//		});
//		textField.textProperty().addListener((observable, oldValue, newValue) -> {
//			if (validate(newValue) && addButton.isDisabled()) {
//				addButton.setDisable(false);
//			} else if (!validate(newValue) && !addButton.isDisabled()) {
//				addButton.setDisable(true);
//				if (!clear) {
//					PopupNotification.showPopupMessage(Strings.SPECIAL_CHAR_MSG, getScene());
//				}
//				clear = false;
//			}
//		});
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
