package com.example.mycoin.fragments.quizz;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.inject.Inject;

public class QuizViewModel extends ViewModel {
    private static final String TAG = LogcatUtil.getTag(QuizViewModel.class);

    private final AppPreferences mAppPreferences;
    private final UserRepository mUserRepository;
    private final FirebaseFirestore mFirebaseFirestore;

    private String mRoomCode;
    private Boolean mIsOnlineMatch;
    private MutableLiveData<Boolean> mHasTwoPlayers = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsRoomRemoved = new MutableLiveData<>();

    @Inject
    public QuizViewModel(AppPreferences appPreferences, UserRepository userRepository,
                         FirebaseFirestore firebaseFirestore) {
        mAppPreferences = appPreferences;
        mUserRepository = userRepository;
        mFirebaseFirestore = firebaseFirestore;
    }

    public void saveUserPoints(int pointsGained) {
        User user = mAppPreferences.getCurrentUser();
        int newUserPoints = user.getPoints() + pointsGained;
        user.setPoints(newUserPoints);
        mUserRepository.updateCurrentUser(user);
    }

    public void startGame() {

    }

    public LiveData<Boolean> hasMinimumPlayersInRoom() {
        return mHasTwoPlayers;
    }

    public void initFirebaseStoreObserve() {
        if (!mIsOnlineMatch) return;
        mFirebaseFirestore.collection(Constants.ROOMS).document(mRoomCode).addSnapshotListener(
                (value, error) -> {
                    if (value == null) return;

                    if (!value.exists()) {
                        Log.d(TAG, "Owner exit room!");
                        mIsRoomRemoved.postValue(true);
                        return;
                    }

                    List<String> players = (List<String>) value.get(Constants.PLAYERS);

                    mHasTwoPlayers.postValue(players.size() == 2);

                });
    }

    public void setRoomCode(int code) {
        mRoomCode = String.valueOf(code);
    }

    public void handleExitRoom(boolean ownerRoom) {
        if (!mIsOnlineMatch) return;

        if (ownerRoom) {
            mFirebaseFirestore.collection(Constants.ROOMS).document(mRoomCode).delete();
        } else {
            mFirebaseFirestore.collection(Constants.ROOMS).document(mRoomCode).get().addOnCompleteListener( task -> {
               if (task.isComplete()) {
                   List<String> players = (List<String>) task.getResult().get(Constants.PLAYERS);
                   players.remove(players.size()-1);
                   mFirebaseFirestore.collection(Constants.ROOMS)
                           .document(mRoomCode).update(Constants.PLAYERS, players);
               }
            });
        }
    }

    public void setIsOnlineMatch(int mRoomCode) {
        mIsOnlineMatch = mRoomCode != -1;
    }

    public boolean isOnlineMatch() {
        return mIsOnlineMatch;
    }

    public LiveData<Boolean> isRoomRemoved() {
        return mIsRoomRemoved;
    }
}
