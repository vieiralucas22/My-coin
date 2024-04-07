package com.example.mycoin.fragments.confirmcode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.preferences.AppPreferences;

import javax.inject.Inject;

public class ConfirmCodeViewModel extends ViewModel {

    private final AppPreferences mAppPreferences;

    private MutableLiveData<Boolean> mNeedNavigate = new MutableLiveData<>();

    @Inject
    public ConfirmCodeViewModel(AppPreferences appPreferences) {
        mAppPreferences = appPreferences;
    }

    public void confirmCode(int confirmationCode) {
        if (confirmationCode == mAppPreferences.getConfirmationCode()) {
            mNeedNavigate.postValue(true);
            return;
        }
        mNeedNavigate.postValue(false);
    }

    public MutableLiveData<Boolean> getNeedNavigate() {
        return mNeedNavigate;
    }

}