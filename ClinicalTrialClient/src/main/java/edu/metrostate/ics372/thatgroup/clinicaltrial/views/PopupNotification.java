/**
 * File: PopupNotification.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
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
	
	private static final long TIME_DELAY = 3000;

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
	    EventHandler<WindowEvent> handler = new EventHandler<WindowEvent>() {
	    	private ScheduledExecutorService sch = Executors.newSingleThreadScheduledExecutor();
	        @Override
	        public void handle(WindowEvent e) {
	        	if (WindowEvent.WINDOW_SHOWN == e.getEventType()) {
	        		popup.centerOnScreen();
	        	    sch.schedule(new Runnable() {
	        	        public void run() {
	        	        	Platform.runLater(new Runnable() {
	        	        		public void run() {
			        	            if (popup != null && popup.isShowing()) {
			        	            	popup.hide();
			        	            }
	        	        		}
	        	        	});
	        	        }
	        	    }, TIME_DELAY, TimeUnit.MILLISECONDS);
	        	} else if (WindowEvent.WINDOW_HIDING == e.getEventType()) {
	        		sch.shutdown();
	        	}
	        }
	    };
	    
	    popup.setOnShown(handler);
	    popup.setOnHiding(handler);
	    

	    popup.show(scene.getWindow());
	}}
