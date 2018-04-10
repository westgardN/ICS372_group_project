package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.time.LocalDate;

import edu.metrostate.ics372.thatgroup.clinicaltrial.AppExecutors;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.ClinicDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.PatientDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.ReadingDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao.TrialDao;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.PatientEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ReadingEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;

@Database(entities = {TrialEntity.class, ClinicEntity.class, PatientEntity.class, ReadingEntity.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class CatalogHandler extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "clinical_trial_catalog";
    private static CatalogHandler sInstance;
    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static CatalogHandler getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (CatalogHandler.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static CatalogHandler buildDatabase(final Context appContext,
                                                final AppExecutors executors) {
        return Room.databaseBuilder(appContext, CatalogHandler.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Generate the data for pre-population
                            CatalogHandler database = CatalogHandler.getInstance(appContext, executors);

                            Trial trial = new Trial("DEFAULT");
                            trial.setStartDate(LocalDate.now());
                            database.trialDao().insert(new TrialEntity(trial));
                            database.clinicDao().insert(new ClinicEntity(new Clinic("TC1", trial.getId(), "That Clinic")));
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    public abstract TrialDao trialDao();

    public abstract ClinicDao clinicDao();

    public abstract PatientDao patientDao();

    public abstract ReadingDao readingDao();

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}