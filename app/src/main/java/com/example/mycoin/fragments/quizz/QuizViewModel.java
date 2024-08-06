package com.example.mycoin.fragments.quizz;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.GameStatus;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.ListUtil;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class QuizViewModel extends ViewModel {
    private static final String TAG = LogcatUtil.getTag(QuizViewModel.class);

    private final AppPreferences mAppPreferences;
    private final UserRepository mUserRepository;
    private final FirebaseFirestore mFirebaseFireStore;

    private MutableLiveData<Boolean> mIsOwnerRoom = new MutableLiveData<>();
    private MutableLiveData<GameStatus> mGameStatus = new MutableLiveData<>();

    private String mRoomCode;
    private Boolean mIsOnlineMatch;
    private Boolean mHasMinimumPLayersInRoom;
    private int mCorrectQuestions = 0;
    private int mWrongQuestions = 0;


    @Inject
    public QuizViewModel(AppPreferences appPreferences, UserRepository userRepository,
                         FirebaseFirestore firebaseFirestore) {
        mAppPreferences = appPreferences;
        mUserRepository = userRepository;
        mFirebaseFireStore = firebaseFirestore;
    }

    public void saveUserPoints(int pointsGained) {
        User user = mAppPreferences.getCurrentUser();
        int newUserPoints = user.getPoints() + pointsGained;
        user.setPoints(newUserPoints);
        mUserRepository.updateCurrentUser(user);
    }

    public void startGame() {
        if (mIsOnlineMatch) {
            mFirebaseFireStore.collection(Constants.ROOMS).document(mRoomCode)
                    .update(Constants.GAME_STATUS, GameStatus.STARTED);
        } else {
            mGameStatus.postValue(GameStatus.STARTED);
        }
    }

    public boolean hasMinimumPlayersInRoom() {
        return mHasMinimumPLayersInRoom;
    }

    public void setMinimumPlayersInRoom(boolean minimumPlayersInRoom) {
        mHasMinimumPLayersInRoom = minimumPlayersInRoom;
    }

    public void initFirebaseStoreObserve() {
        if (!mIsOnlineMatch) return;
        mFirebaseFireStore.collection(Constants.ROOMS).document(mRoomCode).addSnapshotListener(
                (value, error) -> {
                    Log.d(TAG, "Changes in Firebase!");
                    if (value == null) return;

                    List<String> players = (List<String>) value.get(Constants.PLAYERS);

                    if (ListUtil.isEmpty(players)) return;

                    handleWithInitGame(players, value);

                    handleWithGameStatus(value.getString(Constants.GAME_STATUS));

                });
    }

    private void handleWithInitGame(List<String> players, DocumentSnapshot value) {
        setMinimumPlayersInRoom(players.size() == 2);

        User user = mAppPreferences.getCurrentUser();

        mIsOwnerRoom.postValue(Objects.equals(value.getString(Constants.PLAYER_ONE), user.getEmail()));
    }

    private void handleWithGameStatus(String gameStatus) {

        switch (gameStatus) {

            case "NOT_STARTED": {
                Log.d(TAG, "Not Started!");
                break;
            }
            case "STARTED": {
                Log.d(TAG, "Game Started!");
                mGameStatus.postValue(GameStatus.STARTED);
                break;
            }
            case "RUNNING": {
                Log.d(TAG, "Game is running!");
                break;
            }
            case "FINISHED": {
                Log.d(TAG, "Game is finished!");
                break;
            }
        }

    }

    public void setRoomCode(int code) {
        mRoomCode = String.valueOf(code);
    }

    public void handleExitRoom() {
        if (!mIsOnlineMatch) return;

        User user = mAppPreferences.getCurrentUser();

        mFirebaseFireStore.collection(Constants.ROOMS).document(mRoomCode).get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                List<String> players = (List<String>) task.getResult().get(Constants.PLAYERS);

                if (ListUtil.isEmpty(players)) return;

                players.remove(user.getEmail());

                mFirebaseFireStore.collection(Constants.ROOMS)
                        .document(mRoomCode).update(Constants.PLAYERS, players);

                if (!hasMinimumPlayersInRoom()) return;

                if (user.getEmail().equals(task.getResult().getString(Constants.PLAYER_ONE))) {

                    if (Boolean.TRUE.equals(task.getResult().getBoolean(Constants.PLAYER_ONE_FINISH_GAME))) return;
                    mFirebaseFireStore.collection(Constants.ROOMS)
                            .document(mRoomCode).update(Constants.PLAYER_ONE, players.get(0));
                }
            }
        });

    }

    public void setIsOnlineMatch(int mRoomCode) {
        mIsOnlineMatch = mRoomCode != -1;
    }

    public boolean isOnlineMatch() {
        return mIsOnlineMatch;
    }

    public MutableLiveData<GameStatus> getGameStatus() {
        return mGameStatus;
    }

    public int getCorrectQuestions() {
        return mCorrectQuestions;
    }

    public void incrementCorrectQuestions(boolean ownerRoom) {
        mCorrectQuestions++;

        if (isOnlineMatch()) {

            String player = Boolean.TRUE.equals(mIsOwnerRoom.getValue())
                    ? Constants.PLAYER_ONE_POINTS : Constants.PLAYER_TWO_POINTS;
            mFirebaseFireStore.collection(Constants.ROOMS)
                    .document(mRoomCode).update(player, getPointsObtained());
            mGameStatus.postValue(GameStatus.RUNNING);
        }
    }

    public int getWrongQuestions() {
        return mWrongQuestions;
    }

    public void incrementWrongQuestions() {
        mWrongQuestions++;
    }

    private int getPointsObtained() {
        return mCorrectQuestions * 10;
    }

    public void defineGameStatusAsRunning() {
        if (isOnlineMatch()) {
            mFirebaseFireStore.collection(Constants.ROOMS).document(mRoomCode)
                    .update(Constants.GAME_STATUS, GameStatus.RUNNING);
        }
    }

    public void handleLastQuestionAnswer(boolean ownerRoom) {
        if (isOnlineMatch()) {
            String player = Boolean.TRUE.equals(mIsOwnerRoom.getValue())
                    ? Constants.PLAYER_ONE_FINISH_GAME : Constants.PLAYER_TWO_FINISH_GAME;

            mFirebaseFireStore.collection(Constants.ROOMS)
                    .document(mRoomCode).update(player, true);
        }
    }

    public LiveData<Boolean> getIsOwnerRoom() {
        return mIsOwnerRoom;
    }
}
