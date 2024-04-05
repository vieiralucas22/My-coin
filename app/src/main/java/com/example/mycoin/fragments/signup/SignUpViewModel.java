package com.example.mycoin.fragments.signup;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final Register mRegister;
    private final Context mContext;

    private MutableLiveData<Boolean> mSignUpSuccessful = new MutableLiveData<>();

    @Inject
    public SignUpViewModel(Register register, Context context) {
        mRegister = register;
        mContext = context;
    }

    protected void createAccount(String email, String password, String date, String username) {
        mRegister.signUp(email, password, new RegisterCallback() {
            @Override
            public void onSuccess() {
                mSignUpSuccessful.postValue(true);
            }

            @Override
            public void onFailure() {
                MessageUtil.showToast(mContext, R.string.register_fail);
                mSignUpSuccessful.postValue(false);
            }
        });
    }

}
