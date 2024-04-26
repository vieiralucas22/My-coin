package com.example.mycoin.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.callbacks.UserExistCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.LogcatUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    @Override
    public void changePassword(String newPassword, ChangePasswordCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        User currentUser = mAppPreferences.getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(currentUser.getEmail(), currentUser.getPassword());

        user.reauthenticate(credential)
                .addOnCompleteListener(isAuthenticate -> {
                    if (isAuthenticate.isSuccessful()) {
                        user.updatePassword(newPassword).addOnCompleteListener(change -> {
                            if (change.isSuccessful()) {
                                mFirebaseFirestore.collection(Constants.USERS).document(currentUser.getEmail())
                                        .update(Constants.PASSWORD, newPassword).addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                currentUser.setPassword(newPassword);
                                                mAppPreferences.setCurrentUser(currentUser);
                                                callback.onSuccess();
                                                return;
                                            }
                                            callback.onFailure(R.string.change_password_fail);
                                        });
                            }
                        });
                    }
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
    public void sendLink(ChangePasswordCallback callback) {
        mAuth.sendPasswordResetEmail(mAppPreferences.getUserEmail()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Link sent successful! " + task);
                mAppPreferences.removeConfirmationCode();
                mAppPreferences.removeUserEmail();
                callback.onSuccess();
                return;
            }
            callback.onFailure(R.string.send_link_fail);
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

    @Override
    public void editUser(String name, String dateBirth, UserDataChangeCallback callback) {
        User currentUser = mAppPreferences.getCurrentUser();

        Map<String, Object> updates = new HashMap<>();
        updates.put(Constants.NAME, name);
        updates.put(Constants.BIRTHDATE, dateBirth);

        mFirebaseFirestore.collection(Constants.USERS).document(currentUser.getEmail())
                .update(updates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentUser.setName(name);
                        currentUser.setBirthDate(dateBirth);
                        mAppPreferences.setCurrentUser(currentUser);
                        callback.onSuccess();
                        return;
                    }
                    callback.onFailure();
                });
    }

    private User getUser(DocumentSnapshot document) {
        User user = new User();
        user.setName(document.getString(Constants.NAME));
        user.setEmail(document.getString(Constants.EMAIL));
        user.setBirthDate(document.getString(Constants.BIRTHDATE));
        user.setPassword(document.getString(Constants.PASSWORD));
        return user;
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
