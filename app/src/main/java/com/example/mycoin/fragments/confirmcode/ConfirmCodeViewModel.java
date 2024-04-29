package com.example.mycoin.fragments.confirmcode;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.fragments.login.LoginFragment;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.ChangePassword;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class ConfirmCodeViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(ConfirmCodeViewModel.class);

    private final ChangePassword mChangePassword;
    private final Context mContext;

    private final MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHandleResponseLayout = new MutableLiveData<>();

    @Inject
    public ConfirmCodeViewModel(ChangePassword changePassword, Context context) {
        mChangePassword = changePassword;
        mContext = context;
    }

    public void confirmCode(int confirmationCode) {
        if (confirmationCode == -1) {
            mHandleResponseLayout.postValue(false);
            MessageUtil.showToast(mContext, R.string.code_invalid);
            return;
        }

        mChangePassword.sendLinkToChangeForgotPassword(confirmationCode, new ChangePasswordCallback() {
            @Override
            public void onSuccess() {
                mNeedNavigate.postValue(true);
            }

            @Override
            public void onFailure(int messageId) {
                MessageUtil.showToast(mContext, messageId);
                mNeedNavigate.postValue(false);
                mHandleResponseLayout.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getNeedNavigate() {
        return mNeedNavigate;
    }

    public MutableLiveData<Boolean> getHandleResponseLayout() {
        return mHandleResponseLayout;
    }

    public void setUpUIToWaitResponse() {
        mHandleResponseLayout.setValue(true);
    }

    public boolean codeIsValid(String code1, String code2, String code3, String code4) {
        return !TextUtils.isEmpty(String.valueOf(code1))
                && !TextUtils.isEmpty(String.valueOf(code2))
                && !TextUtils.isEmpty(String.valueOf(code3))
                && !TextUtils.isEmpty(String.valueOf(code4));
    }
}