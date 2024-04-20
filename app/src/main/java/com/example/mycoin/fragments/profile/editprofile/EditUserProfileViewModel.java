package com.example.mycoin.fragments.profile.editprofile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.UserDataCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class EditUserProfileViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(EditUserProfileViewModel.class);

    private final FirebaseAuth mAuth;
    private final EditProfile mEditProfile;

    private final MutableLiveData<User> mUserDataLoaded = new MutableLiveData<>();

    @Inject
    public EditUserProfileViewModel(FirebaseAuth auth, EditProfile editProfile) {
        mAuth = auth;
        mEditProfile = editProfile;
    }

    public void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) return;

        User currentUser = mEditProfile.loadDataUser(user.getEmail());
        mUserDataLoaded.postValue(currentUser);
    }

    public MutableLiveData<User> getUserDataLoaded() {
        return mUserDataLoaded;
    }
}