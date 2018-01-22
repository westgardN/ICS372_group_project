package edu.metrostate.ics372.thatgroup.clinicaltrial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Run extends Application {

	@Override
	public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/views/ClinicalTrialClient.fxml"));      
        Scene scene = new Scene(root);
     
        stage.setTitle("Patient Trial Client");
        stage.setScene(scene);
        stage.show();
	}

	public static void main(String[] args) {
        launch(args);
    }
}
