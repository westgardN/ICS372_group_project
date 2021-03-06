/**
 * File: ClinicalTrialClient.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import edu.metrostate.ics372.thatgroup.clinicaltrial.views.ClinicalTrialView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The main application class for the Clinical Trial JavaFX application.
 * 
 * It is responsible for loading any application wide resources and kicking off the
 * JavaFX framework to start the application.
 * 
 * Nurses will use the software to record data that patients have entered into their journals.
 * 
 * The software can import readings from a file in JSON or XML format containing clinics and patient readings.
 * The software supports 4 different types of items in the input file: weight reading, temp reading, blood pressure reading, and number of steps.
 * The software stores the item ID for each entry and associates it with the specified patient ID.
 * The software reads and stores the associated metadata for each item.
 * The software supports the following commands for each patient: add reading, start patient trial, and end patient trial.
 * The software only allows adding readings to a patient that is part of the trial.
 * The software keeps records for a patient that has left a trial, and disregards new readings.
 * The software can export all the readings into a single JSON file
 * The software shows a list of associated readings for each patient.
 * 
 * @author That Group
 *
 */
public class ClinicalTrialClient extends Application {
	ClinicalTrialView view;

	@Override
	public void start(Stage stage) {
		stage.setTitle("Patient Trial Client");
		
		try (InputStream is = getClass().getResourceAsStream(Strings.LOGO_PATH);) {
			Image applicationIcon = new Image(is);
			stage.getIcons().add(applicationIcon);
			Pane pane = loadMainPane();
			stage.setScene(createScene(pane));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (view != null) {
			view.setStage(stage);
		}
	}

	/**
	 * Loads the main fxml layout. Sets up the view Navigator. Loads
	 * the first view into the fxml layout.
	 *
	 * @return the loaded pane.
	 * @throws IOException
	 *             if the pane could not be loaded.
	 */
	private Pane loadMainPane() throws IOException {
		Pane mainPane = null;
		try {
			URL url = getClass().getResource(Strings.CLINICAL_TRIAL_VIEW_FXML);
			FXMLLoader loader = new FXMLLoader();
			mainPane = (Pane) loader.load(url.openStream());
			view = loader.<ClinicalTrialView>getController();
		} catch (IOException | IllegalStateException ex) {
			ex.printStackTrace();
		}
		return mainPane;
	}

	/**
	 * Creates the main application scene.
	 *
	 * @param mainPane
	 *            the main application layout.
	 *
	 * @return the created scene.
	 */
	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().setAll(getClass().getResource(Strings.CSS_PATH).toExternalForm());
		return scene;
	}

	/**
	 * The main entry point in to the application that kicks off the JavaFX and gets everything going.
	 * 
	 * @param args any command line arguments passed to this application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}