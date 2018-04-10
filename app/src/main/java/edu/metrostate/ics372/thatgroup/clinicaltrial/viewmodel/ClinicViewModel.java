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
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;

public class ClinicViewModel extends AndroidViewModel {
    private final LiveData<ClinicEntity> liveClinic;
    private final Parcelable clinicEntity;
    public ObservableField<ClinicEntity> observableClinic = new ObservableField<>();

    public ClinicViewModel(@NonNull Application application, ClinicalTrialCatalog catalog,
                           final Parcelable clinicEntity) {
        super(application);
        this.clinicEntity = clinicEntity;

        liveClinic = catalog.get(((ClinicEntity) clinicEntity).getClinic());
    }

    public LiveData<ClinicEntity> getLiveClinic() {
        return liveClinic;
    }

    public void setLiveClinic(ClinicEntity clinic) {
        this.observableClinic.set(clinic);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final Parcelable trialEntity;
        private final ClinicalTrialCatalog catalog;

        public Factory(@NonNull Application application, Parcelable clinicEntity) {
            this.application = application;
            this.trialEntity = clinicEntity;
            catalog = ((ClinicalTrialClient) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ClinicViewModel(application, catalog, trialEntity);
        }
    }
}
