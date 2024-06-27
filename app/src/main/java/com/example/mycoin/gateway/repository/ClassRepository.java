package com.example.mycoin.gateway.repository;

import com.example.mycoin.callbacks.LoadClassesCallback;

public interface ClassRepository {
    void getAllClassesByModule(String introduction, LoadClassesCallback callback);
    void updateClassState(int position, boolean checked);
}
