package edu.metrostate.ics372.thatgroup.clinicaltrial.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import edu.metrostate.ics372.thatgroup.clinicaltrial.ClinicalTrialClient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalTrialCatalog;
import edu.metrostate.ics372.thatgroup.clinicaltrial.exceptions.TrialCatalogException;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;

public class TrialViewModel extends AndroidViewModel {
    private static ClinicalTrialCatalog catalog;
    private final LiveData<TrialEntity> liveTrial;
    private final Parcelable trial;
    public ObservableField<TrialEntity> observableTrial = new ObservableField<>();

    public TrialViewModel(@NonNull Application application, ClinicalTrialCatalog catalog,
                          final Parcelable trialEntity) {
        super(application);
        TrialViewModel.catalog = catalog;
        this.trial = trialEntity;
        liveTrial = catalog.get(((TrialEntity) trialEntity).getTrial());
    }

    public static void insert(Trial trial) {
        catalog.insert(trial);
    }

    public LiveData<TrialEntity> getLiveTrial() {
        return liveTrial;
    }

    public void setLiveTrial(TrialEntity trial) {
        this.observableTrial.set(trial);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final Parcelable trialEntity;
        private final ClinicalTrialCatalog catalog;

        public Factory(@NonNull Application application, Parcelable trialEntity) {
            this.application = application;
            this.trialEntity = trialEntity;
            catalog = ((ClinicalTrialClient) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return (T) new TrialViewModel(application, catalog, trialEntity);
            } catch (TrialCatalogException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
