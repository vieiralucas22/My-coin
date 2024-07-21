package com.example.mycoin.fragments.login;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.LoginCallback;
import com.example.mycoin.constants.PermissionsConstants;
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

    private final MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHandleResponseLayout = new MutableLiveData<>();

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
                mAppPreferences.setUserPassword(password);
                mAppPreferences.setRememberMe(checked);
                mNeedNavigate.postValue(true);
            }

            @Override
            public void onFailure(int messageError) {
                MessageUtil.showToast(mContext, messageError);
                mNeedNavigate.postValue(false);
                mHandleResponseLayout.setValue(false);
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

    public boolean rememberMeWasChecked() {
        return mAppPreferences.getRememberMe();
    }

    public boolean isNecessaryRequestNotificationPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && !(ContextCompat.checkSelfPermission(mContext, PermissionsConstants.POST_NOTIFICATION)
                == PackageManager.PERMISSION_GRANTED);
    }
}