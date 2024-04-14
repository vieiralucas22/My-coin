package com.example.mycoin.fragments.confirmcode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.ChangePassword;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class ConfirmCodeViewModel extends ViewModel {

    private final AppPreferences mAppPreferences;
    private final ChangePassword mChangePassword;

    private MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();

    @Inject
    public ConfirmCodeViewModel(AppPreferences appPreferences, ChangePassword changePassword) {
        mAppPreferences = appPreferences;
        mChangePassword = changePassword;
    }

    public void confirmCode(int confirmationCode) {

        mChangePassword.changeForgotPassword(mAppPreferences.getUserEmail(), new ChangePasswordCallback() {
            @Override
            public void onSuccess() {
                if (confirmationCode == mAppPreferences.getConfirmationCode()) {
                    mNeedNavigate.postValue(true);
                }
            }

            @Override
            public void onFailure() {
                mNeedNavigate.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getNeedNavigate() {
        return mNeedNavigate;
    }

}