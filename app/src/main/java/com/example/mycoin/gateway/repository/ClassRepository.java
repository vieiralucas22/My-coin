package com.example.mycoin.gateway.repository;

import com.example.mycoin.callbacks.LoadClassesCallback;

public interface ClassRepository {
    void getAllClassesByModule(String module, LoadClassesCallback callback);
    void updateClassState(int position, boolean checked, String module);
}
