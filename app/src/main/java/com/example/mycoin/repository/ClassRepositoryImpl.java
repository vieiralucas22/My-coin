package com.example.mycoin.repository;

import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class ClassRepositoryImpl implements ClassRepository {
    private static final String TAG = LogcatUtil.getTag(ClassRepositoryImpl.class);

    private FirebaseService mFirebaseService;

    @Inject
    public ClassRepositoryImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public void getAllClassesByModule(String module, LoadClassesCallback callback) {
        mFirebaseService.getClassesByModule(module, callback);
    }

    @Override
    public void updateClassState(int position, boolean checked) {
        mFirebaseService.updateClassState(position, checked);
    }
}
