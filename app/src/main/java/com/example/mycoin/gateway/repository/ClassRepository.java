package com.example.mycoin.gateway.repository;

import com.example.mycoin.callbacks.Callback;
import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;

import java.util.List;

public interface ClassRepository {
    void getAllClassesByModule(String introduction, LoadClassesCallback callback);
}
