package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.trial;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicalEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.viewmodel.TrialViewModel;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.databinding.FragmentTrialBinding;

public class FragmentTrial extends Fragment {
    private static final String TRIAL_ID_KEY = "id";
    protected FragmentTrialBinding binding;

    public static Fragment forEntity(ClinicalEntity entity) {
        Fragment fragment = new FragmentTrial();
        Bundle args = new Bundle();
        args.putParcelable(TRIAL_ID_KEY, entity);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trial, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TrialViewModel.Factory factory = new TrialViewModel.Factory(
                Objects.requireNonNull(getActivity()).getApplication(),
                getArguments() != null ? getArguments().getParcelable(TRIAL_ID_KEY) : null);

        final TrialViewModel model = ViewModelProviders.of(this, factory)
                .get(TrialViewModel.class);
        binding.setTrialViewModel(model);
        subscribeToModel(model);
    }

    protected void subscribeToModel(final TrialViewModel model) {

        model.getLiveTrial().observe(this, new Observer<TrialEntity>() {
            @Override
            public void onChanged(@Nullable TrialEntity trial) {
                model.setLiveTrial(trial);
            }
        });
    }
}
