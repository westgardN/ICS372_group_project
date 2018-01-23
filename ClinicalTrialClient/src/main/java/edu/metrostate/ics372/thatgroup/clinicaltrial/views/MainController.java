/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Vincent J Palodichuk <a HREF="mailto:hu0011wy@metrostate.edu">
 *         (e-mail me) </a>
 *
 * @version 01/18/2018
 */
/**
 * Main controller class for the entire layout.
 */
public class MainController {

	/** Holder of a switchable view. */
	@FXML
	private StackPane root;

	/**
	 * Replaces the view displayed in the root with a new view.
	 *
	 * @param node
	 *            the view node to be swapped in.
	 */
	public void setView(Node node) {
		root.getChildren().setAll(node);
	}

}