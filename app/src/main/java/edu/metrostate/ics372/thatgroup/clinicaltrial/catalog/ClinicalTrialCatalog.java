package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Clinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Patient;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicalEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;


/**
 * The ClinicalTrialCatalog serves as the central database used to store all of
 * the information for a trial. This class provides CRUD data access to
 * Clinic(s), Reading(s), and Patient(s). That is you are able to insert, get,
 * update, and delete a Clinic, Reading, or Patient from the catalog. At
 * instantiation of this class, the <code>init()</code> method will check to see
 * if the catalog storage directory exists, if not it will be created and a new
 * default trial catalog will be created and initialized. If the catalog storage
 * directory does already exist, but the directory is void of any trial catalogs
 * or a default trial catalog, a new default trial catalog will be created and
 * initialized. Otherwise it will be assumed the directory already exists and
 * there is a default trial catalog present in the directory. The catalog
 * storage paths for Windows, Linux, and OS X are as follows:
 * <p>
 * <ul>
 * <li>Windows: Users\*user*\AppData\Roaming\That Group\Trial Catalogs
 * <li>Linux: $HOME\.local\share\That Group\Clinical Trial Catalogs
 * <li>MAC: Users\*user*\Library\Application Support\That Group\Clinical Trial
 * Catalogs
 * </ul>
 *
 * @author That Group
 */
public class ClinicalTrialCatalog implements TrialCatalog {
    private static ClinicalTrialCatalog sInstance;
    private final CatalogHandler catalogHandler;
    private MediatorLiveData<List<TrialEntity>> observableTrials;

    private ClinicalTrialCatalog(final CatalogHandler catalogHandler) {
        this.catalogHandler = catalogHandler;
        observableTrials = new MediatorLiveData<>();

        observableTrials.addSource(this.getTrials(),
                trials -> {
                    if (catalogHandler.getDatabaseCreated().getValue() != null) {
                        observableTrials.postValue(trials);
                    }
                });
    }

    public static ClinicalTrialCatalog getInstance(final CatalogHandler database) {
        if (sInstance == null) {
            synchronized (ClinicalTrialCatalog.class) {
                if (sInstance == null) {
                    sInstance = new ClinicalTrialCatalog(database);
                }
            }
        }
        return sInstance;
    }

    public CatalogHandler getcatalogHandler() {
        return catalogHandler;
    }


    @Override
    public boolean init(Trial trial) {
        return false;
    }

    @Override
    public boolean isInit() {
        return false;
    }

    @Override
    public boolean insert(Trial trial) {
        return false;
    }

    @Override
    public boolean update(Trial trial) {
        return false;
    }

    public LiveData<ClinicalEntity> get(Object wrapperModel) {
        if (wrapperModel instanceof Trial) {
            return get(wrapperModel);
        } else if (wrapperModel instanceof Clinic) {
            return get(wrapperModel);
        } else if (wrapperModel instanceof Patient) {
            return get(wrapperModel);
        } else if (wrapperModel instanceof Reading) {
            return get(wrapperModel);
        }
        return null;
    }

    @Override
    public LiveData<TrialEntity> get(Trial trial) {
        return catalogHandler.trialDao().getTrial(trial.getId());
    }

    @Override
    public boolean remove(Trial trial) {
        return false;
    }

    @Override
    public boolean exists(Clinic clinic) {
        return false;
    }

    @Override
    public boolean insert(Clinic clinic) {
        return false;
    }

    @Override
    public LiveData<ClinicEntity> get(Clinic clinic) {
        return catalogHandler.clinicDao().getClinic(clinic.getId());
    }

    @Override
    public LiveData<Clinic> getDefaultClinic() {
        return null;
    }

    @Override
    public boolean update(Clinic clinic) {
        return false;
    }

    @Override
    public boolean remove(Clinic clinic) {
        return false;
    }

    @Override
    public boolean exists(Patient patient) {
        return false;
    }

    @Override
    public boolean insert(Patient patient) {
        return false;
    }

    @Override
    public LiveData<Patient> get(Patient patient) {
        return null;
    }

    @Override
    public LiveData<Patient> getDefaultPatient() {
        return null;
    }

    @Override
    public boolean update(Patient patient) {
        return false;
    }

    @Override
    public boolean remove(Patient patient) {
        return false;
    }

    @Override
    public boolean exists(Reading reading) {
        return false;
    }

    @Override
    public boolean insert(Reading reading) {
        return false;
    }

    @Override
    public LiveData<Reading> get(Reading reading) {
        return null;
    }

    @Override
    public boolean update(Reading reading) {
        return false;
    }

    @Override
    public boolean remove(Reading patient) {
        return false;
    }

    @Override
    public LiveData<List<TrialEntity>> getTrials() {
        return catalogHandler.trialDao().getTrials();
    }

    @Override
    public LiveData<List<ClinicEntity>> getClinics() {
        return catalogHandler.clinicDao().getClinics();
    }

    @Override
    public LiveData<List<Patient>> getPatients() {
        return null;
    }

    @Override
    public LiveData<List<Patient>> getActivePatients() {
        return null;
    }

    @Override
    public LiveData<List<Patient>> getInactivePatients() {
        return null;
    }

    @Override
    public LiveData<List<Reading>> getReadings() {
        return null;
    }

    @Override
    public LiveData<List<Reading>> getReadings(Patient patient) {
        return null;
    }

    @Override
    public LiveData<List<Reading>> getReadings(Clinic clinic) {
        return null;
    }
}