package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.ClinicEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.callback.EntityClickCallback;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.databinding.ItemClinicBinding;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicViewHolder> {
    @Nullable
    private final EntityClickCallback clinicClickCallback;
    List<? extends ClinicEntity> clinicList;

    public ClinicAdapter(@Nullable EntityClickCallback clickCallback) {
        clinicClickCallback = clickCallback;
    }

    public void setClinicList(final List<ClinicEntity> cList) {
        if (clinicList == null) {
            clinicList = cList;
            notifyItemRangeInserted(0, cList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return clinicList.size();
                }

                @Override
                public int getNewListSize() {
                    return cList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldTrialPosition, int newTrialPosition) {
                    return clinicList.get(oldTrialPosition).getId() ==
                            cList.get(newTrialPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldTrialPosition, int newTrialPosition) {
                    ClinicEntity newTrial = cList.get(newTrialPosition);
                    ClinicEntity oldTrial = clinicList.get(oldTrialPosition);
                    return newTrial.equals(oldTrial);
                }
            });
            clinicList = cList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ClinicAdapter.ClinicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemClinicBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_clinic,
                        parent, false);
        binding.setCallback(clinicClickCallback);
        return new ClinicAdapter.ClinicViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ClinicAdapter.ClinicViewHolder holder, int position) {
        holder.binding.setClinicEntity(clinicList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return clinicList == null ? 0 : clinicList.size();
    }

    static class ClinicViewHolder extends RecyclerView.ViewHolder {
        final ItemClinicBinding binding;

        public ClinicViewHolder(ItemClinicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
