package com.example.mycoin.fragments.classes.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentQuizBinding;
import com.example.mycoin.entities.Question;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(QuizFragment.class);

    private RadioButton mRadioA, mRadioB, mRadioC, mRadioD;
    private Button mButtonSubmit, mButtonBack;
    private ImageView mImageRight, mImageWrong;
    private TextView mTextNumberOfQuestions, mTimer, mTextQuestion;
    private ProgressBar mProgressBar;
    private List<Question> mQuestionItens;
    private int currentQuestion = 0;
    private int correct = 0;
    private int wrong = 0;

    private FragmentQuizBinding mBinding;

    private QuizViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentQuizBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();

        loadAllQuestions();
        Collections.shuffle(mQuestionItens);
        setQuestionsScreen();

        setListeners();
    }

    private void initComponents() {
        mRadioA = mBinding.answerOne;
        mRadioB = mBinding.answerTwo;
        mRadioC = mBinding.answerThree;
        mRadioD = mBinding.answerFour;
        mButtonSubmit = mBinding.buttonSubmit;
        mButtonBack = mBinding.buttonBack;
        mImageRight = mBinding.imageRight;
        mImageWrong = mBinding.imageWrong;
        mTextNumberOfQuestions = mBinding.currentAndTotalQuestions;
        mTimer = mBinding.timerTime;
        mProgressBar = mBinding.progressBarQuiz;
        mTextQuestion = mBinding.textQuestion;
    }

    private void loadAllQuestions() {
        mQuestionItens = new ArrayList<>();
        String jsonQuiz = loadJsonFromAsset("questions.json");

        try {
            JSONObject jsonObject = new JSONObject(jsonQuiz);
            JSONArray questions = jsonObject.getJSONArray("questions");

            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);
                String questionString = question.getString("question");
                String answer1 = question.getString("answer1");
                String answer2 = question.getString("answer2");
                String answer3 = question.getString("answer3");
                String answer4 = question.getString("answer4");
                String correct = question.getString("correctAnswer");

                mQuestionItens.add(new Question(questionString, answer1, answer2,
                        answer3, answer4, correct));
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private String loadJsonFromAsset(String jsonFile) {
        String json = "";

        try {
            InputStream inputStream = getContext().getAssets().open(jsonFile);
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

    private void setQuestionsScreen() {
        mTextQuestion.setText(mQuestionItens.get(currentQuestion).getQuestion());
        mRadioA.setText(mQuestionItens.get(currentQuestion).getAnswer1());
        mRadioB.setText(mQuestionItens.get(currentQuestion).getAnswer2());
        mRadioC.setText(mQuestionItens.get(currentQuestion).getAnswer3());
        mRadioD.setText(mQuestionItens.get(currentQuestion).getAnswer4());
    }

    private void setListeners() {
        mButtonSubmit.setOnClickListener(this);
    }

    private void showQuestions() {
        if (currentQuestion < mQuestionItens.size() - 1) {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                currentQuestion++;
                setQuestionsScreen();
                mImageWrong.setVisibility(View.INVISIBLE);
                mImageRight.setVisibility(View.INVISIBLE);
            }, 500);
        } else {
            //TODO mandar os corrects e wrongs por safe args
            Navigation.findNavController(getView())
                    .navigate(R.id.action_quizFragment_to_resultFragment);
        }
    }

    private void handleWithQuestionAnswer(View view) {
        if (checkIfQuestionIsCorrect(view)) {
            correct++;
            mImageRight.setVisibility(View.VISIBLE);
            return;
        }
        wrong++;
        mImageWrong.setVisibility(View.VISIBLE);
    }

    private boolean checkIfQuestionIsCorrect(View view) {
        int id = view.getId();

        if (id == R.id.answer_one) {
            return mQuestionItens.get(currentQuestion).getAnswer1()
                    .equals(mQuestionItens.get(currentQuestion).getIsRight());
        } else if (id == R.id.answer_two) {
            return mQuestionItens.get(currentQuestion).getAnswer2()
                    .equals(mQuestionItens.get(currentQuestion).getIsRight());
        } else if (id == R.id.answer_three) {
            return mQuestionItens.get(currentQuestion).getAnswer3()
                    .equals(mQuestionItens.get(currentQuestion).getIsRight());
        } else if (id == R.id.answer_four) {
            return mQuestionItens.get(currentQuestion).getAnswer4()
                    .equals(mQuestionItens.get(currentQuestion).getIsRight());
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_submit) {
            handleWithQuestionAnswer(v);
            showQuestions();
        }
    }
}
