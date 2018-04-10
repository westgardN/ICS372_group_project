package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.trial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.Trial;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.DatePickerPopup;
import edu.metrostate.ics372.thatgroup.clinicaltrial.viewmodel.TrialViewModel;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;

public class FragmentAddTrial extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_trial, container, false);
        EditText trialId = rootView.findViewById(R.id.add_trial_trial_id);
        EditText startDate = rootView.findViewById(R.id.add_trial_startdate);
        EditText endDate = rootView.findViewById(R.id.add_trial_enddate);
        Button ok = rootView.findViewById(R.id.add_trial_submit_btn);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerPopup picker = DatePickerPopup.newInstance("sDate");
                picker.show(Objects.requireNonNull(getFragmentManager()), null);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerPopup picker = DatePickerPopup.newInstance("eDate");
                picker.show(Objects.requireNonNull(getFragmentManager()), null);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
                Trial trial = new Trial(trialId.getText().toString());
                trial.setStartDate(LocalDate.parse(startDate.getText().toString(), formatter));
                TrialViewModel.insert(trial);
            }
        });


        return rootView;
    }
}
