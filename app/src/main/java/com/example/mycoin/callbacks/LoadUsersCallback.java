package com.example.mycoin.callbacks;

import com.example.mycoin.fragments.ranking.RankingListAdapter;

import java.util.List;

public interface LoadUsersCallback {
    void onSuccess(List<RankingListAdapter.RankingItem> list);
    void onFailure(String message);
}
