package com.example.mycoin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mycoin.InternalIntents;
import com.example.mycoin.NotificationHelper;
import com.example.mycoin.R;
import com.example.mycoin.callbacks.GoalCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.entities.Goal;
import com.example.mycoin.entities.User;
import com.example.mycoin.gateway.repository.GoalRepository;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class GoalCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = LogcatUtil.getTag(GoalCompletedReceiver.class);

    @Inject
    GoalRepository goalRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    AppPreferences appPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {

        DaggerAppComponent.builder()
                .applicationModule(new AppModule(context))
                .build()
                .inject(this);

        String action = intent.getAction();

        if (action != null && action.equals(InternalIntents.ACTION_GOAL_COMPLETED)) {
            String goal = intent.getStringExtra(Constants.GOAL_DONE);

            handleGoalCompleted(goal, context);
        }
    }

    private void handleGoalCompleted(String goal, Context context) {

        goalRepository.getGoal(goal, new GoalCallback() {
            @Override
            public void onSuccess(Goal goal) {
                if (goal.isDone()) return;

                showNotificationAccordingGoal(goal, context);
                goalRepository.updateGoalAsCompleted(goal.getDescription());
                givePointsToUser(goal.getPoints());
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void showNotificationAccordingGoal(Goal goal, Context context) {
        switch (goal.getDescription()) {
            case Constants.GOAL_INTRODUCTION_MODULE_COMPLETED: {
                NotificationHelper.createNotification(context,
                        context.getString(R.string.goal_notification_title), context.getString(R.string.goal_two));
                break;
            }

            case Constants.GOAL_ORGANIZE_MODULE_COMPLETED: {
                NotificationHelper.createNotification(context,
                        context.getString(R.string.goal_notification_title), context.getString(R.string.goal_three));
                break;
            }

            case Constants.GOAL_ACTION_MODULE_COMPLETED: {
                NotificationHelper.createNotification(context,
                        context.getString(R.string.goal_notification_title), context.getString(R.string.goal_four));
                break;
            }

            case Constants.GOAL_EXTRA_MODULE_COMPLETED: {
                NotificationHelper.createNotification(context,
                        context.getString(R.string.goal_notification_title), context.getString(R.string.goal_five));
                break;
            }

            case Constants.GOAL_FIRST_QUIZ: {
                NotificationHelper.createNotification(context,
                        context.getString(R.string.goal_notification_title), context.getString(R.string.goal_one));
                break;
            }

            case Constants.GOAL_FIRST_MATCH_QUIZ: {
                NotificationHelper.createNotification(context,
                        context.getString(R.string.goal_notification_title), context.getString(R.string.goal_six));                break;
            }
        }
    }

    private void givePointsToUser(int points) {
        User user = appPreferences.getCurrentUser();
        int newUserPoints = user.getPoints() + points;
        user.setPoints(newUserPoints);
        userRepository.updateCurrentUser(user);
    }
}
