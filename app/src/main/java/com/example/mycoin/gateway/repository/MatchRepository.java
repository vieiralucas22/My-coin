package com.example.mycoin.gateway.repository;

import com.example.mycoin.callbacks.JoinRoomCallback;
import com.example.mycoin.callbacks.RoomCreatedCallback;

public interface MatchRepository {
    void createRoom(String mItemSelected, int roomCode, RoomCreatedCallback roomCreatedCallback);
    void joinRoom(int roomCode, JoinRoomCallback joinRoomCallback);
}
