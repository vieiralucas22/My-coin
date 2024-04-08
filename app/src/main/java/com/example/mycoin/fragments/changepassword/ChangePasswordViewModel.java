package com.example.mycoin.fragments.changepassword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.usecases.interfaces.ChangePassword;

import javax.inject.Inject;

public class ChangePasswordViewModel extends ViewModel {

    private final ChangePassword mChangePassword;

    private MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();

    @Inject
    public ChangePasswordViewModel(ChangePassword changePassword) {
        mChangePassword = changePassword;
    }

    public void changePassword(String newPassword, String newConfirmedPassword) {
        mChangePassword.changeForgotPassword(newPassword, newConfirmedPassword, new ChangePasswordCallback() {

            @Override
            public void onSuccess() {
                mNeedNavigate.postValue(true);
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