package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.fragments.clinic;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicalEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.MainActivity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.adapters.ClinicAdapter;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.callback.EntityClickCallback;
import edu.metrostate.ics372.thatgroup.clinicaltrial.viewmodel.ClinicListViewModel;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.databinding.FragmentListBinding;

public class FragmentClinicList extends Fragment {

    public static final String TAG = "ClinicListViewModel";
    private final EntityClickCallback clinicClickCallback = new EntityClickCallback() {
        @Override
        public void onClick(ClinicalEntity entity) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(entity);
            }
        }
    };
    private ClinicAdapter clinicAdapter;
    private FragmentListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        View view = inflater.inflate(R.layout.fragment_list,
                container, false);

        clinicAdapter = new ClinicAdapter(clinicClickCallback);
        mBinding.list.setAdapter(clinicAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ClinicListViewModel viewModel =
                ViewModelProviders.of(this).get(ClinicListViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(ClinicListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getClinics().observe(this, myClinics -> {
            if (myClinics != null) {
                mBinding.setIsLoading(false);
                clinicAdapter.setClinicList(myClinics);
            } else {
                mBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }
}
