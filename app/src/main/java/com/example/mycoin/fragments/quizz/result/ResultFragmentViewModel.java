package com.example.mycoin.fragments.quizz.result;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.GameStatus;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.utils.ListUtil;
import com.example.mycoin.utils.LogcatUtil;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.inject.Inject;

public class ResultFragmentViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(ResultFragmentViewModel.class);

    private final FirebaseFirestore mFirebaseFireStore;
    private MutableLiveData<DocumentSnapshot> mIsGameDone = new MutableLiveData<>();

    private String mRoomCode;
    private Boolean mIsOnlineMatch;

    @Inject
    public ResultFragmentViewModel(FirebaseFirestore firebaseFirestore) {
        mFirebaseFireStore = firebaseFirestore;
    }

    public void initFirebaseListener(int roomCode) {
        setIsOnlineMatch(roomCode);

        if (!mIsOnlineMatch) return;

        mRoomCode = String.valueOf(roomCode);

        mFirebaseFireStore.collection(Constants.ROOMS).document(mRoomCode).addSnapshotListener(
                (value, error) -> {
                    if (value == null) return;

                    checkIfMatchIsDone(value);
                });
    }

    private void checkIfMatchIsDone(DocumentSnapshot value) {
        if (Boolean.TRUE.equals(value.getBoolean(Constants.PLAYER_ONE_FINISH_GAME))
                && Boolean.TRUE.equals(value.getBoolean(Constants.PLAYER_TWO_FINISH_GAME))) {
            mFirebaseFireStore.collection(Constants.ROOMS).document(mRoomCode)
                    .update(Constants.GAME_STATUS, GameStatus.FINISHED);
            mFirebaseFireStore.collection(Constants.ROOMS).document(mRoomCode)
                    .update(Constants.WINNER, getWinner(value));

            mIsGameDone.postValue(value);
        }
    }

    private String getWinner(DocumentSnapshot value) {
        String winner = "Draw";
        long pointsPlayerOne = (long) value.get(Constants.PLAYER_ONE_POINTS);
        long pointsPlayerTwo = (long) value.get(Constants.PLAYER_ONE_POINTS);
        List<String> players = (List<String>) value.get(Constants.PLAYERS);

        if (ListUtil.isEmpty(players)) return winner;

        if (pointsPlayerOne > pointsPlayerTwo) {
            winner = players.get(0);
        } else {
            winner = players.get(1);
        }

        return winner;
    }

    public MutableLiveData<DocumentSnapshot> getIsGameDone() {
        return mIsGameDone;
    }

    public void setIsOnlineMatch(int mRoomCode) {
        mIsOnlineMatch = mRoomCode != -1;
    }

    public boolean isOnlineMatch() {
        return mIsOnlineMatch;
    }
}
