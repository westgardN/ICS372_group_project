package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;

public class DatePickerPopup extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static DatePickerPopup newInstance(String caller) {
        DatePickerPopup fragment = new DatePickerPopup();
        Bundle bundle = new Bundle();
        bundle.putString("caller", caller);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        EditText dateView = null;
        if (getArguments() != null && Objects.equals(getArguments().getString("caller"), "sDate")) {
            dateView = Objects.requireNonNull(getActivity()).findViewById(R.id.add_trial_startdate);

        } else if (getArguments() != null && Objects.equals(getArguments().getString("caller"), "eDate")) {
            dateView = Objects.requireNonNull(getActivity()).findViewById(R.id.add_trial_enddate);
        }
        if (dateView != null) {
            dateView.setText(String.format(getResources().getString(
                    R.string.add_trial_date_placeholder), month + 1, dayOfMonth, year));
        }
    }
}
