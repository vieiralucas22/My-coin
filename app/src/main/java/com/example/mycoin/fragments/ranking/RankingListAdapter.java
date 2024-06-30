package com.example.mycoin.fragments.ranking;

import static android.view.LayoutInflater.from;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.PlayerRankingItemBinding;
import com.example.mycoin.utils.LogcatUtil;

import java.util.List;

public class RankingListAdapter
        extends RecyclerView.Adapter<RankingListAdapter.RankingListViewHolder> {

    private static final String TAG = LogcatUtil.getTag(RankingListAdapter.class);

    private List<RankingItem> mList;

    public RankingListAdapter(List<RankingItem> list) {
        mList = list;
    }

    @NonNull
    @Override
    public RankingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Definir o layout de cada item
        View view = from(parent.getContext())
                .inflate(R.layout.player_ranking_item, parent, false);
        //Criar uma viewHolder
        return new RankingListAdapter.RankingListViewHolder(PlayerRankingItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull RankingListViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setItems(List<RankingItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public static class RankingListViewHolder extends RecyclerView.ViewHolder {

    private PlayerRankingItemBinding mBinding;

    public RankingListViewHolder(PlayerRankingItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(RankingItem item) {
        Uri uri = Uri.parse(item.getPlayerPhoto());
        mBinding.playerPhoto.setImageURI(uri);
        mBinding.playerName.setText(item.getName());
        mBinding.playerPoints.setText(item.getPoints());
    }
}

    public static class RankingItem {
        private String mPlayerPhoto;
        private String mName;
        private String mPoints;

        public RankingItem() {
        }

        public String getPlayerPhoto() {
            return mPlayerPhoto;
        }

        public void setPlayerPhoto(String mPlayerPhoto) {
            this.mPlayerPhoto = mPlayerPhoto;
        }

        public String getName() {
            return mName;
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public String getPoints() {
            return mPoints;
        }

        public void setPoints(String mPoints) {
            this.mPoints = mPoints;
        }
    }
}
