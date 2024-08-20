package com.example.mycoin.fragments.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycoin.utils.LogcatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(ChatViewModel.class);

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public OkHttpClient client = new OkHttpClient();

    public MutableLiveData<String> mLoadMessage = new MutableLiveData<>();

    @Inject
    public ChatViewModel() {
    }

    public LiveData<String> getLoadMessage() {
        return mLoadMessage;
    }

    public void handleWithQuestion(String question) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");

            // Criando o array de mensagens
            JSONArray messagesArray = new JSONArray();
            messagesArray.put(new JSONObject().put("role", "user").put("content", question));

            jsonBody.put("messages", messagesArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mLoadMessage.postValue("Failed to load response, please try again in few seconds" +
                        " ;). \n Possible reason2: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray choices = jsonObject.getJSONArray("choices");
                        JSONObject choice = choices.getJSONObject(0);
                        String result = choice.getJSONObject("message").getString("content");
                        mLoadMessage.postValue(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    int statusCode = response.code();  // Obtém o código de status

                    Log.d(TAG, response.body().string());
                }
            }
        });

    }
}