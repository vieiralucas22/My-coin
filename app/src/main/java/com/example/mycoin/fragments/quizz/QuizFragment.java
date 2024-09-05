package com.example.mycoin.fragments.quizz;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.mycoin.GameStatus;
import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.constants.TimeConstants;
import com.example.mycoin.databinding.FragmentQuizBinding;
import com.example.mycoin.entities.Question;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;
import com.example.mycoin.utils.MessageUtil;

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
    private final int POINTS_PER_QUESTION = 10;

    private RadioButton mRadioA, mRadioB, mRadioC, mRadioD;
    private RadioGroup mRadioGroup;
    private Button mButtonSubmit, mButtonBack;
    private ImageView mImageRight, mImageWrong;
    private TextView mTextNumberOfQuestions, mTimer, mTextQuestion;
    private ProgressBar mProgressBar;
    private List<Question> mQuestionItems;
    private FragmentQuizBinding mBinding;
    private CountDownTimer mCountDownTimer;
    private QuizViewModel mViewModel;

    private int mCurrentQuestion;
    private int mRoomCode = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentQuizBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(QuizViewModel.class);

        mCurrentQuestion = 0;
        initComponents();
        setListeners();
        initObservers();
        mViewModel.initFirebaseStoreObserve();
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
        mRoomCode = getArgs().getRoomCode();
        mViewModel.setIsOnlineMatch(mRoomCode);
        setUpUI();
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

                if (mViewModel.isOnlineMatch()) {
                    mViewModel.handleLastQuestionAnswer(getArgs().getOwnerRoom());
                    goResultScreen();
                } else {
                    goResultScreen();
                }
            }
        }.start();
    }

    @SuppressLint("SimpleDateFormat")
    private String formatTime(long millisUntilFinished) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(millisUntilFinished);
    }


    private void loadAllQuestions() {
        mQuestionItems = new ArrayList<>();
        String jsonQuiz = loadJsonFromAsset(getQuestionFile());

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
        mBinding.buttonStartGame.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
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

        if (mViewModel.isOnlineMatch()) {
            mViewModel.handleLastQuestionAnswer(getArgs().getOwnerRoom());
            goResultScreen();
        } else {
            goResultScreen();
        }
    }

    private void handleWithQuestionAnswer(View view) {
        if (checkIfQuestionIsCorrect(view)) {
            mViewModel.incrementCorrectQuestions(getArgs().getOwnerRoom());
            mImageRight.setVisibility(View.VISIBLE);
            mViewModel.saveUserPoints(POINTS_PER_QUESTION);
            return;
        }
        mViewModel.incrementWrongQuestions();
        mImageWrong.setVisibility(View.VISIBLE);
    }

    private boolean checkIfQuestionIsCorrect(View view) {
        if (view == null) return false;

        if (mRadioA.isChecked()) {
            char firstCharacter = mQuestionItems.get(mCurrentQuestion).getAnswer1().charAt(0);
            char responseCharacter = mQuestionItems.get(mCurrentQuestion).getIsRight().charAt(0);

            return firstCharacter == responseCharacter;
        } else if (mRadioB.isChecked()) {
            char firstCharacter = mQuestionItems.get(mCurrentQuestion).getAnswer2().charAt(0);
            char responseCharacter = mQuestionItems.get(mCurrentQuestion).getIsRight().charAt(0);

            return firstCharacter == responseCharacter;
        } else if (mRadioC.isChecked()) {
            char firstCharacter = mQuestionItems.get(mCurrentQuestion).getAnswer3().charAt(0);
            char responseCharacter = mQuestionItems.get(mCurrentQuestion).getIsRight().charAt(0);

            return firstCharacter == responseCharacter;
        } else if (mRadioD.isChecked()) {
            char firstCharacter = mQuestionItems.get(mCurrentQuestion).getAnswer4().charAt(0);
            char responseCharacter = mQuestionItems.get(mCurrentQuestion).getIsRight().charAt(0);

            return firstCharacter == responseCharacter;
        }
        return false;
    }

    private boolean hasMoreQuestions() {
        return mCurrentQuestion < mQuestionItems.size() - 1;
    }

    private void goResultScreen() {
        if (getView() == null) return;

        int totalPointsEarned = (POINTS_PER_QUESTION * mViewModel.getCorrectQuestions());

        NavDirections action = QuizFragmentDirections.actionQuizFragmentToResultFragment()
                .setTotalRightQuestions(mViewModel.getCorrectQuestions())
                .setTotalWrongQuestions(mViewModel.getWrongQuestions())
                .setTotalPoints(totalPointsEarned)
                .setRoomCode(mRoomCode);

        Navigation.findNavController(getView()).navigate(action);
    }

    @SuppressLint("SetTextI18n")
    private void setUpUI() {
        if (!mViewModel.isOnlineMatch()) {
            mViewModel.startGame();
        } else {
            setUpWaitingPlayerUI();
        }
    }

    private QuizFragmentArgs getArgs() {
        if (getArguments() == null) return null;

        return QuizFragmentArgs.fromBundle(getArguments());
    }

    private void initObservers() {
        mViewModel.getIsOwnerRoom().observe(getViewLifecycleOwner(), isOwner -> {
            mBinding.buttonStartGame.setBackground(getDrawable(mViewModel.hasMinimumPlayersInRoom() ?
                    R.drawable.background_button_submit : R.drawable.background_button_disabled));
            if (isOwner) {
                mBinding.textWaiting.setText(getContext().getString(mViewModel.hasMinimumPlayersInRoom()
                        ? R.string.player_arrived : R.string.waiting_another_player));
                mBinding.buttonStartGame.setVisibility(View.VISIBLE);
            }

        });

        mViewModel.getGameStatus().observe(getViewLifecycleOwner(), gameStatus -> {
            if (gameStatus.equals(GameStatus.STARTED)) {
                setUpGameStartedUI();
                initQuiz();
                mViewModel.defineGameStatusAsRunning();
            }
        });
    }

    private void initQuiz() {
        initCountdownTimer();
        loadAllQuestions();
        setQuestionsScreen();
    }

    private void setUpGameStartedUI() {
        mBinding.currentAndTotalQuestions.setVisibility(View.VISIBLE);
        mBinding.cardTimer.setVisibility(View.VISIBLE);
        mBinding.progressBarQuiz.setVisibility(View.VISIBLE);
        mBinding.textQuestion.setVisibility(View.VISIBLE);
        mBinding.cardAnswers.setVisibility(View.VISIBLE);
        mBinding.logo.setVisibility(View.INVISIBLE);
        mBinding.appTitle.setVisibility(View.INVISIBLE);
        mBinding.textRoomCode.setVisibility(View.INVISIBLE);
        mBinding.textWaiting.setVisibility(View.INVISIBLE);
        mBinding.buttonStartGame.setVisibility(View.INVISIBLE);
        mBinding.logoHuge.setVisibility(View.INVISIBLE);
        mTextNumberOfQuestions.setText((mCurrentQuestion + 1) + "/4");
    }

    private void setUpWaitingPlayerUI() {
        mBinding.currentAndTotalQuestions.setVisibility(View.INVISIBLE);
        mBinding.cardTimer.setVisibility(View.INVISIBLE);
        mBinding.progressBarQuiz.setVisibility(View.INVISIBLE);
        mBinding.textQuestion.setVisibility(View.INVISIBLE);
        mBinding.cardAnswers.setVisibility(View.INVISIBLE);
        mBinding.logo.setVisibility(View.VISIBLE);
        mBinding.appTitle.setVisibility(View.VISIBLE);
        mBinding.textRoomCode.setVisibility(View.VISIBLE);
        mBinding.textWaiting.setVisibility(View.VISIBLE);
        mBinding.buttonStartGame.setVisibility(getArgs().getOwnerRoom()? View.VISIBLE : View.INVISIBLE);
        mBinding.textWaiting.setText(getArgs().getOwnerRoom()
                ? getContext().getString(R.string.waiting_another_player)
                : getContext().getString(R.string.waiting_owner_start_game));
        mBinding.logoHuge.setVisibility(View.VISIBLE);
        mBinding.textRoomCode.setText(getContext().getString(
                R.string.the_code_room_is, getArgs().getRoomCode()));
        mViewModel.setRoomCode(getArgs().getRoomCode());
    }

    private String getQuestionFile() {
        if (!mViewModel.isOnlineMatch()) return mViewModel.getQuestions();
        return mViewModel.getQuestionsByTheme();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        mViewModel.handleExitRoom();
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
        } else if (v.getId() == R.id.button_start_game) {
            if (mViewModel.hasMinimumPlayersInRoom()) {
                mViewModel.startGame();
            } else {
                Toast.makeText(getContext(), "Wait another player to start game!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
