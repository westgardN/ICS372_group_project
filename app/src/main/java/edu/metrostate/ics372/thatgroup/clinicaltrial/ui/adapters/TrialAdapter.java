package edu.metrostate.ics372.thatgroup.clinicaltrial.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import edu.metrostate.ics372.thatgroup.clinicaltrial.models.entities.TrialEntity;
import edu.metrostate.ics372.thatgroup.clinicaltrial.ui.callback.EntityClickCallback;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.R;
import edu.metrostate.ics372.thatgroup.clinicaltrialclient.databinding.ItemTrialBinding;

public class TrialAdapter extends RecyclerView.Adapter<TrialAdapter.TrialViewHolder> {
    @Nullable
    private final EntityClickCallback trialClickCallback;
    List<? extends TrialEntity> trialList;

    public TrialAdapter(@Nullable EntityClickCallback clickCallback) {
        trialClickCallback = clickCallback;
    }

    public void setTrialList(final List<TrialEntity> tList) {
        if (trialList == null) {
            trialList = tList;
            notifyItemRangeInserted(0, tList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return trialList.size();
                }

                @Override
                public int getNewListSize() {
                    return tList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldTrialPosition, int newTrialPosition) {
                    return trialList.get(oldTrialPosition).getId() ==
                            tList.get(newTrialPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldTrialPosition, int newTrialPosition) {
                    TrialEntity newTrial = tList.get(newTrialPosition);
                    TrialEntity oldTrial = trialList.get(oldTrialPosition);
                    return newTrial.getId() == oldTrial.getId()
                            && Objects.equals(newTrial.getId(), oldTrial.getId())
                            && Objects.equals(newTrial.getStartDate(), oldTrial.getStartDate())
                            && newTrial.getEndDate() == oldTrial.getEndDate();
                }
            });
            trialList = tList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public TrialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTrialBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_trial,
                        parent, false);
        binding.setCallback(trialClickCallback);
        return new TrialViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TrialViewHolder holder, int position) {
        holder.binding.setTrialEntity(trialList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return trialList == null ? 0 : trialList.size();
    }

    static class TrialViewHolder extends RecyclerView.ViewHolder {
        final ItemTrialBinding binding;

        public TrialViewHolder(ItemTrialBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
