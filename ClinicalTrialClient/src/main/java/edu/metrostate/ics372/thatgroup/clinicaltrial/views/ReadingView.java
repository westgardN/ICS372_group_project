/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class ReadingView extends AnchorPane implements Initializable {
	private ClinicalTrialViewModel model;
	@FXML
	private TextField id;
	@FXML
	private TextField patientId;
	@FXML
	private TextField value;
	@FXML
	private DatePicker date;
	@FXML
	private ChoiceBox<String> type;
	
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
	 * @param model the model to set
	 */
	public void setModel(ClinicalTrialViewModel model) {
		this.model = model;
		// Initialize the reading type choice box
		type.setItems(model.getReadingTypeChoices());
		// Set the reading type choice box default value
		type.getSelectionModel().selectFirst();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

}
