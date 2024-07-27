package com.example.mycoin.fragments.codematch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.RoomCreatedCallback;
import com.example.mycoin.gateway.repository.MatchRepository;

import java.util.Random;

import javax.inject.Inject;

public class CodeMatchViewModel extends ViewModel {

    public MatchRepository mMatchRepository;

    public MutableLiveData<Integer> mLoadRoomCode = new MutableLiveData<>();

    @Inject
    public CodeMatchViewModel(MatchRepository matchRepository) {
        mMatchRepository = matchRepository;
    }

    public void createRoom() {
        Random random = new Random();
        int roomCode = random.nextInt(5000);
        mMatchRepository.createRoom(roomCode, new RoomCreatedCallback() {
            @Override
            public void onSuccess(int roomCode) {
                mLoadRoomCode.postValue(roomCode);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public MutableLiveData<Integer> getLoadRoomCode() {
        return mLoadRoomCode;
    }
}