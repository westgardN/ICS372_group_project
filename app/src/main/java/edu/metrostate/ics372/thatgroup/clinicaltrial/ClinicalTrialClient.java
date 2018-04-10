package edu.metrostate.ics372.thatgroup.clinicaltrial;

import android.app.Application;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.CatalogHandler;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalTrialCatalog;

public class ClinicalTrialClient extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public CatalogHandler getDatabase() {
        return CatalogHandler.getInstance(this, mAppExecutors);
    }

    public ClinicalTrialCatalog getRepository() {
        return ClinicalTrialCatalog.getInstance(getDatabase());
    }
}
