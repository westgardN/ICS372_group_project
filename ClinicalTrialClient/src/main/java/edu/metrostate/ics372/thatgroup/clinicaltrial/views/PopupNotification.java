/**
 * 
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;

/**
 * @author Vincent J. Palodichuk
 *
 */
public class PopupNotification {
	public static Popup createPopup(final String message) {
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

	public static void showPopupMessage(final String message, final Scene scene) {
	    final Popup popup = createPopup(message);
	    popup.setOnShown(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent e) {
	            popup.setX(scene.getX() + scene.getWidth()/2 - popup.getWidth()/2);
	            popup.setY(scene.getY() + scene.getHeight()/2 - popup.getHeight()/2);
	        }
	    });        
	    popup.show(scene.getWindow());
	}}
