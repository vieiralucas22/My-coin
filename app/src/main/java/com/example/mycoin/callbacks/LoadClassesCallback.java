package com.example.mycoin.callbacks;

import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;

import java.util.List;

public interface LoadClassesCallback {
    void onSuccess(List<ClassAdapter.ClassItem> list);
    void onFailure(String message);
}
