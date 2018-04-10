package edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.time.LocalDateTime;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Reading;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.ReadingFactory;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "readings", foreignKeys = {
        @ForeignKey(
                entity = ClinicEntity.class,
                parentColumns = "id",
                childColumns = "clinic_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = PatientEntity.class,
                parentColumns = "id",
                childColumns = "patient_id",
                onDelete = CASCADE
        )
})
public class ReadingEntity implements Parcelable {
    public static final Parcelable.Creator<ReadingEntity> CREATOR = new Parcelable.Creator<ReadingEntity>() {
        @Override
        public ReadingEntity createFromParcel(Parcel source) {
            return new ReadingEntity(source);
        }

        @Override
        public ReadingEntity[] newArray(int size) {
            return new ReadingEntity[size];
        }
    };
    @Ignore
    private Reading reading;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "clinic_id")
    private String clinicId;
    @ColumnInfo(name = "patient_id")
    private String patientId;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "date")
    private LocalDateTime date;
    @ColumnInfo(name = "type")
    private String type;

    @Ignore
    public ReadingEntity(Reading reading) {
        this(reading.getId(), reading.getClinicId(), reading.getPatientId(), reading.getValue().toString(),
                reading.getDate(), ReadingFactory.getReadingType(reading));
        this.reading = reading;
    }

    public ReadingEntity(String id, String clinicId, String patientId, String value, LocalDateTime date, String type) {
        this.id = id;
        this.clinicId = clinicId;
        this.patientId = patientId;
        this.value = value;
        this.date = date;
        this.type = type;
        reading = ReadingFactory.getReading(this.type);
        reading.setId(this.id);
        reading.setClinicId(this.clinicId);
        reading.setPatientId(this.patientId);
        reading.setValue(value);
        reading.setDate(this.date);
    }

    protected ReadingEntity(Parcel in) {
        this.reading = (Reading) in.readSerializable();
        this.id = in.readString();
        this.clinicId = in.readString();
        this.patientId = in.readString();
        this.value = in.readString();
        this.date = (LocalDateTime) in.readSerializable();
        this.type = in.readString();
    }

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.reading);
        dest.writeString(this.id);
        dest.writeString(this.clinicId);
        dest.writeString(this.patientId);
        dest.writeString(this.value);
        dest.writeSerializable(this.date);
        dest.writeString(this.type);
    }
}
