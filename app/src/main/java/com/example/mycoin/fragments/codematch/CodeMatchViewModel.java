package com.example.mycoin.fragments.codematch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.JoinRoomCallback;
import com.example.mycoin.callbacks.RoomCreatedCallback;
import com.example.mycoin.gateway.repository.MatchRepository;

import java.util.Random;

import javax.inject.Inject;

public class CodeMatchViewModel extends ViewModel {

    public MatchRepository mMatchRepository;

    public MutableLiveData<Integer> mLoadRoomCode = new MutableLiveData<>();
    public MutableLiveData<Integer> mJoinRoom = new MutableLiveData<>();

    public boolean mCanNavigate = false;

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

    public void joinRoom(String code) {

        int roomCode = Integer.parseInt(code);

        mMatchRepository.joinRoom(roomCode, new JoinRoomCallback(){
            @Override
            public void onSuccess(int roomCode) {
                mJoinRoom.postValue(roomCode);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public MutableLiveData<Integer> getLoadRoomCode() {
        return mLoadRoomCode;
    }

    public MutableLiveData<Integer> getJoinRoom() {
        return mJoinRoom;
    }

    public boolean canNavigate() {
        return mCanNavigate;
    }

    public void setCanNavigate(boolean canNavigate) {
        mCanNavigate = canNavigate;
    }
}