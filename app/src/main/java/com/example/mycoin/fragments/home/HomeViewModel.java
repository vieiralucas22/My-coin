package com.example.mycoin.fragments.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.preferences.AppPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {
    private static final String TAG = HomeViewModel.class.getSimpleName();

    private final FirebaseAuth mAuth;
    private final FirebaseService mFirebaseService;
    private final AppPreferences mAppPreferences;
    private final ClassRepository mClassRepository;

    private MutableLiveData<Integer> mProgressIntroduction = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgressOrganizeHome = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgressActionTime = new MutableLiveData<>();
    private MutableLiveData<Integer> mProgressExtra = new MutableLiveData<>();

    @Inject
    public HomeViewModel(FirebaseAuth auth, FirebaseService firebaseService,
                         AppPreferences appPreferences, ClassRepository classRepository) {
        mAuth = auth;
        mFirebaseService = firebaseService;
        mAppPreferences = appPreferences;
        mClassRepository = classRepository;
    }

    public void setCurrentUser() {
        mFirebaseService.setUserByEmail();
    }

    public String getUserName() {
        return mAppPreferences.getCurrentUser().getName();
    }

    public void getModuleProgress() {
//        mClassRepository.getAllClassesByModule(Constants.INTRODUCTION, new LoadClassesCallback() {
//            @Override
//            public void onSuccess(List<ClassAdapter.ClassItem> list) {
//                int percentage = 0;
//                int progressStep = 100 / list.size();
//                for (ClassAdapter.ClassItem classItem : list) {
//                    if (classItem.isDone()) {
//                        percentage += progressStep;
//                    }
//                }
//                mProgressIntroduction.postValue(percentage);
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//            }
//        });

    }

    public LiveData<Integer> getProgressIntroduction() {
        return mProgressIntroduction;
    }

    public MutableLiveData<Integer> getProgressOrganizeHome() {
        return mProgressOrganizeHome;
    }

    public MutableLiveData<Integer> getProgressActionTime() {
        return mProgressActionTime;
    }

    public MutableLiveData<Integer> getProgressExtra() {
        return mProgressExtra;
    }

    public String getUserPoints() {
        return String.valueOf(mAppPreferences.getCurrentUser().getPoints()) + " points";
    }
}