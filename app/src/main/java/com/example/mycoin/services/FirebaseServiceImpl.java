package com.example.mycoin.services;

import android.util.Log;

import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.callbacks.UserDataCallback;
import com.example.mycoin.callbacks.UserExistCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class FirebaseServiceImpl implements FirebaseService {
    private static final String TAG = LogcatUtil.getTag(FirebaseServiceImpl.class);

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mFirebaseFirestore;
    private final AppPreferences mAppPreferences;

    @Inject
    public FirebaseServiceImpl(FirebaseAuth auth, FirebaseFirestore firebaseFirestore,
                               AppPreferences appPreferences) {
        mAuth = auth;
        mFirebaseFirestore = firebaseFirestore;
        mAppPreferences = appPreferences;
    }

    @Override
    public void authenticate(String email, String password, LoginCallback loginCallback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginCallback.onSuccess();
                return;
            }
            loginCallback.onFailure();
        });
    }

    @Override
    public void signUp(User user, RegisterCallback registerCallback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registerCallback.onSuccess();
                        createUser(user);
                        return;
                    }
                    registerCallback.onFailure();
                });
    }

    private void createUser(User user) {
        Map<String, String> newUser = new HashMap<>();
        newUser.put(Constants.NAME, user.getName());
        newUser.put(Constants.EMAIL, user.getEmail());
        newUser.put(Constants.PASSWORD, user.getPassword());
        newUser.put(Constants.BIRTHDATE, user.getBirthDate());

        mFirebaseFirestore.collection(Constants.USERS)
                .document(user.getEmail())
                .set(newUser).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User created!");
                        return;
                    }
                    Log.e(TAG, "User was not created!");
                });
    }

    @Override
    public void sendLinkToChangeForgotPassword(ChangePasswordCallback callback) {
        mAuth.sendPasswordResetEmail(mAppPreferences.getUserEmail()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Link sent successful! " + task);
                callback.onSuccess();
                return;
            }
            callback.onFailure();
        });
    }

    @Override
    public void setUserByEmail(String email) {
        mFirebaseFirestore.collection(Constants.USERS).document(email)
                .get().addOnSuccessListener(document -> {
                    if (document.exists()) {
                        mAppPreferences.setCurrentUser(getUser(document));
                    }
                });
    }

    private User getUser(DocumentSnapshot document) {
        User user = new User();
        user.setName(document.getString(Constants.NAME));
        user.setEmail(document.getString(Constants.EMAIL));
        user.setBirthDate(document.getString(Constants.BIRTHDATE));
        return user;
    }

    @Override
    public void changeForgotPassword(String newPassword, ChangePasswordCallback callback) {
        mFirebaseFirestore.collection(Constants.USERS).document(mAppPreferences.getUserEmail())
                .update(Constants.PASSWORD, newPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                        return;
                    }
                    callback.onFailure();
                });
    }

    private void checkUserExist(String email, UserExistCallback callBack) {
        mFirebaseFirestore.collection(Constants.USERS)
                .document(email).addSnapshotListener((value, error) -> {
                    if (value != null) {
                        callBack.exist(value.getString(Constants.PASSWORD));
                    }
                });
    }
}
