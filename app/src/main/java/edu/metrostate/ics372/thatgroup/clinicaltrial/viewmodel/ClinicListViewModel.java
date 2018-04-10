package edu.metrostate.ics372.thatgroup.clinicaltrial.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.ClinicalTrialClient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;

public class ClinicListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ClinicEntity>> observableClinics;

    public ClinicListViewModel(Application application) {
        super(application);

        observableClinics = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClinics.setValue(null);

        LiveData<List<ClinicEntity>> clinics = ((ClinicalTrialClient) application).getRepository()
                .getClinics();

        observableClinics.addSource(clinics, observableClinics::setValue);
    }

    public LiveData<List<ClinicEntity>> getClinics() {
        return observableClinics;
    }
}
