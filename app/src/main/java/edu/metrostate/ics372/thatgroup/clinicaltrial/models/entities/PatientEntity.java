package edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.LocalDate;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Patient;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "patients", foreignKeys = @ForeignKey(
        entity = TrialEntity.class,
        parentColumns = "id",
        childColumns = "trial_id",
        onDelete = CASCADE
))
public class PatientEntity {
    @Ignore
    private Patient patient;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "trial_id")
    private String trialId;
    @ColumnInfo(name = "start_date")
    private LocalDate startDate;
    @ColumnInfo(name = "end_date")
    private LocalDate endDate;

    @Ignore
    public PatientEntity(Patient patient) {
        this(patient.getId(), patient.getTrialId(), patient.getTrialStartDate(), patient.getTrialEndDate());
        this.patient = patient;
    }

    public PatientEntity(String id, String trialId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.trialId = trialId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTrialId() {
        return trialId;
    }

    public void setTrialId(String trialId) {
        this.trialId = trialId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
