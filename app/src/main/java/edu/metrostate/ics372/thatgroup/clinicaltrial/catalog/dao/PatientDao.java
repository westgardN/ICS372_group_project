package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.PatientEntity;

@Dao
public interface PatientDao {
    @Insert
    long insert(PatientEntity patient);

    @Update
    int update(PatientEntity patient);

    @Delete
    int delete(PatientEntity patient);

    @Query(ClinicalStatement.GET_PATIENT)
    LiveData<PatientEntity> getClinic(String id, String trialId);

    @Query(ClinicalStatement.GET_ALL_PATIENTS)
    LiveData<List<PatientEntity>> getAllPatients();
}
