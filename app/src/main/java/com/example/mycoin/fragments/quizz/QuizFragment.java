package com.example.mycoin.fragments.quizz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.constants.TimeConstants;
import com.example.mycoin.databinding.FragmentQuizBinding;
import com.example.mycoin.entities.Question;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(QuizFragment.class);

    private final long QUESTION_TIME = TimeConstants.ONE_MINUTE;
    private final long POINTS_PER_QUESTION = 10;

    private RadioButton mRadioA, mRadioB, mRadioC, mRadioD;
    private RadioGroup mRadioGroup;
    private Button mButtonSubmit, mButtonBack;
    private ImageView mImageRight, mImageWrong;
    private TextView mTextNumberOfQuestions, mTimer, mTextQuestion;
    private ProgressBar mProgressBar;
    private List<Question> mQuestionItems;
    private FragmentQuizBinding mBinding;
    private CountDownTimer mCountDownTimer;
    private int mCurrentQuestion;
    private int correct = 0;
    private int wrong = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentQuizBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCurrentQuestion = 0;
        initComponents();
        initCountdownTimer();
        loadAllQuestions();
        Collections.shuffle(mQuestionItems);
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
        mRadioGroup = mBinding.radioGroup;
        mTextNumberOfQuestions.setText((mCurrentQuestion + 1) + "/4");
    }

    private void initCountdownTimer() {
        mCountDownTimer = new CountDownTimer(QUESTION_TIME, TimeConstants.ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                int timeUntilFinish = (int) ((QUESTION_TIME - (QUESTION_TIME - millisUntilFinished)) / 1000);
                int progress = (int) ((Constants.TOTAL_PROGRESS_BAR - (timeUntilFinish * 1.12)));

                mProgressBar.setProgress(progress);
                mTimer.setText(formatTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                if (hasMoreQuestions()) {
                    mCountDownTimer.start();
                    mRadioGroup.clearCheck();
                    handleWithQuestionAnswer(getView());
                    showQuestions();
                    return;
                }
                goResultScreen();

            }
        }.start();
    }

    private String formatTime(long millisUntilFinished) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(millisUntilFinished);
    }


    private void loadAllQuestions() {
        mQuestionItems = new ArrayList<>();
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

                mQuestionItems.add(new Question(questionString, answer1, answer2,
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
        mTextQuestion.setText(mQuestionItems.get(mCurrentQuestion).getQuestion());
        mRadioA.setText(mQuestionItems.get(mCurrentQuestion).getAnswer1());
        mRadioB.setText(mQuestionItems.get(mCurrentQuestion).getAnswer2());
        mRadioC.setText(mQuestionItems.get(mCurrentQuestion).getAnswer3());
        mRadioD.setText(mQuestionItems.get(mCurrentQuestion).getAnswer4());
    }

    private void setListeners() {
        mButtonSubmit.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    private void showQuestions() {
        if (hasMoreQuestions()) {
            mCurrentQuestion++;
            mTextNumberOfQuestions.setText((mCurrentQuestion + 1) + "/4");
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                setQuestionsScreen();
                mImageWrong.setVisibility(View.INVISIBLE);
                mImageRight.setVisibility(View.INVISIBLE);
            }, 500);
            return;
        }
        goResultScreen();
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
        if (view == null) return false;

        if (mRadioA.isChecked()) {
            return mQuestionItems.get(mCurrentQuestion).getAnswer1()
                    .equals(mQuestionItems.get(mCurrentQuestion).getIsRight());
        } else if (mRadioB.isChecked()) {
            return mQuestionItems.get(mCurrentQuestion).getAnswer2()
                    .equals(mQuestionItems.get(mCurrentQuestion).getIsRight());
        } else if (mRadioC.isChecked()) {
            return mQuestionItems.get(mCurrentQuestion).getAnswer3()
                    .equals(mQuestionItems.get(mCurrentQuestion).getIsRight());
        } else if (mRadioD.isChecked()) {
            return mQuestionItems.get(mCurrentQuestion).getAnswer4()
                    .equals(mQuestionItems.get(mCurrentQuestion).getIsRight());
        }
        return false;
    }

    private boolean hasMoreQuestions() {
        return mCurrentQuestion < mQuestionItems.size() - 1;
    }

    private void goResultScreen() {
        int totalPointsEarned = (int) (POINTS_PER_QUESTION * correct);

        NavDirections action = QuizFragmentDirections.actionQuizFragmentToResultFragment()
                .setTotalRightQuestions(correct)
                .setTotalWrongQuestions(wrong)
                .setTotalPoints(totalPointsEarned);

        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
        mCountDownTimer.cancel();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_submit) {
            handleWithQuestionAnswer(v);
            showQuestions();
            mRadioGroup.clearCheck();
            mCountDownTimer.start();
        } else if (v.getId() == R.id.button_back) {
            backScreen(v);
        }
    }
}
