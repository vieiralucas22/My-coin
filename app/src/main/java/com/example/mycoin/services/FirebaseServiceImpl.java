package com.example.mycoin.services;

import android.net.Uri;
import android.util.Log;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.callbacks.UploadPhotoCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.callbacks.UserExistCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class FirebaseServiceImpl implements FirebaseService {
    private static final String TAG = LogcatUtil.getTag(FirebaseServiceImpl.class);

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mFirebaseFirestore;
    private final StorageReference mStorageReference;
    private final AppPreferences mAppPreferences;

    @Inject
    public FirebaseServiceImpl(FirebaseAuth auth, FirebaseFirestore firebaseFirestore,
                               AppPreferences appPreferences, StorageReference storageReference) {
        mAuth = auth;
        mFirebaseFirestore = firebaseFirestore;
        mStorageReference = storageReference;
        mAppPreferences = appPreferences;
    }

    @Override
    public void authenticate(String email, String password, LoginCallback loginCallback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginCallback.onSuccess();
                return;
            }
            loginCallback.onFailure(R.string.login_fail);
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
                    registerCallback.onFailure(R.string.register_fail);
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
        newUser.put(Constants.PHOTO, "");
        newUser.put(Constants.POINTS, String.valueOf(user.getPoints()));

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

    @Override
    public void uploadPhoto(Uri uri, UploadPhotoCallback uploadPhotoCallback) {
        String path = "UsersImages/" + mAuth.getUid();
        User user = mAppPreferences.getCurrentUser();

        StorageReference reference = mStorageReference.child(path);

        reference.putFile(uri).addOnSuccessListener(task -> {
            task.getStorage().getDownloadUrl().addOnCompleteListener(uri1 -> {
                if (uri1.isSuccessful()) {
                    String  downloadUri= uri1.getResult().toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(Constants.PHOTO, downloadUri);
                    mFirebaseFirestore.collection(Constants.USERS).document(user.getEmail())
                            .update(map).addOnCompleteListener(uploadFirestore -> {
                                if (uploadFirestore.isSuccessful()) {
                                    Log.d(TAG, "upload done");
                                    uploadPhotoCallback.onSuccess();
                                } else {
                                    Log.d(TAG, "upload fail");
                                    uploadPhotoCallback.onFailure(R.string.upload_fail);
                                }
                            });
                }
            });

        }).addOnFailureListener(e -> {
            Log.d(TAG, e.getMessage());
        });
    }

    private User getUser(DocumentSnapshot document) {
        User user = new User();
        user.setName(document.getString(Constants.NAME));
        user.setEmail(document.getString(Constants.EMAIL));
        user.setBirthDate(document.getString(Constants.BIRTHDATE));
        user.setPassword(document.getString(Constants.PASSWORD));
        user.setPoints(Integer.parseInt(document.getString(Constants.POINTS)));
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
