/**
 * File: PatientsView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.InputStream;
import java.net.URL;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * This view is responsible for displaying the patients in a ListView for the
 * user to select to view or add readings
 * 
 * @author That Group
 *
 */
public class PatientsView extends VBox implements Initializable {
	@FXML
	private TextField textField;
	@FXML
	private Button addButton;
	@FXML
	private ListView<Patient> listView;
	@FXML
	private Button startPtTrial;
	@FXML
	private Button endPtTrial;
	private ClinicalTrialModel model;
	private ListProperty<Patient> patientsProperty;

	/**
	 * Constructs a new PatientsView instance
	 */
	public PatientsView() {
		model = null;
		patientsProperty = new SimpleListProperty<>();

		try (InputStream stream = getClass().getResourceAsStream(Strings.PATIENTS_VIEW_FXML)) {
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
	 * Sets the view model associated with this view and adds listeners for the list
	 * of patients and the currently selected patient from the list of patients
	 * 
	 * @param model
	 *            the model to set
	 */
	public void setModel(ClinicalTrialModel model) {
		this.model = model;

		patientsProperty.set(model.getPatients());

		this.model.addPropertyChangeListener((event) -> {
			String prop = event.getPropertyName();
			if (prop.equals(ClinicalTrialModel.PROP_SELECTED_PATIENT)) {
				if (event.getNewValue() instanceof Patient) {
					Patient patient = (Patient) event.getNewValue();

					if (event.getOldValue() == null) {
						updateButtons(patient, false);
					} else {
						updateButtons(patient, true);
					}
				}
			} else if (prop.equals(ClinicalTrialModel.PROP_UPDATE_PATIENT)) {
				if (event.getNewValue() instanceof Patient) {
					updatePatient((Patient) event.getNewValue());
				}
			}

		});

		listView.itemsProperty().bind(patientsProperty);

		listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			int index = newValue.intValue();

			try {
				if (index >= 0 && index < patientsProperty.size() && patientsProperty.get(index) instanceof Patient) {
					Patient patient = patientsProperty.get(index);
					if (!Objects.equals(patient, model.getSelectedPatient())) {
							model.setSelectedPatient(patient);
					}
	
				} else {
					model.setSelectedPatient(null);
				}
			} catch (TrialCatalogException e) {
				PopupNotification.showPopupMessage(e.getMessage(), this.getScene());
			}
		});
	}

	private void updateButtons(Patient patient, boolean updatePatient) {
		if (updatePatient && patient != null) {
			updatePatient(patient);
		}

		if (!model.hasPatientStartedTrial(patient)) {
			startPtTrial.setDisable(false);
			endPtTrial.setDisable(true);
		} else if (model.isPatientInTrial(patient)) {
			endPtTrial.setDisable(false);
			startPtTrial.setDisable(true);
		}
	}

	private void updatePatient(Patient patient) {
		int index = patientsProperty.indexOf(patient);

		if (index >= 0) {
			int selected = listView.getSelectionModel().getSelectedIndex();
			patientsProperty.set(index, null);
			patientsProperty.set(index, patient);
			listView.getSelectionModel().select(selected);
		}
	}

	/**
	 * Starts the clinical trial for the selected trial
	 * 
	 * @param e
	 *            the triggering entity of this action
	 */
	public void startPtTrial(ActionEvent e) {
		Patient patient = model.getSelectedPatient();
		if (patient != null) {
			LocalDate startDate = getTrialDate(patient, true);
			if (startDate != null) {
				patient.setTrialStartDate(startDate);
				patient.setTrialEndDate(null);
				try {
					model.updateOrAdd(patient);
				} catch (TrialCatalogException ex) {
					PopupNotification.showPopupMessage(ex.getMessage(), this.getScene());
				}
			}
			updateButtons(patient, true);
		}
	}

	private LocalDate getTrialDate(Patient patient, boolean start) {
		LocalDate answer = null;
		
		Dialog<LocalDate> dialog = new Dialog<>();
		dialog.initOwner(this.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		String action = start ? "Start" : "End";
		String title = String.format(Strings.SELECT_PATIENT_TRIAL_DATE_TITLE_FMT, action, patient.getId());
		
		dialog.setTitle(title);
		
		action = start ? "started" : "ended";
		String header = String.format(Strings.SELECT_PATIENT_TRIAL_DATE_LABEL_FMT, patient.getId(), action);
		
		dialog.setHeaderText(header);
		
		URL url = getClass().getResource(Strings.LOGO_PATH);
		String img = url.toString();
		
		dialog.setGraphic(new ImageView(img));
		
		ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		
		LocalDate ld = start ? patient.getTrialStartDate() : patient.getTrialEndDate();
		DatePicker datePicker = new DatePicker(ld != null ? ld : LocalDate.now());
		
		datePicker.setMaxWidth(Double.MAX_VALUE);
		datePicker.setMaxHeight(Double.MAX_VALUE);
		
		VBox expContent = new VBox(datePicker);
		expContent.setMaxWidth(Double.MAX_VALUE);
		
		dialog.getDialogPane().setContent(expContent);
		
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == okButtonType) {
				return datePicker.getValue();
			} else {
				return null;
			}
		});
		
		Optional<LocalDate> result = dialog.showAndWait();
		
		if (result.isPresent()) {
			answer = result.get();
		}
		
		return answer;
	}
	/**
	 * Ends the trial for the currently selected patient
	 * 
	 * @param e
	 *            the triggering entity of this action
	 */
	public void endPtTrial(ActionEvent e) {
		Patient patient = model.getSelectedPatient();
		if (patient != null) {
			LocalDate endDate = getTrialDate(patient, false);
			if (isDateOnOrAfter(endDate, patient.getTrialStartDate()) && isDateOnOrBefore(endDate, LocalDate.now())) {
				patient.setTrialEndDate(endDate);
				try {
					model.updateOrAdd(patient);
				} catch (TrialCatalogException ex) {
					PopupNotification.showPopupMessage(ex.getMessage(), this.getScene());
				}
			} else {
				if (endDate != null) {
					PopupNotification.showPopupMessage(Strings.ERR_DATE_MSG, this.getScene());
				}
			}
			updateButtons(patient, true);
		}
	}

	private boolean isDateOnOrAfter(LocalDate ldA, LocalDate ldB) {
		boolean answer = false;
		
		if (ldA != null && ldB != null) {
			if (ldA.isEqual(ldB) || ldA.isAfter(ldB)) {
				answer = true;
			}
		} else if (ldA != null && ldB == null) {
			answer = true;
		}
		
		return answer;		
	}
	
	private boolean isDateOnOrBefore(LocalDate ldA, LocalDate ldB) {
		boolean answer = false;
		
		if (ldA != null && ldB != null) {
			if (ldA.isEqual(ldB) || ldA.isBefore(ldB)) {
				answer = true;
			}
		} else if (ldA != null && ldB == null) {
			answer = true;
		}
		
		return answer;		
	}
	
	private void clear() {
		textField.setDisable(false);
		textField.setText(Strings.EMPTY);
		addButton.setText(Strings.ADD);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startPtTrial.setDisable(true);
		endPtTrial.setDisable(true);
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
				if (newValue != null && !newValue.trim().isEmpty()) {
					PopupNotification.showPopupMessage(Strings.SPECIAL_CHAR_MSG, getScene());
				}
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
	
	/**
	 * Clears the input fields present on the input form and clears the
	 * current selection in the list
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void clearForm(ActionEvent event) {
		int index = listView.getSelectionModel().getSelectedIndex();
		
		if (index >= 0 && index < patientsProperty.size() && listView.getSelectionModel().isSelected(index)) {
			listView.getSelectionModel().clearSelection(index);
		}
		clear();
	}

}
