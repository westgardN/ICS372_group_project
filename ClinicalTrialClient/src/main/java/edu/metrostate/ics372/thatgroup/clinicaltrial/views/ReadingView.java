/**
 * File: ReadingView.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.BloodPressure;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.BloodPressure.BloodPressureValue;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * This view is responsible for taking user input for a new patient reading. It
 * represents the input form required to perform the task of creating and adding
 * a new reading into a patient's journal
 *
 * @author That Group
 */
public class ReadingView extends VBox implements Initializable {
	private enum ErrCause {
		DATE, TIME, DATE_TIME, ID, BP, TEMP, VALUE;
	}

	/*
	 * This class is used for validating the user input, ensuring that the values
	 * entered are of the correct format for their respective fields
	 */
	private class ReadingFormValidator {
		private final String ALPHANUMERIC = "^[a-zA-Z0-9]+$"; 
		private final String INT_INPUT = "^[0-9]*$"; 
		private final String DECIMAL_INPUT = "[-+]?[0-9]*\\.?[0-9]+"; 
		private final String HOURS = "^0*([0-9]|1[0-9]|2[0-3])$"; 
		private final String MIN_SEC = "^0*([0-9]|[1-4][0-9]|5[0-9])$";

		private boolean validateInput() {
			String readingType = type.getSelectionModel().getSelectedItem();
			Clinic clinic = clinicChoice.getSelectionModel().getSelectedItem();
			Patient patient = patientChoice.getSelectionModel().getSelectedItem();
			
			if (readingType == null || clinic == null || patient == null) {
				return false;
			}
			
			readingType = readingType.toLowerCase();
			
			if (!hasValidDate(patient)) {
				generateErrorMessage(ErrCause.DATE);
				return false;
			}
			if (!hasValidTime()) {
				generateErrorMessage(ErrCause.TIME);
				return false;
			}
			if (!hasValidDateTime(patient)) {
				generateErrorMessage(ErrCause.DATE_TIME);
				return false;
			}
			if (!isFilled(id) || !id.getText().matches(ALPHANUMERIC)) {
				generateErrorMessage(ErrCause.ID);
				return false;
			}
			if (readingType.equals(Strings.BP_VALUE) && !validateBloodPressure()) {
				generateErrorMessage(ErrCause.BP);
				return false;
			}
			if (readingType.equals(Strings.TEMP_VALUE) && !validateTemp()) {
				generateErrorMessage(ErrCause.TEMP);
				return false;
			}
			if (!isFilled(value) && !readingType.equals(Strings.TEMP_VALUE) && !readingType.equals(Strings.BP_VALUE)
					|| isFilled(value) && !value.getText().matches(INT_INPUT) && !readingType.equals(Strings.TEMP_VALUE)
							&& !readingType.equals(Strings.BP_VALUE)) {
				generateErrorMessage(ErrCause.VALUE);
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

		private boolean hasValidDate(Patient patient) {
			boolean answer = false;

			if (patient != null) {
				LocalDate ld = date.getValue() != null ? date.getValue() : null;
				LocalDate sd = patient != null ? patient.getTrialStartDate() : null;
	
				if (sd != null && ld != null && !ld.isAfter(LocalDate.now()) && !ld.isBefore(sd)) {
					answer = true;
				}
			}
			return answer;
		}

		private boolean hasValidTime() {
			boolean answer;
			String hh = hour.getText(), mm = minutes.getText(), ss = seconds.getText();
			if (hh.matches(HOURS) && mm.matches(MIN_SEC) && ss.matches(MIN_SEC)) {
				answer = true;
			} else if (hh.equals(Strings.EMPTY) && mm.equals(Strings.EMPTY) && ss.equals(Strings.EMPTY)) {
				answer = true;
			} else {
				answer = false;
			}
			return answer;
		}

		private boolean hasValidDateTime(Patient patient) {
			boolean answer = false;

			if (hasValidDate(patient)) {
				LocalTime lt = getTime();
				LocalDate ld = date.getValue() != null ? date.getValue() : null;

				if (ld != null) {
					LocalDateTime ldt = LocalDateTime.of(ld, lt);
					LocalDateTime now = LocalDateTime.now();

					long seconds = ChronoUnit.SECONDS.between(ldt, now);

					if (seconds >= 0) {
						answer = true;
					}
				}
			}

			return answer;
		}

		private void generateErrorMessage(ErrCause cause) {
			switch (cause) {
			case DATE:
				PopupNotification.showPopupMessage(Strings.ERR_DATE_MSG, getScene());
				break;
			case TIME:
				PopupNotification.showPopupMessage(Strings.ERR_TIME_MSG, getScene());
				break;
			case DATE_TIME:
				PopupNotification.showPopupMessage(Strings.ERR_DATE_TIME_MSG, getScene());
				break;
			case ID:
				PopupNotification.showPopupMessage(Strings.ERR_ID_MSG, getScene());
				break;
			case BP:
				PopupNotification.showPopupMessage(Strings.ERR_BLOOD_PRESSURE_MSG, getScene());
				break;
			case TEMP:
				PopupNotification.showPopupMessage(Strings.ERR_TEMP_MSG, getScene());
				break;
			case VALUE:
				PopupNotification.showPopupMessage(Strings.ERR_VALUE_MSG, getScene());
			default:
				break;
			}
		}
	}

	private ClinicalTrialModel model;
	@FXML
	private ChoiceBox<Clinic> clinicChoice;
	@FXML
	private ChoiceBox<Patient> patientChoice;
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
	private StackPane bloodPressStack;
	@FXML
	private TextField systolic;
	@FXML
	private TextField diastolic;
	
	private ReadingFormValidator validator;
	private boolean selected;
	/**
	 * Constructs a new ReadingView instance
	 */
	public ReadingView() {
		model = null;
		validator = new ReadingFormValidator();
		selected = false;
		
		try (InputStream stream = getClass().getResourceAsStream(Strings.READING_VIEW_FXML)) {
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
		type.setItems(model.getReadingTypes());
		clinicChoice.setItems(model.getClinics());
		patientChoice.setItems(model.getPatients());
		
		model.addPropertyChangeListener((evt) -> {
			switch (evt.getPropertyName()) {
			case ClinicalTrialModel.PROP_SELECTED_READING:
				Reading reading = null;

				if (evt.getNewValue() instanceof Reading) {
					reading = (Reading) evt.getNewValue();
					clear();
					if (reading != null) {
						load(reading);
					}
				}
				
				if (reading == null) {
					clear();
					selected = false;
					okBtn.setText(Strings.ADD);
					id.setDisable(false);
				} else {
					selected = true;
					okBtn.setText(Strings.UPDATE);
					id.setDisable(true);
				}
				break;
			}
		});
	}

	private void load(Reading reading) {
		LocalDateTime ldt = reading.getDate();
		if (ldt != null) {
			date.setValue(ldt.toLocalDate());
			setTime(ldt.toLocalTime());
		}

		id.setText(reading.getId());

		setValue(reading);

		Clinic clinic = new Clinic(reading.getClinicId(), model.getTrialId(), null);
		Patient patient = new Patient(reading.getPatientId(), model.getTrialId(), null, null);
		clinicChoice.getSelectionModel().select(clinic);
		patientChoice.getSelectionModel().select(patient);
		
	}

	private void setValue(Reading reading) {
		String strType = ReadingFactory.getPrettyReadingType(reading);
		type.getSelectionModel().clearSelection();
		type.getSelectionModel().select(strType);
		if (strType.equals(ReadingFactory.PRETTY_BLOOD_PRESSURE)) {
			BloodPressure bp = (BloodPressure) reading;
			BloodPressureValue bpv = bp.getValue() != null ? (BloodPressureValue) bp.getValue() : null;

			if (bpv != null) {
				systolic.setText(String.valueOf(bpv.getSystolic()));
				diastolic.setText(String.valueOf(bpv.getDiastolic()));
			}
		}

		if (reading.getValue() != null) {
			value.setText(reading.getValue().toString());
		}
	}

	/**
	 * Initializes the view/input form and assigns the Action and Event listeners to
	 * the proper GUI components
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		validator = new ReadingFormValidator();
		date.setValue(LocalDate.now());

		type.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSel, newSel) -> {
			if (newSel != null && newSel.toLowerCase().equals(Strings.BP_VALUE)) {
				bloodPressStack.setVisible(true);
				value.setVisible(false);
			} else {
				bloodPressStack.setVisible(false);
				value.setVisible(true);
			}
		});

		okBtn.setOnAction((event) -> {
			if (validator.validateInput()) {
				String msgS = selected ? Strings.READING_UPDATED_MSG : Strings.READING_ADDED_MSG;
				String msgF = selected ? Strings.READING_NOT_UPDATED_MSG : Strings.READING_NOT_ADDED_MSG;
				if (addReading(type.getSelectionModel().getSelectedItem(), id.getText(), value.getText(), LocalDateTime.of(date.getValue(), getTime()))) {
					id.setText(Strings.EMPTY);
					PopupNotification.showPopupMessage(msgS, getScene());
				} else {
					PopupNotification.showPopupMessage(msgF, getScene());
				}
			}
		});
	}

	private boolean addReading(String rType, String rId, String rVal, LocalDateTime rDateTime) {
		boolean answer = false;
		
		String pid = patientChoice.getSelectionModel().getSelectedItem().getId();
		String cid = clinicChoice.getSelectionModel().getSelectedItem().getId();
		Reading reading = ReadingFactory.getReading(rType);
		
		reading.setClinicId(cid);
		reading.setPatientId(pid);
		reading.setId(rId);
		reading.setDate(rDateTime);
		reading.setValue(rVal);
		
		try {
			if (rType.toLowerCase().equals(Strings.BP_VALUE)) {
				reading.setValue(String.format("%s/%s", systolic.getText(), diastolic.getText()));
			}
			answer = model.updateOrAdd(reading);
		} catch (TrialCatalogException e) {
			PopupNotification.showPopupMessage(e.getMessage(), this.getScene());
		}
		
		return answer;
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

	private void setTime(LocalTime lt) {
		hour.setText(String.valueOf(lt.getHour()));
		minutes.setText(String.valueOf(lt.getMinute()));
		seconds.setText(String.valueOf(lt.getSecond()));
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
		type.getSelectionModel().clearSelection();
		patientChoice.getSelectionModel().clearSelection();
		clinicChoice.getSelectionModel().clearSelection();
		okBtn.setText(Strings.ADD);
	}

	/**
	 * Clears the input fields present on the reading input form
	 * 
	 * @param event
	 *            the triggering entity of this action
	 */
	public void clearForm(ActionEvent event) {
		clear();
	}

}
