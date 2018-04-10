package edu.metrostate.ics372.thatgroup.clinicaltrial.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.ClinicalTrialClient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;

public class TrialListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TrialEntity>> observableTrials;

    public TrialListViewModel(Application application) {
        super(application);

        observableTrials = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableTrials.setValue(null);

        LiveData<List<TrialEntity>> trials = ((ClinicalTrialClient) application).getRepository()
                .getTrials();


        observableTrials.addSource(trials, observableTrials::setValue);
    }

    public LiveData<List<TrialEntity>> getTrials() {
        return observableTrials;
    }
}
