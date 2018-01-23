package edu.metrostate.ics372.thatgroup.clinicaltrial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ViewAllReadingsController {
	
	@FXML
    void previousPane(ActionEvent event) {
        ViewNavigator.loadView(ViewNavigator.INPUT_READING);
    }
}
