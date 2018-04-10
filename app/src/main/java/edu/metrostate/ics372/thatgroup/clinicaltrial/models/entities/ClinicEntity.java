package edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Clinic;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "clinics", foreignKeys = @ForeignKey(
        entity = TrialEntity.class,
        parentColumns = "id",
        childColumns = "trial_id",
        onDelete = CASCADE
))
public class ClinicEntity implements ClinicalEntity {
    public static final Creator<ClinicEntity> CREATOR = new Creator<ClinicEntity>() {
        @Override
        public ClinicEntity createFromParcel(Parcel source) {
            return new ClinicEntity(source);
        }

        @Override
        public ClinicEntity[] newArray(int size) {
            return new ClinicEntity[size];
        }
    };
    @Ignore
    private Clinic clinic;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "trial_id")
    private String trialId;

    @Ignore
    public ClinicEntity(Clinic clinic) {
        this(clinic.getId(), clinic.getName(), clinic.getTrialId());
        this.clinic = clinic;
    }

    public ClinicEntity(String id, String name, String trialId) {
        this.id = id;
        this.name = name;
        this.trialId = trialId;
        clinic = new Clinic(id, trialId, name);
    }

    protected ClinicEntity(Parcel in) {
        this.clinic = (Clinic) in.readSerializable();
        this.id = in.readString();
        this.name = in.readString();
        this.trialId = in.readString();
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
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
        return clinic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrialId() {
        return trialId;
    }

    public void setTrialId(String trialId) {
        this.trialId = trialId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.clinic);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.trialId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClinicEntity that = (ClinicEntity) o;
        return Objects.equals(getClinic(), that.getClinic()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getTrialId(), that.getTrialId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClinic(), getId(), getName(), getTrialId());
    }
}
