package com.example.mycoin.fragments.classes.videoclasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.R;
import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.callbacks.VideosCallback;
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
    private Context mContext;

    private final MutableLiveData<String> mServerResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLoadNextClasses = new MutableLiveData<>();
    private List<ClassAdapter.ClassItem> mNextClasses;

    @Inject
    public VideoPlayerViewModel(ClassRepository classRepository, Context context) {
        mClassRepository = classRepository;
        mContext = context;
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

    public void showNextClasses(String classTitle, String module) {
        mNextClasses = new ArrayList<>();
        mClassRepository.getAllClassesByModule(module, new LoadClassesCallback() {
            @Override
            public void onSuccess(List<ClassAdapter.ClassItem> list) {

                switch (module) {
                    case Constants.INTRODUCTION:
                        defineNextIntroductionClasses(classTitle, list);
                        break;
                    case Constants.ORGANIZE_HOME:
                        defineNextOrganizeHomeClasses(classTitle, list);
                        break;
                    case Constants.ACTION_TIME:
                        defineNextActionTimeClasses(classTitle, list);
                        break;
                    case Constants.EXTRA:
                        defineNextExtraClasses(classTitle, list);
                        break;
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void defineNextIntroductionClasses(String classTitle, List<ClassAdapter.ClassItem> list) {
        if (classTitle.equals(list.get(0).getTitle())) {
            mNextClasses.add(list.get(1));
            mNextClasses.add(list.get(2));
            mNextClasses.add(list.get(3));
        } else if (classTitle.equals(list.get(1).getTitle())) {
            mNextClasses.add(list.get(2));
            mNextClasses.add(list.get(3));
        } else if (classTitle.equals(list.get(2).getTitle())) {
            mNextClasses.add(list.get(3));
        }
        mLoadNextClasses.postValue(true);
    }

    private void defineNextOrganizeHomeClasses(String classTitle, List<ClassAdapter.ClassItem> list) {
        if (classTitle.equals(list.get(0).getTitle())) {
            mNextClasses.add(list.get(1));
            mNextClasses.add(list.get(2));
        } else if (classTitle.equals(list.get(1).getTitle())) {
            mNextClasses.add(list.get(2));
        }
        mLoadNextClasses.postValue(true);
    }

    private void defineNextActionTimeClasses(String classTitle, List<ClassAdapter.ClassItem> list) {
        if (classTitle.equals(list.get(0).getTitle())) {
            mNextClasses.add(list.get(1));
            mNextClasses.add(list.get(2));
        } else if (classTitle.equals(list.get(1).getTitle())) {
            mNextClasses.add(list.get(2));
        }
        mLoadNextClasses.postValue(true);
    }

    private void defineNextExtraClasses(String classTitle, List<ClassAdapter.ClassItem> list) {
        if (classTitle.equals(list.get(0).getTitle())) {
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

    public void initClass(String module, String classTitle) {
        mClassRepository.getAllVideosByModule(module, new VideosCallback() {
            @Override
            public void onSuccess(List<String> list) {
                if (classTitle.equals(mContext.getString(R.string.lesson_1))) {
                    mServerResponse.postValue(list.get(0));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_2))) {
                    mServerResponse.postValue(list.get(4));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_3))) {
                    mServerResponse.postValue(list.get(5));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_4))) {
                    mServerResponse.postValue(list.get(6));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_5))) {
                    mServerResponse.postValue(list.get(7));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_6))) {
                    mServerResponse.postValue(list.get(8));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_7))) {
                    mServerResponse.postValue(list.get(9));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_8))) {
                    mServerResponse.postValue(list.get(10));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_9))) {
                    mServerResponse.postValue(list.get(11));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_10))) {
                    mServerResponse.postValue(list.get(1));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_11))) {
                    mServerResponse.postValue(list.get(2));
                } else if (classTitle.equals(mContext.getString(R.string.lesson_12))) {
                    mServerResponse.postValue(list.get(3));
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

}