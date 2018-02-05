/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.reading.ReadingFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class ReadingsView extends AnchorPane implements Initializable {
	private ClinicalTrialViewModel model;
	
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

	public ReadingsView() {
		model = null;
		
		try (InputStream stream = getClass().getResourceAsStream("ReadingsView.fxml")) {
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
		
		this.model.addPropertyChangeListener((event) -> {
			String prop = event.getPropertyName();
			if (prop.equals("journal") || prop.equals("updatePatient")) {
				fillTable();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		readingIDCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getId().toString()));
		readingTypeCol.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(ReadingFactory.getPrettyReadingType(cellData.getValue())));
		valueCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		dateTimeCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDate().format(formatter)));
		
		readingTable.getSelectionModel().selectedIndexProperty().addListener((event) -> {
			this.model.setSelectedReading(readingTable.getSelectionModel().getSelectedItem());
		});
	}

	/*
	 * Fills the reading table columns with the proper information based on the
	 * currently selected patient and their respective readings
	 */
	private void fillTable() {
		readingTable.setItems(model.getJournal());
	}
}
