/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.patient.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class ReadingView extends AnchorPane implements Initializable {
	private class ReadingFormValidator {
		private final String INT_INPUT = "^[0-9]*$"; // Only numbers
		private final String DECIMAL_INPUT = "[-+]?[0-9]*\\.?[0-9]+"; // Only numbers or decimals
		private final String HOURS = "^0*([0-9]|1[0-9]|2[0-3])$"; // Only numbers between 0 & 23
		private final String MIN_SEC = "^0*([0-9]|[1-4][0-9]|5[0-9])$"; // Only numbers between 0 & 59

		private boolean validateInput() {
			String readingType = type.getSelectionModel().getSelectedItem().toLowerCase();
			if (date.getValue() == null) {
				generateErrorMessage("date");
				return false;
			}
			if (!hasValidTime()) {
				generateErrorMessage("time");
				return false;
			}
			if (!isFilled(id)) {
				generateErrorMessage("id");
				return false;
			}
			if (readingType.equals("blood pressure") && !validateBloodPressure()) {
				generateErrorMessage("blood_press");
				return false;
			}
			if (readingType.equals("temp") && !validateTemp()) {
				generateErrorMessage("temp");
				return false;
			}
			if (!isFilled(value) && !readingType.equals("temp") && !readingType.equals("blood pressure")
					|| isFilled(value) && !value.getText().matches(INT_INPUT) && !readingType.equals("temp")
							&& !readingType.equals("blood pressure")) {
				generateErrorMessage("value");
				return false;
			}
			return true;
		}

		private boolean isFilled(TextField textField) {
			return textField.getText() != null && !textField.getText().isEmpty();
		}

		private boolean validateBloodPressure() {
			return isFilled(systolic) && systolic.getText().matches(INT_INPUT) && isFilled(diastolic)
					&& diastolic.getText().matches(INT_INPUT);
		}

		private boolean validateTemp() {
			return isFilled(value) && value.getText().matches(INT_INPUT)
					|| isFilled(value) && value.getText().matches(DECIMAL_INPUT);
		}

		private boolean timeIsNotEmpty() {
			return isFilled(hour) && isFilled(minutes) && isFilled(seconds);
		}

		private boolean hasValidTime() {
			boolean answer;
			String hh = hour.getText(), mm = minutes.getText(), ss = seconds.getText();
			if (hh.matches(HOURS) && mm.matches(MIN_SEC) && ss.matches(MIN_SEC)) {
				answer = true;
			} else if (hh.equals("") && mm.equals("") && ss.equals("")) {
				answer = true;
			} else {
				answer = false;
			}
			return answer;
		}

		private void generateErrorMessage(String cause) {
			switch (cause) {
			case "date":
				PopupNotification.showPopupMessage("Date can not be empty.", getScene());
				break;
			case "time":
				PopupNotification.showPopupMessage(
						"Invalid time detected.  Please check the time input fields for errors.", getScene());
				break;
			case "id":
				PopupNotification.showPopupMessage(
						"Invalid reading id detected.  Please check the reading id input fields for errors.",
						getScene());
				break;
			case "blood_press":
				PopupNotification.showPopupMessage(
						"Invalid blood pressure detected.  Please check the blood pressure input fields for errors.",
						getScene());
				break;
			case "temp":
				PopupNotification.showPopupMessage(
						"Invalid temperature detected.  Please check the reading value input field for errors.",
						getScene());
				break;
			case "value":
				PopupNotification.showPopupMessage(
						"Invalid input detected.  Please check the reading value input field for errors.", getScene());
				break;
			}
		}
	}
	
	private ReadingFormValidator validator;
	private ClinicalTrialViewModel model;
	@FXML
	private Button addBtn;
	@FXML
	private VBox form;
	@FXML
	private TextField patientId;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private TextField id;
	@FXML
	private TextField value;
	@FXML
	private DatePicker date;
	@FXML
	private TextField hour;
	@FXML
	private TextField minutes;
	@FXML
	private TextField seconds;
	@FXML
	private Button okBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private StackPane bloodPressStack;
	@FXML
	private TextField systolic;
	@FXML
	private TextField diastolic;

	/**
	 * Constructs a new ReadingView instance
	 */
	public ReadingView() {
		model = null;

		try (InputStream stream = getClass().getResourceAsStream("ReadingView.fxml")) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Gets the view model associated with this view
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
		type.setItems(model.getReadingTypeChoices());
		type.getSelectionModel().selectFirst();		
		
		model.addPropertyChangeListener((evt) -> {
			if (Objects.equals(evt.getPropertyName(), "selectedPatient")) {
				Patient patient = null;
				
				if (evt.getNewValue() instanceof Patient) {
					patient = (Patient) evt.getNewValue();
				}
				
				if (patient != null && model.isPatientInTrial(patient)) {
					patientId.setText(model.getSelectedPatient().getId());
					addBtn.setDisable(false);
				} else {
					addBtn.setDisable(true);
				}
			}
		});

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		validator = new ReadingFormValidator();
		date.setValue(LocalDate.now());
		form.setVisible(false);
		addBtn.setDisable(true);

		type.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSel, newSel) -> {
			if (newSel.equals("Blood Pressure")) {
				bloodPressStack.setVisible(true);
				value.setVisible(false);
			} else {
				bloodPressStack.setVisible(false);
				value.setVisible(true);
			}
		});

		addBtn.setOnAction((event) -> {
			Patient patient = model.getSelectedPatient();
			
			if (model.isPatientInTrial(patient)) {
				form.setVisible(true);
				addBtn.setDisable(true);
			} else {
				PopupNotification.showPopupMessage("This patient is not active in the clinical trial.", getScene());
			}
		});

		okBtn.setOnAction((event) -> {
			if (validator.validateInput()) {
				if (addReading(type.getSelectionModel().getSelectedItem(), id.getText(), value.getText(), date.getValue())) {
					model.fireUpdatePatient(patientId.getText());
					PopupNotification.showPopupMessage("Reading has been added.", getScene());
				}
			}
		});
		
		cancelBtn.setOnAction((event) -> {
			Patient patient = model.getSelectedPatient();
			clear();
			
			form.setVisible(false);
			
			if (model.isPatientInTrial(patient)) {
				addBtn.setDisable(false);
			} else {
				addBtn.setDisable(true);
			}
		});
	}
	

	private boolean addReading(String rType, String rId, String rVal, LocalDate rDateTime) {
		if (rType.toLowerCase().equals("blood pressure")) {
			rType = "blood_press";
			rVal = String.format("%s/%s", systolic.getText(), diastolic.getText());
		}
		return model.addReading(rType, rId, rVal, LocalDateTime.of(rDateTime, getTime()));
	}

	private LocalTime getTime() {
		if (validator.timeIsNotEmpty()) {
			int hh = Integer.parseInt(hour.getText());
			int mm = Integer.parseInt(minutes.getText());
			int ss = Integer.parseInt(seconds.getText());
			return LocalTime.of(hh, mm, ss);
		} else {
			return LocalTime.now();
		}
	}

	private void clear() {
		id.clear();
		value.clear();
		systolic.clear();
		diastolic.clear();
		date.setValue(LocalDate.now());
		hour.clear();
		minutes.clear();
		seconds.clear();
		okBtn.setDisable(true);
	}
	
	public void clearForm(ActionEvent event) {
		clear();
	}

}
