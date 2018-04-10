package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments;

import android.support.v4.app.Fragment;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicalEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.clinic.FragmentClinic;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.trial.FragmentTrial;

public class FragmentFactory {
    public static Fragment getEntityFragment(ClinicalEntity entity) {
        return identifyEntity(entity);
    }

    private static Fragment identifyEntity(ClinicalEntity entity) {
        if (entity instanceof TrialEntity) {
            return new FragmentTrial();
        } else if (entity instanceof ClinicEntity) {
            return new FragmentClinic();
//        } else if (entity instanceof PatientEntity) {
//            fragmentToShow = PatientEntity.forPatient(((PatientEntity) entity));
//            backStack = "patient";
//        } else if (entity instanceof ReadingEntity) {
//            fragmentToShow = ReadingEntity.forReading(((ReadingEntity) entity));
//            backStack = "reading";
        }
        return null;
    }
}
