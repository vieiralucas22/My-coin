package com.example.mycoin.services;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.callbacks.LoadUsersCallback;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.callbacks.UploadPhotoCallback;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;
import com.example.mycoin.fragments.ranking.RankingListAdapter;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        newUser.put(Constants.POINTS, "");

        DocumentReference userRef = mFirebaseFirestore.collection("Users").document(user.getEmail());
        userRef.set(user).addOnSuccessListener(aVoid -> {
                    System.out.println("User added with email ID: " + user.getEmail());

                    addSubCollections(user.getEmail());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding user: " + e.getMessage());
                });
    }

    private void addSubCollections(String email) {
        addIntroductionModule(email);
        addObjectiveModule(email);
        addActionModule(email);
        addExtraModule(email);
    }

    private void addIntroductionModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.INTRODUCTION).document("0");
        userRef.set(createClass("Lesson 1", "What`s financial education?"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.INTRODUCTION).document("1");
        userRef.set(createClass("Lesson 2", "How can we start?"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.INTRODUCTION).document("2");
        userRef.set(createClass("Lesson 3", "Organize your home"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.INTRODUCTION).document("3");
        userRef.set(createClass("Lesson 4", "Define your target"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.INTRODUCTION).document("4");
        userRef.set(createClass("Lesson 5", "The value of tomorrow"), SetOptions.merge());
    }

    private void addObjectiveModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.ORGANIZE_HOME).document("0");
        userRef.set(createClass("Lesson 6", "Basic concepts about financial education"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.ORGANIZE_HOME).document("1");
        userRef.set(createClass("Lesson 7", "Let`s organize your home"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.ORGANIZE_HOME).document("2");
        userRef.set(createClass("Lesson 8", "Define your goal"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.ORGANIZE_HOME).document("3");
        userRef.set(createClass("Lesson 9", "Define your goal example"), SetOptions.merge());
    }

    private void addActionModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.ACTION_TIME).document("0");
        userRef.set(createClass("Lesson 10", "Compound interest"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.ACTION_TIME).document("1");
        userRef.set(createClass("Lesson 11", "Let`s talk about fixed income"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.ACTION_TIME).document("2");
        userRef.set(createClass("Lesson 12", "Let`s talk about equities"), SetOptions.merge());
    }

    private void addExtraModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.EXTRA).document("0");
        userRef.set(createClass("Lesson 13", "Rich dad Poor dad"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(Constants.USERS).document(email).collection(Constants.EXTRA).document("1");
        userRef.set(createClass("Lesson 14", "The Richest Man In Babylon"), SetOptions.merge());
    }

    private Map<String, Object> createClass(String title, String description) {
        Map<String, Object> lesson = new HashMap<>();
        lesson.put(Constants.TITLE, title);
        lesson.put(Constants.DESCRIPTION, description);
        lesson.put(Constants.CLASS_DONE, false);
        return lesson;
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
    public void setUserByEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (TextUtils.isEmpty(user.getEmail())) return;

        mFirebaseFirestore.collection(Constants.USERS).document(user.getEmail())
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
                    String downloadUri = uri1.getResult().toString();
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

    @Override
    public void updateUser(User user) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) return;

        String userPoints = String.valueOf(user.getPoints());
        mFirebaseFirestore.collection(Constants.USERS).document(currentUser.getUid())
                .update(Constants.POINTS, userPoints).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mAppPreferences.setCurrentUser(user);
                    }
                });
    }

    @Override
    public void getClassesByModule(String module, LoadClassesCallback callback) {
        List<ClassAdapter.ClassItem> classItemList = new ArrayList<>();

        User user = mAppPreferences.getCurrentUser();

        mFirebaseFirestore.collection(Constants.USERS).document(user.getEmail())
                .collection(module).get().addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        for (DocumentSnapshot x : task.getResult().getDocuments()) {
                            ClassAdapter.ClassItem classItem = new ClassAdapter.ClassItem();
                            classItem.setTitle(x.getString(Constants.TITLE));
                            classItem.setDescription(x.getString(Constants.DESCRIPTION));
                            classItem.setIsDone(Boolean.TRUE.equals(x.getBoolean(Constants.CLASS_DONE)));
                            classItemList.add(classItem);
                        }
                        callback.onSuccess(classItemList);
                        return;
                    }
                    callback.onFailure("Classes not loaded");
                });
    }

    @Override
    public void updateClassState(int position, boolean checked, String module) {
        String document = String.valueOf(position);
        mFirebaseFirestore.collection(module).document(document)
                .update(Constants.CLASS_DONE, checked);
    }

    @Override
    public void getAllUsers(LoadUsersCallback callback) {
        List<RankingListAdapter.RankingItem> rankingItems = new ArrayList<>();
        mFirebaseFirestore.collection(Constants.USERS).get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                for (DocumentSnapshot x : task.getResult().getDocuments()) {
                    RankingListAdapter.RankingItem item = new RankingListAdapter.RankingItem();

                    item.setName(x.getString(Constants.NAME));
                    item.setPoints(x.getString(Constants.POINTS));
                    item.setPlayerPhoto(x.getString(Constants.PHOTO));
                    rankingItems.add(item);
                }
                callback.onSuccess(rankingItems);
                return;
            }
            callback.onFailure("Users not loaded");
        });

    }

    private User getUser(DocumentSnapshot document) {
        User user = new User();
        user.setName(document.getString(Constants.NAME));
        user.setEmail(document.getString(Constants.EMAIL));
        user.setBirthDate(document.getString(Constants.BIRTHDATE));
        user.setPassword(document.getString(Constants.PASSWORD));
        user.setPoints(Integer.parseInt(document.getString(Constants.POINTS)));
        user.setPhoto(document.getString(Constants.PHOTO));
        return user;
    }
}
