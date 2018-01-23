package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import edu.metrostate.ics372.thatgroup.clinicaltrial.impl.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.impl.ReadingType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class InputReadingController {
	@FXML
	StackPane inputReadingView;
	@FXML
	Label patientIDLbl;
	@FXML
	TextField patientIDTxt;
	@FXML
	Label readingTypeLbl;
	@FXML
	TextField readingTypeTxt;
	@FXML
	Label readingIDLbl;
	@FXML
	TextField readingIDTxt;
	@FXML
	Label readingValueLbl;
	@FXML
	TextField readingValueTxt;
	@FXML
	Label readingDateLbl;
	@FXML
	TextField readingDateTxt;
	@FXML
	Button submitBtn;
	@FXML
	Label resultLbl;
	@FXML
	TextArea resultTxt;

	public InputReadingController() {

	}

	@FXML
	void showReading() {
		ReadingType readingType = null;
		if (!patientIDTxt.getText().equals("") && !readingTypeTxt.getText().equals("")
				&& !readingIDTxt.getText().equals("") && !readingValueTxt.getText().equals("")
				&& !readingDateTxt.getText().equals("")) {

			LocalDateTime date = Instant.ofEpochMilli(Long.parseLong(readingDateTxt.getText()))
					.atZone(ZoneId.systemDefault()).toLocalDateTime();
			for (ReadingType readType : ReadingType.values()) {
				if (readType.get().equals(readingTypeTxt.getText())) {
					readingType = readType;
					break;
				}
			}
			Reading reading = new Reading(patientIDTxt.getText(), readingType, readingIDTxt.getText(),
					readingValueTxt.getText(), date);
			resultTxt.textProperty().setValue(reading.toString());
		}
	}

	@FXML
	void nextPane(ActionEvent event) {
		ViewNavigator.loadView(ViewNavigator.VIEW_ALL_READINGS);
	}
}
