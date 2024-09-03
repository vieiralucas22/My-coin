package com.example.mycoin.services;

import static com.example.mycoin.constants.Constants.CLASSES;
import static com.example.mycoin.constants.Constants.USERS;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.mycoin.GameStatus;
import com.example.mycoin.R;
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
import com.example.mycoin.callbacks.VideosCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.Goal;
import com.example.mycoin.entities.User;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;
import com.example.mycoin.fragments.goals.GoalsAdapter;
import com.example.mycoin.fragments.ranking.RankingListAdapter;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.ListUtil;
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
import java.util.Collections;
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
    private final Context mContext;

    @Inject
    public FirebaseServiceImpl(FirebaseAuth auth, FirebaseFirestore firebaseFirestore,
                               AppPreferences appPreferences, StorageReference storageReference,
                               Context context) {
        mAuth = auth;
        mFirebaseFirestore = firebaseFirestore;
        mStorageReference = storageReference;
        mAppPreferences = appPreferences;
        mContext = context;
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
                                mFirebaseFirestore.collection(USERS).document(currentUser.getEmail())
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
        newUser.put(Constants.POINTS, "0");

        DocumentReference userRef = mFirebaseFirestore.collection(USERS).document(user.getEmail());
        userRef.set(newUser).addOnSuccessListener(aVoid -> {
                    Log.i(TAG, "User added with email ID: " + user.getEmail());
                    addSubCollections(user.getEmail());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding user: " + e.getMessage());
                });
    }

    private void addSubCollections(String email) {
        addIntroductionModule(email);
        addObjectiveModule(email);
        addActionModule(email);
        addExtraModule(email);
        addGoalsCollection(email);
    }

    private void addIntroductionModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.INTRODUCTION).document("0");
        userRef.set(createClass(mContext.getString(R.string.lesson_1), "What`s financial education?", "0"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.INTRODUCTION).document("1");
        userRef.set(createClass(mContext.getString(R.string.lesson_2), "How can we start?", "1"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.INTRODUCTION).document("2");
        userRef.set(createClass(mContext.getString(R.string.lesson_3), "Organize your home", "2"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.INTRODUCTION).document("3");
        userRef.set(createClass(mContext.getString(R.string.lesson_4), "Define your target", "3"), SetOptions.merge());

    }

    private void addObjectiveModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.ORGANIZE_HOME).document("0");
        userRef.set(createClass(mContext.getString(R.string.lesson_5), "Basic concepts about financial education", "4"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.ORGANIZE_HOME).document("1");
        userRef.set(createClass(mContext.getString(R.string.lesson_6), "Let`s organize your home", "5"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.ORGANIZE_HOME).document("2");
        userRef.set(createClass(mContext.getString(R.string.lesson_7), "Define your goal", "6"), SetOptions.merge());
    }

    private void addActionModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.ACTION_TIME).document("0");
        userRef.set(createClass(mContext.getString(R.string.lesson_8), "Compound interest", "7"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.ACTION_TIME).document("1");
        userRef.set(createClass(mContext.getString(R.string.lesson_9), "Let`s talk about fixed income", "8"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.ACTION_TIME).document("2");
        userRef.set(createClass(mContext.getString(R.string.lesson_10), "Let`s talk about equities", "9"), SetOptions.merge());
    }

    private void addExtraModule(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.EXTRA).document("0");
        userRef.set(createClass(mContext.getString(R.string.lesson_11), "Rich dad Poor dad", "10"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.EXTRA).document("1");
        userRef.set(createClass(mContext.getString(R.string.lesson_12), "The Richest Man In Babylon", "11"), SetOptions.merge());
    }

    private void addGoalsCollection(String email) {
        DocumentReference userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.GOALS).document("0");
        userRef.set(createGoal(mContext.getString(R.string.goal_one), "10"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.GOALS).document("1");
        userRef.set(createGoal(mContext.getString(R.string.goal_two), "20"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.GOALS).document("2");
        userRef.set(createGoal(mContext.getString(R.string.goal_three), "20"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.GOALS).document("3");
        userRef.set(createGoal(mContext.getString(R.string.goal_four), "20"), SetOptions.merge());
        userRef = mFirebaseFirestore.collection(USERS).document(email)
                .collection(Constants.GOALS).document("4");
        userRef.set(createGoal(mContext.getString(R.string.goal_five), "20"), SetOptions.merge());
    }

    private Map<String, Object> createClass(String title, String description, String id) {
        Map<String, Object> lesson = new HashMap<>();
        lesson.put(Constants.TITLE, title);
        lesson.put(Constants.DESCRIPTION, description);
        lesson.put(Constants.CLASS_DONE, false);
        lesson.put(Constants.ID, id);
        return lesson;
    }

    private Map<String, Object> createGoal(String goalTitle, String points) {
        Map<String, Object> goal = new HashMap<>();
        goal.put(Constants.GOAL, goalTitle);
        goal.put(Constants.POINTS, points);
        goal.put(Constants.GOAL_DONE, false);
        return goal;
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

        mFirebaseFirestore.collection(USERS).document(user.getEmail())
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

        mFirebaseFirestore.collection(USERS).document(currentUser.getEmail())
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
                    mFirebaseFirestore.collection(USERS).document(user.getEmail())
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

        if (currentUser.getEmail() == null) return;

        String userPoints = String.valueOf(user.getPoints());
        mFirebaseFirestore.collection(USERS).document(currentUser.getEmail())
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

        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(module).get().addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        for (DocumentSnapshot x : task.getResult().getDocuments()) {
                            ClassAdapter.ClassItem classItem = new ClassAdapter.ClassItem();
                            classItem.setTitle(x.getString(Constants.TITLE));
                            classItem.setDescription(x.getString(Constants.DESCRIPTION));
                            classItem.setIsDone(Boolean.TRUE.equals(x.getBoolean(Constants.CLASS_DONE)));
                            Log.d(TAG, "ID - " + x.get(Constants.ID));
                            classItem.setId(x.getString(Constants.ID));
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

        User user = mAppPreferences.getCurrentUser();

        mFirebaseFirestore.collection(USERS).document(user.getEmail()).collection(module)
                .document(document).update(Constants.CLASS_DONE, checked);
    }

    @Override
    public void getAllUsers(LoadUsersCallback callback) {
        List<RankingListAdapter.RankingItem> rankingItems = new ArrayList<>();
        mFirebaseFirestore.collection(USERS).get().addOnCompleteListener(task -> {
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

    @Override
    public void getAllGoals(LoadGoalsCallback loadGoalsCallback) {
        List<GoalsAdapter.GoalItem> goalList = new ArrayList<>();

        User user = mAppPreferences.getCurrentUser();

        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).get().addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        for (DocumentSnapshot item : task.getResult().getDocuments()) {
                            GoalsAdapter.GoalItem goalItem = new GoalsAdapter.GoalItem();
                            goalItem.setGoalTitle(item.getString(Constants.GOAL));
                            goalItem.setPoints(item.getString(Constants.POINTS));
                            goalItem.setIsDone(Boolean.TRUE.equals(item.getBoolean(Constants.GOAL_DONE)));
                            goalList.add(goalItem);
                        }
                        loadGoalsCallback.onSuccess(goalList);
                        return;
                    }
                    loadGoalsCallback.onFailure("Goals not loaded");
                });
    }

    @Override
    public void completeUserGoal(String goalCompleted) {
        User user = mAppPreferences.getCurrentUser();

        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).get().addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        for (DocumentSnapshot goal : task.getResult().getDocuments()) {
                            if (goal.getString(Constants.GOAL).equals(goalCompleted)) {
                                mFirebaseFirestore.collection(USERS).document(user.getEmail())
                                        .collection(Constants.GOALS).document(goal.getId())
                                        .update(Constants.GOAL_DONE, true);
                            }
                        }
                    }
                });
    }

    @Override
    public void getGoal(String goalCompleted, GoalCallback goalCallback) {
        Goal goal = new Goal();

        User user = mAppPreferences.getCurrentUser();

        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).get().addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        for (DocumentSnapshot item : task.getResult().getDocuments()) {
                            if (item.getString(Constants.GOAL).equals(goalCompleted)) {
                                goal.setDescription(item.getString(Constants.GOAL));
                                goal.setPoints(Integer.parseInt(item.getString(Constants.POINTS)));
                                goal.setIsDone(Boolean.TRUE.equals(item.getBoolean(Constants.GOAL_DONE)));
                            }
                        }
                        goalCallback.onSuccess(goal);
                        return;
                    }
                    goalCallback.onFailure();
                });
    }

    @Override
    public void addRoom(int roomCode, RoomCreatedCallback roomCreatedCallback) {
        String roomDocument = String.valueOf(roomCode);

        List<String> users = Collections.singletonList(mAppPreferences.getCurrentUser().getEmail());

        Map<String, Object> newRoom = new HashMap<>();
        newRoom.put(Constants.WINNER, "");
        newRoom.put(Constants.PLAYER_ONE_POINTS, 0);
        newRoom.put(Constants.PLAYER_TWO_POINTS, 0);
        newRoom.put(Constants.PLAYER_ONE_FINISH_GAME, false);
        newRoom.put(Constants.PLAYER_TWO_FINISH_GAME, false);
        newRoom.put(Constants.GAME_STATUS, GameStatus.NOT_STARTED);
        newRoom.put(Constants.PLAYERS, users);
        newRoom.put(Constants.PLAYER_ONE, mAppPreferences.getCurrentUser().getEmail());
        newRoom.put(Constants.PLAYER_TWO, "");

        mFirebaseFirestore.collection(Constants.ROOMS).document(roomDocument)
                .set(newRoom).addOnCompleteListener(task -> {
                    if (task.isComplete()) {
                        Log.d(TAG, "Room " + roomCode + " created!");
                        roomCreatedCallback.onSuccess(roomCode);
                        return;
                    }
                    roomCreatedCallback.onFailure();
                });
    }

    @Override
    public void addUserInRoom(int roomCode, JoinRoomCallback joinRoomCallback) {
        String roomDocument = String.valueOf(roomCode);

        User currentUser = mAppPreferences.getCurrentUser();

        mFirebaseFirestore.collection(Constants.ROOMS).document(roomDocument)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot data = task.getResult();
                        List<String> players = (List<String>) data.get(Constants.PLAYERS);

                        if (ListUtil.isEmpty(players)) {
                            joinRoomCallback.onFailure();
                            return;
                        }

                        players.add(currentUser.getEmail());
                        mFirebaseFirestore.collection(Constants.ROOMS)
                                .document(roomDocument).update(Constants.PLAYERS, players);
                        joinRoomCallback.onSuccess(roomCode);
                    }
                });
    }

    @Override
    public void updateDataLanguage() {
        User user = mAppPreferences.getCurrentUser();
        Log.d(TAG, mContext.getString(R.string.goal_one));
        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).document("0").update(Constants.GOAL, mContext.getString(R.string.goal_one));
        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).document("1").update(Constants.GOAL, mContext.getString(R.string.goal_two));
        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).document("2").update(Constants.GOAL, mContext.getString(R.string.goal_three));
        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).document("3").update(Constants.GOAL, mContext.getString(R.string.goal_four));
        mFirebaseFirestore.collection(USERS).document(user.getEmail())
                .collection(Constants.GOALS).document("4").update(Constants.GOAL, mContext.getString(R.string.goal_five));
    }

    @Override
    public void getVideosByModule(String module, VideosCallback videosCallback) {
        List<String> videos = new ArrayList<>();
        mFirebaseFirestore.collection(CLASSES).get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                for (DocumentSnapshot video : task.getResult().getDocuments()) {
                    videos.add(video.getString(Constants.LINK));
                }

                videosCallback.onSuccess(videos);
                return;
            }
            videosCallback.onFailure("Users not loaded");
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
