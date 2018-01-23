package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class ViewNavigator {
	public static final String MAIN    = "main.fxml";
    public static final String INPUT_READING = "inputReading.fxml";
    public static final String VIEW_ALL_READINGS = "viewAllReadings.fxml";

    /** The main application layout controller. */
    private static MainController mainController;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(MainController mainController) {
        ViewNavigator.mainController = mainController;
    }

    /**
     * Loads the view specified by the fxml file into the
     * root pane of the main application layout.
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadView(String fxml) {
    	System.out.println(fxml);
        try {
            mainController.setView(FXMLLoader.load(ViewNavigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
