package com.example.mycoin.fragments.profile.editprofile;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.UserDataChangeCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class EditUserProfileViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(EditUserProfileViewModel.class);

    private final FirebaseAuth mAuth;
    private final EditProfile mEditProfile;
    private final Context mContext;

    private final MutableLiveData<User> mUserDataLoaded = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHandleResponseLayout = new MutableLiveData<>();


    @Inject
    public EditUserProfileViewModel(FirebaseAuth auth, EditProfile editProfile, Context context) {
        mAuth = auth;
        mEditProfile = editProfile;
        mContext = context;
    }

    public void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) return;

        User currentUser = mEditProfile.loadDataUser();
        mUserDataLoaded.postValue(currentUser);
    }

    public MutableLiveData<User> getUserDataLoaded() {
        return mUserDataLoaded;
    }

    public void editUserData(String name, String dataBirth) {
        mEditProfile.editUserData(name, dataBirth, new UserDataChangeCallback() {
            @Override
            public void onSuccess() {
                MessageUtil.showToast(mContext, R.string.edit_user_data_success);
                mHandleResponseLayout.setValue(true);
            }

            @Override
            public void onFailure() {
                MessageUtil.showToast(mContext, R.string.edit_user_data_fail);
                mHandleResponseLayout.setValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getHandleResponseLayout() {
        return mHandleResponseLayout;
    }
}