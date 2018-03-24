package edu.metrostate.ics372.thatgroup.clinicaltrial.views;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.beans.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ClinicalTrialModel;
import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ClinicsView extends AnchorPane implements Initializable {
	@FXML
	private ListView<Clinic> listView;
	private ClinicalTrialModel model;
	private ListProperty<Clinic> clinicProperty;

	public ClinicsView() {
		model = null;
		clinicProperty = new SimpleListProperty<>();

		try (InputStream stream = getClass().getResourceAsStream(Strings.CLINICS_VIEW_FXML)) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load(stream);
		} catch (IOException | IllegalStateException ex) {
			ex.printStackTrace();
		}

	}
	
	public ClinicalTrialModel getModel() {
		return model;
	}

	public void setModel(ClinicalTrialModel model) {
		this.model = model;

		clinicProperty.set(model.getClinics());
		listView.itemsProperty().bind(clinicProperty);

		this.model.addPropertyChangeListener((event) -> {
			String prop = event.getPropertyName();
			if (prop.equals(ClinicalTrialModel.PROP_SELECTED_CLINIC)) {
				if (event.getNewValue() instanceof Clinic) {
					Clinic clinic = (Clinic) event.getNewValue();
				}
			} else if (prop.equals(ClinicalTrialModel.PROP_SELECTED_CLINIC)) {
				if (event.getNewValue() instanceof Patient) {
					updateClinic((Clinic) event.getNewValue());
				}
			}

		});	
		listView.itemsProperty().bind(clinicProperty);

		listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			int index = newValue.intValue();

			try {
				if (index >= 0 && index < clinicProperty.size() && clinicProperty.get(index) instanceof Clinic) {
					Clinic clinic = clinicProperty.get(index);
					if (!Objects.equals(clinic, model.getSelectedClinic())) {
							model.setSelectedClinic(clinic);
					}
	
				} else {
					model.setSelectedClinic(null);
				}
			} catch (TrialCatalogException e) {
				PopupNotification.showPopupMessage(e.getMessage(), this.getScene());
			}
		});
		}
	
	
	private void updateClinic(Clinic clinic) {
		System.out.println("updateClinic");
		//ClinicalTrialModel.PROP_SELECTED_CLINIC;
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
