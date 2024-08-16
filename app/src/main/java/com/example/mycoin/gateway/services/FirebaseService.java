package com.example.mycoin.gateway.services;

import android.net.Uri;

import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.GoalCallback;
import com.example.mycoin.callbacks.JoinRoomCallback;
import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.callbacks.LoadGoalsCallback;
import com.example.mycoin.callbacks.LoadUsersCallback;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.callbacks.RoomCreatedCallback;
import com.example.mycoin.callbacks.UploadPhotoCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.entities.User;

public interface FirebaseService {
    void authenticate(String email, String password, LoginCallback loginCallback);
    void signUp(User user, RegisterCallback registerCallback);
    void changePassword(String newPassword, ChangePasswordCallback callback);
    void sendLink(ChangePasswordCallback callback);
    void setUserByEmail();
    void editUser(String name, String dataBirth, UserDataChangeCallback callback);
    void uploadPhoto(Uri uri, UploadPhotoCallback uploadPhotoCallback);
    void updateUser(User user);
    void getClassesByModule(String module, LoadClassesCallback callback);
    void updateClassState(int position, boolean checked, String module);
    void getAllUsers(LoadUsersCallback loadUsersCallback);
    void getAllGoals(LoadGoalsCallback loadGoalsCallback);
    void completeUserGoal(String goal);
    void getGoal(String goal, GoalCallback goalCallback);
    void addRoom(int roomCode, RoomCreatedCallback roomCreatedCallback);
    void addUserInRoom(int roomCode, JoinRoomCallback joinRoomCallback);
    void updateDataLanguage();
}
