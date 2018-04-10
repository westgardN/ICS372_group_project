package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;

@Dao
public interface TrialDao {
    @Insert
    long insert(TrialEntity trial);

    @Update
    int update(TrialEntity trial);

    @Delete
    int delete(TrialEntity trial);

    @Query(ClinicalStatement.GET_TRIAL)
    LiveData<TrialEntity> getTrial(String id);

    @Query(ClinicalStatement.GET_ALL_TRIALS)
    LiveData<List<TrialEntity>> getTrials();
}
