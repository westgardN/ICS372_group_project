package edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Trial;

@Entity(tableName = "trials")
public class TrialEntity implements ClinicalEntity {
    public static final Parcelable.Creator<TrialEntity> CREATOR = new Parcelable.Creator<TrialEntity>() {
        @Override
        public TrialEntity createFromParcel(Parcel source) {
            return new TrialEntity(source);
        }

        @Override
        public TrialEntity[] newArray(int size) {
            return new TrialEntity[size];
        }
    };
    @Ignore
    private Trial trial;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "start_date")
    private LocalDate startDate;
    @ColumnInfo(name = "end_date")
    private LocalDate endDate;


    @Ignore
    public TrialEntity(Trial trial) {
        this(trial.getId(), trial.getStartDate(), trial.getEndDate());
        this.trial = trial;
    }

    public TrialEntity(String id, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        trial = new Trial(id);
        trial.setStartDate(startDate);
        trial.setEndDate(endDate);
    }

    protected TrialEntity(Parcel in) {
        this.trial = (Trial) in.readSerializable();
        this.id = in.readString();
        this.startDate = (LocalDate) in.readSerializable();
        this.endDate = (LocalDate) in.readSerializable();
    }

    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Override
    public Object getWrapperModel() {
        return trial;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStartDateString() {
        return startDate.toString();
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getEndDateString() {
        return endDate == null ? "Undetermined" : endDate.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.trial);
        dest.writeString(this.id);
        dest.writeSerializable(this.startDate);
        dest.writeSerializable(this.endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrialEntity that = (TrialEntity) o;
        return Objects.equals(getTrial(), that.getTrial()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getStartDateString(), that.getStartDateString()) &&
                Objects.equals(getEndDate(), that.getEndDate()) &&
                Objects.equals(getEndDateString(), that.getEndDateString());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTrial(), getId(), getStartDate(), getStartDateString(),
                getEndDate(), getEndDateString());
    }
}
