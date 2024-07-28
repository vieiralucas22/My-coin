package com.example.mycoin.repository;

import com.example.mycoin.callbacks.JoinRoomCallback;
import com.example.mycoin.callbacks.RoomCreatedCallback;
import com.example.mycoin.gateway.repository.MatchRepository;
import com.example.mycoin.gateway.services.FirebaseService;

import javax.inject.Inject;

public class MatchRepositoryImpl implements MatchRepository {

    private FirebaseService mFirebaseService;

    @Inject
    public MatchRepositoryImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public void createRoom(int roomCode, RoomCreatedCallback roomCreatedCallback) {
        mFirebaseService.addRoom(roomCode, roomCreatedCallback);
    }

    @Override
    public void joinRoom(int roomCode, JoinRoomCallback joinRoomCallback) {
        mFirebaseService.addUserInRoom(roomCode, joinRoomCallback);
    }
}
