package com.example.mycoin.fragments.classes.allclasses;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.constants.Constants;
import com.example.mycoin.entities.Question;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.utils.LogcatUtil;

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
        String jsonClasses = loadJsonFromAsset("classes.json");

        try {
            JSONObject jsonObject = new JSONObject(jsonClasses);
            JSONArray classes = jsonObject.getJSONArray("modules");
            JSONObject module = classes.getJSONObject(0);
            JSONArray currentModule = module.getJSONArray(Constants.INTRODUCTION);

            for (int i = 0; i < currentModule.length(); i++) {
                JSONObject lesson = currentModule.getJSONObject(i);
                String title = lesson.getString("title");
                String description = lesson.getString("description");
                boolean isDone = lesson.getBoolean("isDone");
                mClassList.add(new ClassAdapter.ClassItem(title, description, isDone));
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private String loadJsonFromAsset(String jsonFile) {
        String json = "";

        try {
            InputStream inputStream = mContext.getAssets().open(jsonFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return json;
    }

    public void loadClasses() {
        mLoadData.postValue(true);
    }

    public LiveData<Boolean> getLoadData() {
        return mLoadData;
    }

    public List<ClassAdapter.ClassItem> getClassList() {
        return mClassList;
    }
}
