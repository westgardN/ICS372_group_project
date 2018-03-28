/**
 * File: ClinicsView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * This view displays the clinics in a list which the user can select
 * to view all readings from that clinic.
 * 
 * @author That Group
 *
 */ 
public class ClinicsView extends VBox implements Initializable {
	private static final int MAX_CLINIC_ID = 32;
	private static final int MAX_CLINIC_NAME = 255;
	private static final String REGEX_NO_SPECIAL_CHARS = "^[A-Za-z0-9_]+$";
	private static final String REGEX_NO_SPECIAL_CHARS_ALLOW_SPACES = "^[A-Za-z0-9_\\s]+$";
	@FXML
	private TextField clinicId;
	@FXML
	private TextField clinicName;
	@FXML
	private Button addButton;
	@FXML
	private ListView<Clinic> listView;
	private boolean selected;
	private ClinicalTrialModel model;
	private ListProperty<Clinic> clinicsProperty;

	/**
	 * Constructs a new ClinicsView instance
	 */
	
	public ClinicsView() {
		model = null;
		selected = false;
		clinicsProperty = new SimpleListProperty<>();

		try (InputStream stream = getClass().getResourceAsStream(Strings.CLINICS_VIEW_FXML)) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Returns the model associated with this view.
	 * @return the model
	 */
	public ClinicalTrialModel getModel() {
		return model;
	}
	
	/**
	 * Sets the view model associated with this view and adds listeners for the list
	 * of clinics and the currently selected clinic from the list.
	 * 
	 * @param model model to set
	 */
	public void setModel(ClinicalTrialModel model) {
		this.model = model;

		clinicsProperty.set(model.getClinics());

		listView.itemsProperty().bind(clinicsProperty);

		listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			int index = newValue.intValue();

			try {
				if (index >= 0 && index < clinicsProperty.size() && clinicsProperty.get(index) instanceof Clinic) {
					Clinic clinic = clinicsProperty.get(index);
					if (!Objects.equals(clinic, model.getSelectedClinic())) {
						model.setSelectedClinic(clinic);
					}

				} else if (oldValue != null) {
					model.setSelectedClinic(null);
				}
			} catch (TrialCatalogException e) {
				PopupNotification.showPopupMessage(e.getMessage(), this.getScene());
			}
		});
		
		model.addPropertyChangeListener((evt) -> {
			switch (evt.getPropertyName()) {
			case ClinicalTrialModel.PROP_SELECTED_CLINIC:
				Clinic clinic = null;

				if (evt.getNewValue() instanceof Clinic) {
					clinic = (Clinic) evt.getNewValue();
					clear();
					if (clinic != null) {
						load(clinic);
					}
				}
				
				if (clinic == null) {
					clear();
					selected = false;
					addButton.setText(Strings.ADD);
					clinicId.setDisable(false);
				} else {
					selected = true;
					addButton.setText(Strings.UPDATE);
					clinicId.setDisable(true);
				}
				break;
			}
		});
	}

	private void load(Clinic clinic) {
		clinicId.setText(clinic.getId());
		clinicName.setText(clinic.getName());
	}
	
	private void clear() {
		clinicId.setDisable(false);
		clinicId.setText(Strings.EMPTY);
		clinicName.setText(Strings.EMPTY);
		addButton.setText(Strings.ADD);

	}
	
	@FXML
	public void addClinic(ActionEvent event) {
		String msgS = selected ? Strings.CLINIC_UPDATED_MSG : Strings.CLINIC_ADDED_MSG;
		String msgF = selected ? Strings.CLINIC_NOT_UPDATED_MSG : Strings.CLINIC_NOT_ADDED_MSG;
		try {
			Clinic clinic = new Clinic(clinicId.getText().trim(), model.getTrialId(), clinicName.getText().trim());
			if (model.updateOrAdd(clinic)) {
				PopupNotification.showPopupMessage(msgS, getScene());
				clear();
			} else {
				PopupNotification.showPopupMessage(msgF, getScene());
			}
		} catch (TrialCatalogException e) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(msgF);
			sb.append("\n");
			sb.append(Strings.ERR_RECEIVED_MSG);
			sb.append(e.getMessage());
			PopupNotification.showPopupMessage(sb.toString(), getScene());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addButton.setDisable(true);

		clinicId.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				if (validate()) {
					addClinic(null);
				}
			}
		});
		
		clinicName.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				if (validate()) {
					addClinic(null);
				}
			}
		});
		
		clinicId.textProperty().addListener((observable, oldValue, newValue) -> {
			if (validate(newValue, MAX_CLINIC_ID, false) && validate(clinicName.getText(), MAX_CLINIC_NAME, true) && addButton.isDisabled()) {
				addButton.setDisable(false);
			} else if (!validate(newValue, MAX_CLINIC_ID, false) && !addButton.isDisabled()) {
				addButton.setDisable(true);
				if (newValue != null && !newValue.trim().isEmpty()) {
					PopupNotification.showPopupMessage(Strings.SPECIAL_CHAR_MSG, getScene());
				}
			}
		});
		
		clinicName.textProperty().addListener((observable, oldValue, newValue) -> {
			if (validate(newValue, MAX_CLINIC_NAME, true) && validate(clinicId.getText(), MAX_CLINIC_ID, false) && addButton.isDisabled()) {
				addButton.setDisable(false);
			} else if (!validate(newValue, MAX_CLINIC_NAME, true) && !addButton.isDisabled()) {
				addButton.setDisable(true);
				if (newValue != null && !newValue.trim().isEmpty()) {
					PopupNotification.showPopupMessage(Strings.SPECIAL_CHAR_MSG, getScene());
				}
			}
		});
	}
	
	private boolean validate() {
		boolean answer = false;
		
		if (validate(clinicId.getText(), MAX_CLINIC_ID, false) && validate(clinicName.getText(), MAX_CLINIC_NAME, true)) {
			answer = true;
		}
		
		return answer;
	}
	
	private boolean validate(String text, int maxLength, boolean allowSpace) {
		boolean answer = false;
		String matchString = allowSpace ? REGEX_NO_SPECIAL_CHARS_ALLOW_SPACES : REGEX_NO_SPECIAL_CHARS;
		
		if (text != null && !text.trim().isEmpty() && text.trim().length() <= maxLength) {
			if (text.matches(matchString)) {
				answer = true;
			}
		}
		
		return answer;
	}
	
	/**
	 * Clears the input fields present on the input form and clears the
	 * current selection in the list
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void clearForm(ActionEvent event) {
		int index = listView.getSelectionModel().getSelectedIndex();
		
		if (index >= 0 && index < clinicsProperty.size() && listView.getSelectionModel().isSelected(index)) {
			listView.getSelectionModel().clearSelection(index);
		}
		clear();
	}

}
