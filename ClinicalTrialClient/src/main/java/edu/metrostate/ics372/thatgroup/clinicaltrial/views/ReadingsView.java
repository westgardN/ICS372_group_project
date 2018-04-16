/**
 * File: ReadingsViews.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.ReadingFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * This view is responsible for displaying the readings in a TableView for a
 * selected patient
 * 
 * @author That Group
 *
 */
public class ReadingsView extends AnchorPane implements Initializable {
	private ClinicalTrialModel model;

	@FXML
	private TableView<Reading> readingTable;

	@FXML
	private TableColumn<Reading, String> dateTimeCol;
	@FXML
	private TableColumn<Reading, String> readingTypeCol;
	@FXML
	private TableColumn<Reading, String> valueCol;
	@FXML
	private TableColumn<Reading, String> readingIDCol;
	@FXML
	private TableColumn<Reading, String> clinicIDCol;
	@FXML
	private TableColumn<Reading, String> patientIDCol;

	/**
	 * Constructs a new ReadingsView instance
	 */
	public ReadingsView() {
		model = null;

		try (InputStream stream = getClass().getResourceAsStream(Strings.READINGS_VIEW_FXML)) {
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

		this.model.addPropertyChangeListener((event) -> {
			String prop = event.getPropertyName();
			if (prop.equals(ClinicalTrialModel.PROP_JOURNAL)
					|| prop.equals(ClinicalTrialModel.PROP_UPDATE_READING)
					|| prop.equals(ClinicalTrialModel.PROP_READINGS)) {
				try {
					this.model.setSelectedReading(null);
					fillTable();
				} catch (TrialCatalogException e) {
					PopupNotification.showPopupMessage(e.getMessage(), this.getScene());
				}
			}
		});
	}

	/**
	 * Initializes this view and sets up the readings table to display the
	 * individual reading's information
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		readingIDCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getId().toString()));
		clinicIDCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getClinicId().toString()));
		patientIDCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPatientId().toString()));
		readingTypeCol.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(ReadingFactory.getPrettyReadingType(cellData.getValue())));
		valueCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		dateTimeCol.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDate().format(formatter)));

		readingTable.getSelectionModel().selectedIndexProperty().addListener((event) -> {
			try {
				this.model.setSelectedReading(readingTable.getSelectionModel().getSelectedItem());
			} catch (TrialCatalogException e) {
				PopupNotification.showPopupMessage(e.getMessage(), this.getScene());
			}
		});
	}

	/*
	 * Fills the reading table columns with the proper information based on the
	 * currently selected patient and their respective readings
	 */
	private void fillTable() {
		List<Reading> readings = model.getJournal();
		if (readings != null) {
			ObservableList<Reading> journal = FXCollections.observableArrayList(readings);
			readingTable.getItems().clear();
			readingTable.setItems(journal);
		} else if (model.getSelectedClinic() == null && model.getSelectedPatient() == null) {
			readingTable.getItems().clear();
		}
	}
}
