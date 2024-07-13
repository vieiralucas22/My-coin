package com.example.mycoin.fragments.classes.videoclasses;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.fragments.classes.allclasses.ClassAdapter;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.utils.LogcatUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VideoPlayerViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(VideoPlayerViewModel.class);

    private ClassRepository mClassRepository;

    private final MutableLiveData<String> mServerResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoadNextClasses = new MutableLiveData<>();
    private List<ClassAdapter.ClassItem> mNextClasses;

    @Inject
    public VideoPlayerViewModel(ClassRepository classRepository) {
        mClassRepository = classRepository;
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
                Log.d(TAG, "Response from server: " + response);

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

    public void showNextClasses(int classPosition, String module) {
        mNextClasses = new ArrayList<>();
        mClassRepository.getAllClassesByModule(module, new LoadClassesCallback() {
            @Override
            public void onSuccess(List<ClassAdapter.ClassItem> list) {

                switch (module) {
                    case Constants.INTRODUCTION:
                        defineNextIntroductionClasses(classPosition, list);
                        break;
                    case Constants.ORGANIZE_HOME:
                        defineNextOrganizeHomeClasses(classPosition, list);
                        break;
                    case Constants.ACTION_TIME:
                        defineNextActionTimeClasses(classPosition, list);
                        break;
                    case Constants.EXTRA:
                        defineNextExtraClasses(classPosition, list);
                        break;
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void defineNextIntroductionClasses(int classPosition, List<ClassAdapter.ClassItem> list) {
        if (classPosition == 0) {
            mNextClasses.add(list.get(1));
            mNextClasses.add(list.get(2));
            mNextClasses.add(list.get(3));
        } else if (classPosition == 1) {
            mNextClasses.add(list.get(2));
            mNextClasses.add(list.get(3));
            mNextClasses.add(list.get(4));
        } else if (classPosition == 2) {
            mNextClasses.add(list.get(3));
            mNextClasses.add(list.get(4));
        } else if (classPosition == 3) {
            mNextClasses.add(list.get(4));
        }
        mLoadNextClasses.postValue(true);
    }

    private void defineNextOrganizeHomeClasses(int classPosition, List<ClassAdapter.ClassItem> list) {
        if (classPosition == 0) {
            mNextClasses.add(list.get(1));
            mNextClasses.add(list.get(2));
            mNextClasses.add(list.get(3));
        } else if (classPosition == 1) {
            mNextClasses.add(list.get(2));
            mNextClasses.add(list.get(3));
        } else if (classPosition == 2) {
            mNextClasses.add(list.get(3));
        }
        mLoadNextClasses.postValue(true);
    }

    private void defineNextActionTimeClasses(int classPosition, List<ClassAdapter.ClassItem> list) {
        if (classPosition == 0) {
            mNextClasses.add(list.get(1));
            mNextClasses.add(list.get(2));
        } else if (classPosition == 1) {
            mNextClasses.add(list.get(2));
        }
        mLoadNextClasses.postValue(true);
    }

    private void defineNextExtraClasses(int classPosition, List<ClassAdapter.ClassItem> list) {
        if (classPosition == 0) {
            mNextClasses.add(list.get(1));
        }
        mLoadNextClasses.postValue(true);
    }

    public List<ClassAdapter.ClassItem> getNextClasses() {
        return mNextClasses;
    }

    public LiveData<Boolean> getLoadNextClasses() {
        return mLoadNextClasses;
    }
}