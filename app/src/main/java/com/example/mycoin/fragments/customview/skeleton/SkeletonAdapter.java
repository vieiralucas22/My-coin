package com.example.mycoin.fragments.customview.skeleton;

import static android.os.Build.VERSION_CODES.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mycoin.constants.Constants;

import com.example.mycoin.R;

import java.util.List;

public class SkeletonAdapter extends RecyclerView.Adapter<SkeletonAdapter.SkeletonViewHolder> {

    private List<Boolean> mSkeletonList;
    private String mLayoutSkeleton;

    public SkeletonAdapter(List<Boolean> skeletonList, String layout) {
        mSkeletonList = skeletonList;
        mLayoutSkeleton = layout;
    }

    @NonNull
    @Override
    public SkeletonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getSkeletonLayout(), parent, false);
        return new SkeletonViewHolder(view);
    }

    private int getSkeletonLayout() {
        switch (mLayoutSkeleton) {
            case Constants.SKELETON_CLASSES:{
                return com.example.mycoin.R.layout.skeleton_item;
            }
            case Constants.SKELETON_GOALS:{
                return com.example.mycoin.R.layout.skeleton_goal_item;
            }
            case Constants.SKELETON_PLAYERS:{
                return com.example.mycoin.R.layout.skeleton_player_item;
            }
            case Constants.SKELETON_PHOTO:{
                return 1;
            }

        }
        return com.example.mycoin.R.layout.skeleton_item;
    }

    @Override
    public void onBindViewHolder(@NonNull SkeletonViewHolder holder, int position) {
        // No binding necessary for skeleton view
    }

    @Override
    public int getItemCount() {
        return mSkeletonList.size();
    }

    static class SkeletonViewHolder extends RecyclerView.ViewHolder {
        SkeletonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}