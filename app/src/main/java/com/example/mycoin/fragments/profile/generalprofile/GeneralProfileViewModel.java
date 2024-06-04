package com.example.mycoin.fragments.profile.generalprofile;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.UploadPhotoCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.User;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;

import com.example.mycoin.utils.MessageUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;


import javax.inject.Inject;

public class GeneralProfileViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(GeneralProfileViewModel.class);

    private final FirebaseAuth mAuth;
    private final EditProfile mEditProfile;
    private final Context mContext;
    private final FirebaseFirestore mStorage;
    private final AppPreferences mAppPreferences;

    private final MutableLiveData<User> loadUIUsersDetails = new MutableLiveData<>();

    @Inject
    public GeneralProfileViewModel(FirebaseAuth auth, EditProfile editProfile, Context context,
                                   FirebaseFirestore storage, AppPreferences appPreferences) {
        mAuth = auth;
        mEditProfile = editProfile;
        mContext = context;
        mStorage = storage;
        mAppPreferences = appPreferences;
    }

    public void uploadPhoto(Uri uri) {

        mEditProfile.uploadUserPhoto(uri, new UploadPhotoCallback() {
            @Override
            public void onSuccess() {
                MessageUtil.showToast(mContext, R.string.upload_success);
            }

            @Override
            public void onFailure(int messageError) {
                MessageUtil.showToast(mContext, messageError);
            }
        });
    }

    public void initUI() {
        User user = mAppPreferences.getCurrentUser();
        mStorage.collection(Constants.USERS).document(user.getEmail())
                .get().addOnSuccessListener(loadedData -> {
                    Log.d(TAG, user.toString());
                 //   loadUIUsersDetails.postValue(loadedData.getString(Constants.PHOTO));
                });
    }

    public boolean logout() {
        mAuth.signOut();
        mAppPreferences.removeCurrentUser();
        mAppPreferences.removeRememberMe();
        mAppPreferences.removeUserPassword();
        return mAuth.getUid() == null;
    }

    public LiveData<User> getLoadImage() {
        return loadUIUsersDetails;
    }
}