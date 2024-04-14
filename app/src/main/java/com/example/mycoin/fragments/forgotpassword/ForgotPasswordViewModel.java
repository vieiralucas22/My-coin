package com.example.mycoin.fragments.forgotpassword;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.usecases.interfaces.SendForgotPasswordEmail;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class ForgotPasswordViewModel extends ViewModel {

    public static final String TAG = LogcatUtil.getTag(ForgotPasswordViewModel.class);

    private final SendForgotPasswordEmail mSendForgotPasswordEmail;

    private MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();

    @Inject
    public ForgotPasswordViewModel(SendForgotPasswordEmail sendForgotPasswordEmail) {
        mSendForgotPasswordEmail = sendForgotPasswordEmail;
    }

    public void sendEmailToGetConfirmCode(String email) {
        if (TextUtils.isEmpty(email)) {
            return;
        }

        if (mSendForgotPasswordEmail.trySendEmailToGetCode(email)) {
            mNeedNavigate.postValue(true);
            return;
        }
        mNeedNavigate.postValue(false);
    }

    public MutableLiveData<Boolean> getNeedNavigate() {
        return mNeedNavigate;
    }

}