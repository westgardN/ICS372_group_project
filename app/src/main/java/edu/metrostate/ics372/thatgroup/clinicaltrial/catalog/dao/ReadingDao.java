package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.ClinicalStatement;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ReadingEntity;

@Dao
public interface ReadingDao {
    @Insert
    long insert(ReadingEntity reading);

    @Update
    int update(ReadingEntity reading);

    @Delete
    int delete(ReadingEntity reading);

    @Query(ClinicalStatement.GET_READING)
    LiveData<ReadingEntity> getReading(String id);

    @Query(ClinicalStatement.GET_ALL_READINGS)
    LiveData<List<ReadingEntity>> getAllReadings();
}
