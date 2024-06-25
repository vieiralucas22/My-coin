package com.example.mycoin.repository;

import com.example.mycoin.callbacks.Callback;
import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.gateway.services.FirebaseService;

import java.util.List;

import javax.inject.Inject;

public class ClassRepositoryImpl implements ClassRepository {

    private FirebaseService mFirebaseService;

    @Inject
    public ClassRepositoryImpl(FirebaseService firebaseService) {
        mFirebaseService = firebaseService;
    }

    @Override
    public void getAllClassesByModule(String module, LoadClassesCallback callback) {
        mFirebaseService.getClassesByModule(module, callback);
    }
}
