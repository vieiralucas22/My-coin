package com.example.mycoin.fragments.ranking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.LoadUsersCallback;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.utils.LogcatUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class RankingViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(RankingViewModel.class);

    private UserRepository mUserRepository;
    private List<RankingListAdapter.RankingItem> mRankingItems;

    private MutableLiveData<Boolean> mUsersLoaded = new MutableLiveData<>();

    @Inject
    public RankingViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    public List<RankingListAdapter.RankingItem> getRankingItems() {
        return mRankingItems;
    }

    public void loadAllPlayer() {
        mRankingItems = new ArrayList<>();

        mUserRepository.getAllUsers(new LoadUsersCallback() {

            @Override
            public void onSuccess(List<RankingListAdapter.RankingItem> list) {
                list.sort((o1, o2) -> o2.getPoints().compareTo(o1.getPoints()));
                mRankingItems = list;
                mUsersLoaded.postValue(true);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public MutableLiveData<Boolean> getUsersLoaded() {
        return mUsersLoaded;
    }
}