package com.example.mycoin.fragments.login;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.usecases.interfaces.Login;
import com.example.mycoin.fragments.signup.SignUpViewModel;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();
    private final Login mLogin;
    private final Context mContext;
    private final AppPreferences mAppPreferences;

    private MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();

    @Inject
    public LoginViewModel(Login login, Context context, AppPreferences appPreferences) {
        mLogin = login;
        mContext = context;
        mAppPreferences = appPreferences;
    }

    public void login(String email, String password, boolean checked) {
         mLogin.authenticate(email, password, new LoginCallback() {

            @Override
            public void onSuccess() {
                mAppPreferences.setRememberMe(checked);
                mAppPreferences.setUserEmail(email);
                mNeedNavigate.postValue(true);
            }

            @Override
            public void onFailure() {
                MessageUtil.showToast(mContext, R.string.login_fail);
                mNeedNavigate.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getNeedNavigate() {
        return mNeedNavigate;
    }

    public boolean rememberMeWasChecked() {
        return mAppPreferences.getRememberMe();
    }
}