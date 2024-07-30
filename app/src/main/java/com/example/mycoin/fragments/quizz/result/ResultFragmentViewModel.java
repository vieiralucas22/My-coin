package com.example.mycoin.fragments.quizz.result;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.constants.Constants;
import com.example.mycoin.utils.LogcatUtil;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

public class ResultFragmentViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(ResultFragmentViewModel.class);

    private final FirebaseFirestore mFirebaseFireStore;

    @Inject
    public ResultFragmentViewModel(FirebaseFirestore firebaseFirestore) {
        mFirebaseFireStore = firebaseFirestore;
    }

    public void initFirebaseListener(int roomCode) {
        String documentRoom = String.valueOf(roomCode);
        Log.d(TAG, documentRoom);
        mFirebaseFireStore.collection(Constants.ROOMS).document(documentRoom).addSnapshotListener(
                (value, error) -> {
                    Log.d(TAG, "Ouvi no result");
        });
    }
}
