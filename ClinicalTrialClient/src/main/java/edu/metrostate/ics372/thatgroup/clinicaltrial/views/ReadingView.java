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
import java.util.ResourceBundle;

import javax.print.attribute.standard.RequestingUserName;

import org.junit.validator.ValidateWith;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.BloodPressure;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ConstraintsBase;
import javafx.scene.layout.StackPane;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class ReadingView extends AnchorPane implements Initializable {
	private ReadingFormValidator validator;
	private ClinicalTrialViewModel model;
	@FXML
	private Button addReadingBtn;
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
	TextField hour;
	@FXML
	TextField minutes;
	@FXML
	TextField seconds;
	@FXML
	Button okButton;
	@FXML
	StackPane bloodPressStack;
	@FXML
	TextField systolic;
	@FXML
	TextField diastolic;

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
	 * @return the model
	 */
	public ClinicalTrialViewModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(ClinicalTrialViewModel model) {
		this.model = model;
		System.out.println(model);
		// Initialize the reading type choice box
		type.setItems(model.getReadingTypeChoices());
		// Set the reading type choice box default value
		type.getSelectionModel().selectFirst();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		validator = new ReadingFormValidator();
		date.setValue(LocalDate.now());

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

		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (validator.validateInput()) {
					addReading(type.getSelectionModel().getSelectedItem(), id.getText(), value.getText(),
							date.getValue());
				} else {
					PopupNotification.showPopupMessage(
							"There is errors with the input.  Please check all input fields and try again.",
							getScene());
				}
			}
		});
	}

	private void addReading(String rType, String rid, String rVal, LocalDate rDateTime) {
		if (rType.toLowerCase().equals("blood pressure")) {
			rType = "blood_press";
			rVal = String.format("%s/%s", systolic.getText(), diastolic.getText());
		}
		model.addReading(rType, rid, rVal, setReadingDate());
	}

	private LocalDateTime setReadingDate() {
		int hh = 0, mm = 0, ss = 0;
		if (validator.validateTime()) {
				hh = Integer.parseInt(hour.getText());
				mm = Integer.parseInt(minutes.getText());
				ss = Integer.parseInt(seconds.getText());
		}
		return LocalDateTime.of(date.getValue(), LocalTime.of(hh, mm, ss));
	}

	private class ReadingFormValidator {
		private final String INT_INPUT = "^[0-9]*$";
		private final String DOUBLE_INPUT = "[-+]?[0-9]*\\.?[0-9]+";

		private boolean validateInput() {
			boolean answer;
			if (date.getValue() == null) {
				answer = false;
			}
			if (type.getSelectionModel().getSelectedItem().toLowerCase().equals("blood pressure")) {
				answer = isNotEmpty(id) && isNotEmpty(systolic) && isNotEmpty(diastolic) && validateBloodPressure();
			} else if (type.getSelectionModel().getSelectedItem().toLowerCase().equals("temp")) {
				answer = isNotEmpty(id) && isNotEmpty(value) && validateTemp();
			} else {
				answer = isNotEmpty(id) && isNotEmpty(value) && value.getText().matches(INT_INPUT);
			}
			return answer;
		}

		private boolean isNotEmpty(TextField textField) {
			return textField.getText() != null && !textField.getText().isEmpty();
		}

		private boolean validateBloodPressure() {
			return systolic.getText().matches(INT_INPUT) && diastolic.getText().matches(INT_INPUT);
		}

		private boolean validateTemp() {
			return value.getText().matches(INT_INPUT) || value.getText().matches(DOUBLE_INPUT);
		}
		
		private boolean validateTime() {
			return isNotEmpty(hour) && hour.getText().matches(INT_INPUT) && isNotEmpty(minutes)
					&& minutes.getText().matches(INT_INPUT) && isNotEmpty(seconds)
					&& seconds.getText().matches(INT_INPUT);
		}
	}
}
