package com.example.mycoin.fragments.signup;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.RegisterCallback;
import com.example.mycoin.entities.User;
import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.utils.MessageUtil;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = SignUpViewModel.class.getSimpleName();

    private final Register mRegister;
    private final Context mContext;

    private final MutableLiveData<Boolean> mSignUpSuccessful = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHandleResponseLayout = new MutableLiveData<>();

    @Inject
    public SignUpViewModel(Register register, Context context) {
        mRegister = register;
        mContext = context;
    }

    protected void createAccount(String email, String password, String date, String name) {
        User user = new User(name, email, date, password);
        mRegister.signUp(user, new RegisterCallback() {
            @Override
            public void onSuccess() {
                mSignUpSuccessful.postValue(true);
            }

            @Override
            public void onFailure(int messageError) {
                MessageUtil.showToast(mContext, messageError);
                mSignUpSuccessful.postValue(false);
                mHandleResponseLayout.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getSignUpSuccessful() {
        return mSignUpSuccessful;
    }

    public MutableLiveData<Boolean> getHandleResponseLayout() {
        return mHandleResponseLayout;
    }

    public void setUpUIToWaitResponse() {
        mHandleResponseLayout.setValue(true);
    }

}
