/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Vincent J Palodichuk <a HREF="mailto:hu0011wy@metrostate.edu">
 *         (e-mail me) </a>
 *
 * @version 01/18/2018
 */
public class ClinicalTrialClient extends Application {
	private static final String APP_TITLE = "ICS 372 Clinical Trial Client (That Group)";
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	private Stage stage = null;

    /**
     * Setups the stage and initial state of the UI for the application.
     * @param stage The stage to be prepared.
     */
    @Override
    public void start(Stage theStage) {
        stage = theStage;
        stage.setTitle(APP_TITLE);
        VBox root = new VBox();
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene.setFill(Color.VIOLET);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
        root.getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    /**
     * Main entry point of the application.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
