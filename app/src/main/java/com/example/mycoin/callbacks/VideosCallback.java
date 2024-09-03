package com.example.mycoin.callbacks;

import java.util.List;

public interface VideosCallback {
    void onSuccess(List<String> list);
    void onFailure(String message);
}
