package com.example.mycoin.callbacks;

public interface RoomCreatedCallback {
    void onSuccess(int roomCode);
    void onFailure();
}
