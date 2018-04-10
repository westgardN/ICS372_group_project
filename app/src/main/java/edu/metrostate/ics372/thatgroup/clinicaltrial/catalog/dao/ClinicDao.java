package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;

@Dao
public interface ClinicDao {
    @Insert
    long insert(ClinicEntity clinic);

    @Update
    int update(ClinicEntity clinic);

    @Delete
    int delete(ClinicEntity clinic);

    @Query(ClinicalStatement.GET_CLINIC)
    LiveData<ClinicEntity> getClinic(String id);

    @Query(ClinicalStatement.GET_ALL_CLINICS)
    LiveData<List<ClinicEntity>> getClinics();
}
