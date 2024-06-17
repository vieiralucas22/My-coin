package com.example.mycoin.callbacks;

import java.util.List;

public interface LoadClassesCallback {
    void onSuccess(List<Object> list);
    void onFailure(String message);
}
