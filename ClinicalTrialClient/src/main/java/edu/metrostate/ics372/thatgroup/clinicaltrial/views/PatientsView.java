/**
 * File: PatientsView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.File;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * This view is responsible for displaying the patients in a ListView for the
 * user to select to view or add readings
 * 
 * @author That Group
 *
 */
public class PatientsView extends AnchorPane implements Initializable {
	@FXML
	private ListView<Patient> listView;
	private ClinicalTrialModel model;
	private ListProperty<Patient> patientsProperty;
	@FXML
	private Button startPtTrial;
	@FXML
	private Button endPtTrial;

	/**
	 * Constructs a new PatientsView instance
	 */
	public PatientsView() {
		model = null;
		patientsProperty = new SimpleListProperty<>();

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
			LocalDate startDate = getStartDate(patient);
			if (startDate != null) {
				patient.setTrialStartDate(startDate);
				patient.setTrialEndDate(null);
				try {
					model.update(patient);
				} catch (TrialCatalogException ex) {
					PopupNotification.showPopupMessage(ex.getMessage(), this.getScene());
				}
			}
			updateButtons(patient, true);
		}
	}

	private LocalDate getStartDate(Patient patient) {
		LocalDate answer = null;
		
		Dialog<LocalDate> dialog = new Dialog<>();
		dialog.initOwner(this.getScene().getWindow());
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		dialog.setTitle("Select Trial Start Date for Patient " + patient.getId());
		dialog.setHeaderText("Please select the date the patient started the trial");
		URL url = getClass().getResource(".." + File.separator + Strings.LOGO_PATH);
		String img = url.toString();
		dialog.setGraphic(new ImageView(img));
		
		ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		
		DatePicker datePicker = new DatePicker(patient.getTrialStartDate() != null ? patient.getTrialStartDate() : LocalDate.now());
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
			LocalDate endDate = LocalDate.now();
			patient.setTrialEndDate(endDate);
			try {
				model.update(patient);
			} catch (TrialCatalogException ex) {
				PopupNotification.showPopupMessage(ex.getMessage(), this.getScene());
			}
			updateButtons(patient, true);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startPtTrial.setDisable(true);
		endPtTrial.setDisable(true);
	}
}
