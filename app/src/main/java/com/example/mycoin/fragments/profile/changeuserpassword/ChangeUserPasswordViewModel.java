package com.example.mycoin.fragments.profile.changeuserpassword;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.ChangePasswordCallback;
import com.example.mycoin.usecases.interfaces.ChangePassword;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class ChangeUserPasswordViewModel extends ViewModel {
    private static final String TAG = LogcatUtil.getTag(ChangeUserPasswordViewModel.class);

    private final ChangePassword mChangePassword;
    private final Context mContext;

    private final MutableLiveData<Boolean> mHandleResponseLayout = new MutableLiveData<>();

    @Inject
    public ChangeUserPasswordViewModel(ChangePassword changePassword, Context context) {
        mChangePassword = changePassword;
        mContext = context;
    }

    public void changeUserPassword(String oldPassword, String newPassword, String confirmNewPassword) {

        mChangePassword.changePassword(oldPassword, newPassword, confirmNewPassword, new ChangePasswordCallback() {
            @Override
            public void onSuccess() {
                MessageUtil.showToast(mContext, R.string.change_password_success);
                mHandleResponseLayout.setValue(true);
            }

            @Override
            public void onFailure(int messageId) {
                MessageUtil.showToast(mContext, messageId);
                mHandleResponseLayout.setValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getHandleResponseLayout() {
        return mHandleResponseLayout;
    }

}