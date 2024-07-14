package com.example.mycoin.fragments.goals;

import static android.view.LayoutInflater.from;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.GoalsItemBinding;

import java.util.List;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder> {

    private List<GoalItem> mListGoals;

    public GoalsAdapter(List<GoalItem> listGoals) {
        mListGoals = listGoals;
    }

    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Definir o layout de cada item
        View view = from(parent.getContext())
                .inflate(R.layout.goals_item, parent, false);
        //Criar uma viewHolder
        return new GoalsViewHolder(GoalsItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {
        holder.bind(mListGoals.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mListGoals.size();
    }

    public void setItems(List<GoalItem> list) {
        mListGoals = list;
        notifyDataSetChanged();
    }

    public class GoalsViewHolder extends RecyclerView.ViewHolder {

        private GoalsItemBinding mBinding;

        public GoalsViewHolder(GoalsItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(GoalItem item, int position) {
            mBinding.goalTitle.setText(item.getGoalTitle());
            mBinding.textPoints.setText(item.getPoints() + " pts");

            mBinding.getRoot().setOnLongClickListener(v -> false);
        }
    }

    public static class GoalItem {

        private String mGoalTitle;
        private String mPoints;
        private boolean mIsDone;

        public GoalItem(String mGoalTitle, String mPoints, boolean mIsDone) {
            this.mGoalTitle = mGoalTitle;
            this.mPoints = mPoints;
            this.mIsDone = mIsDone;
        }

        public GoalItem() {

        }

        public String getGoalTitle() {
            return mGoalTitle;
        }

        public void setGoalTitle(String mGoalTitle) {
            this.mGoalTitle = mGoalTitle;
        }

        public String getPoints() {
            return mPoints;
        }

        public void setPoints(String mPoints) {
            this.mPoints = mPoints;
        }

        public boolean isIsDone() {
            return mIsDone;
        }

        public void setIsDone(boolean mIsDone) {
            this.mIsDone = mIsDone;
        }
    }
}
