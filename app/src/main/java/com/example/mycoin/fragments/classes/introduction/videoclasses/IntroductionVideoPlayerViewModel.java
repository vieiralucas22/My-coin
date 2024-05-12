package com.example.mycoin.fragments.classes.introduction.videoclasses;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.utils.LogcatUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.inject.Inject;

public class IntroductionVideoPlayerViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(IntroductionVideoPlayerViewModel.class);

    private final MutableLiveData<String> mServerResponse =  new MutableLiveData<>();

    @Inject
    public IntroductionVideoPlayerViewModel() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void sendClassForServer() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("192.168.86.5", 4000);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(1);

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = in.readLine();
                Log.d(TAG,"Response from server: " + response);

                mServerResponse.postValue(response);

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public MutableLiveData<String> getServerResponse() {
        return mServerResponse;
    }
}