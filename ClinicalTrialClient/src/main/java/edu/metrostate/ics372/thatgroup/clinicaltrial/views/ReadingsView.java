/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
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
			if (Objects.equals(event.getPropertyName(), "journal")) {
				System.out.println("New journal is: " + event.getNewValue());
				fillTable();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/*
	 * Fills the reading table columns with the proper information based on the
	 * currently selected patient and their respective readings
	 */
	private void fillTable() {
		readingTable.setItems(model.getJournal());
		readingIDCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getId().toString()));
		readingTypeCol.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(ReadingFactory.getPrettyReadingType(cellData.getValue())));
		valueCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
		dateTimeCol
				.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDate().toString()));
	}
}
