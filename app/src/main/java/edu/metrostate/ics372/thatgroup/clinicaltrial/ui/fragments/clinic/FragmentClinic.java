package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.clinic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicalEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.FragmentFactory;
import edu.metrostate.ics372.thatgroup.clinicaltrial.viewmodel.ClinicViewModel;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.databinding.FragmentClinicBinding;

public class FragmentClinic extends Fragment {
    private static final String CLINIC_ID_KEY = "id";
    FragmentClinicBinding binding;

    public static Fragment forEntity(ClinicalEntity entity) {
        Fragment fragment = FragmentFactory.getEntityFragment(entity);
        Bundle args = new Bundle();
        args.putParcelable(CLINIC_ID_KEY, entity);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_clinic, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ClinicViewModel.Factory factory = new ClinicViewModel.Factory(
                getActivity().getApplication(), getArguments().getParcelable(CLINIC_ID_KEY));
        final ClinicViewModel model = ViewModelProviders.of(this, factory)
                .get(ClinicViewModel.class);
        binding.setClinicViewModel(model);
        subscribeToModel(model);
    }

    protected void subscribeToModel(final ClinicViewModel model) {

        model.getLiveClinic().observe(this, new Observer<ClinicEntity>() {
            @Override
            public void onChanged(@Nullable ClinicEntity trial) {
                model.setLiveClinic(trial);
            }
        });
    }
}
