/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class ReadingView extends AnchorPane {
	private ReadingFormValidator validator;
	private ClinicalTrialViewModel model;
	@FXML
	private Button addReadingBtn;
	@FXML
	private VBox inputForm;
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
	private Button okButton;
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
		setListenersAndEventHandlers();
		validator = new ReadingFormValidator();
		date.setValue(LocalDate.now());
		inputForm.setVisible(false);
		type.setItems(model.getReadingTypeChoices());
		type.getSelectionModel().selectFirst();
	}

	private void setListenersAndEventHandlers() {

		model.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (model.getSelectedPatient() != null) {
					patientId.setText(model.getSelectedPatient().getId());
					clearForm();
				}
				if (!model.getTrial().getPatients().contains(model.getSelectedPatient())) {
					inputForm.setVisible(false);
				}
			}
		});

		type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldSel, String newSel) {
				if (newSel.equals("Blood Pressure")) {
					bloodPressStack.setVisible(true);
					value.setVisible(false);
				} else {
					bloodPressStack.setVisible(false);
					value.setVisible(true);
				}
			}
		});

		addReadingBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (model.getTrial().getPatients().contains(model.getSelectedPatient())) {
					inputForm.setVisible(true);
				} else {
					PopupNotification.showPopupMessage("This patient is not active in the clinical trial.", getScene());
				}
			}
		});

		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (validator.validateInput()) {
					addReading(type.getSelectionModel().getSelectedItem(), id.getText(), value.getText(),
							date.getValue());
				}
			}
		});
	}

	private void addReading(String rType, String rId, String rVal, LocalDate rDateTime) {
		if (rType.toLowerCase().equals("blood pressure")) {
			rType = "blood_press";
			rVal = String.format("%s/%s", systolic.getText(), diastolic.getText());
		}
		model.addReading(rType, rId, rVal, LocalDateTime.of(rDateTime, getTime()));
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

	/**
	 * This is super ugly, however, since some of the nodes are wrapped in other
	 * containers within the same VBox iterating over all the parents would be quite
	 * tedious. This is the most straightforward way that I can see to accomplish
	 * this.
	 */
	private void clearForm() {
		id.clear();
		value.clear();
		systolic.clear();
		diastolic.clear();
		date.setValue(LocalDate.now());
		hour.clear();
		minutes.clear();
		seconds.clear();
	}
}
