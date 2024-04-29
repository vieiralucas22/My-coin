package com.example.mycoin.fragments.profile.generalprofile;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import javax.inject.Inject;

public class GeneralProfileViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(GeneralProfileViewModel.class);

    private final FirebaseAuth mAuth;
    private final EditProfile mEditProfile;
    private final Context mContext;
    private final StorageReference mStorageReference;
    private final FirebaseFirestore storage;
    private final AppPreferences mAppPreferences;

    private MutableLiveData<String> loadImage = new MutableLiveData<>();


    @Inject
    public GeneralProfileViewModel(FirebaseAuth auth, EditProfile editProfile, Context context,
            StorageReference storageReference, FirebaseFirestore storage, AppPreferences appPreferences) {
        mAuth = auth;
        mEditProfile = editProfile;
        mContext = context;
        mStorageReference = storageReference;
        this.storage = storage;
        mAppPreferences = appPreferences;
    }

    public void uploadPhoto(Uri uri) {
        String path = "UsersImages/" + mAuth.getUid();
        User user = mAppPreferences.getCurrentUser();

        StorageReference reference = mStorageReference.child(path);

        reference.putFile(uri).addOnSuccessListener(task -> {
            Task<Uri> uriTask = task.getStorage().getDownloadUrl();
            while (!uriTask.isSuccessful()) {
                if (uriTask.isSuccessful()) {
                    uriTask.addOnSuccessListener(uri1 -> {
                       String downloadUri = uri1.toString();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(Constants.PHOTO, downloadUri);
                        storage.collection(Constants.USERS).document(user.getEmail())
                                .update(map).addOnCompleteListener(uploadFirestore -> {
                            if (uploadFirestore.isSuccessful()) {
                                Log.d(TAG, "upload done");
                            } else {
                                Log.d(TAG, "upload fail");
                            }
                        });

                    });
                }
            }
        }).addOnFailureListener(e -> {
            Log.d(TAG, e.getMessage());
        });
    }

    public void loadPhoto() {
        User user = mAppPreferences.getCurrentUser();
        storage.collection(Constants.USERS).document(user.getEmail())
                .get().addOnSuccessListener(loadedData -> {
                    loadImage.postValue(loadedData.getString(Constants.PHOTO));
                });

    }

    public boolean logout() {
        mAuth.signOut();
        mAppPreferences.removeCurrentUser();
        mAppPreferences.removeRememberMe();
        return mAuth.getUid() == null;
    }

    public MutableLiveData<String> getLoadImage() {
        return loadImage;
    }
}