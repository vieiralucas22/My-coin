package com.example.mycoin.fragments.classes.allclasses;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.callbacks.Callback;
import com.example.mycoin.callbacks.LoadClassesCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.Question;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ClassesViewModel extends ViewModel {
    private static final String TAG = LogcatUtil.getTag(ClassesViewModel.class);

    private final ClassRepository mClassRepository;
    private final Context mContext;
    private List<ClassAdapter.ClassItem> mClassList;

    private MutableLiveData<Boolean> mLoadData = new MutableLiveData<>();

    @Inject
    public ClassesViewModel(ClassRepository classRepository, Context context) {
        mClassRepository = classRepository;
        mContext = context;
    }

    public void loadClassesInBD() {
        mClassList = new ArrayList<>();
        mClassRepository.getAllClassesByModule(Constants.INTRODUCTION, new LoadClassesCallback() {

            @Override
            public void onSuccess(List<ClassAdapter.ClassItem> list) {
                mClassList = list;
                mLoadData.postValue(true);            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    public LiveData<Boolean> getLoadData() {
        return mLoadData;
    }

    public List<ClassAdapter.ClassItem> getClassList() {
        return mClassList;
    }
}
