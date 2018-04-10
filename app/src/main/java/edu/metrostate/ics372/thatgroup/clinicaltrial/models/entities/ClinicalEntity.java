package edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities;

import android.os.Parcelable;

public interface ClinicalEntity extends Parcelable {
    String getId();

    Object getWrapperModel();
}
