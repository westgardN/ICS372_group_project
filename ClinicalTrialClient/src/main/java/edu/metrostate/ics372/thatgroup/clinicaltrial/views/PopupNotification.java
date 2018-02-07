/**
 * File: PopupNotification.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;

/**
 * A utility class to pop-up a message on the screen.
 * 
 * @author That Group
 *
 */
public class PopupNotification {
	
	private static Popup createPopup(final String message) {
	    final Popup popup = new Popup();
	    popup.setAutoFix(true);
	    popup.setAutoHide(true);
	    popup.setHideOnEscape(true);
	    Label label = new Label(message);
	    label.setOnMouseReleased(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent e) {
	            popup.hide();
	        }
	    });
	    label.getStyleClass().add("my-popup");
	    popup.getContent().add(label);
	    return popup;
	}

	/**
	 * Shows the specified message in a pop-up window centered on the screen.
	 * 
	 * @param message the message to display.
	 * @param scene the JavaFX scene that owns the window.
	 */
	public static void showPopupMessage(final String message, final Scene scene) {
	    final Popup popup = createPopup(message);
	    popup.setOnShown(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent e) {
	        		popup.centerOnScreen();
	        }
	    });        
	    popup.show(scene.getWindow());
	}}
