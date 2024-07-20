package com.example.mycoin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mycoin.InternalIntents;
import com.example.mycoin.callbacks.GoalCallback;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.entities.Goal;
import com.example.mycoin.gateway.repository.GoalRepository;
import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

public class GoalCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = LogcatUtil.getTag(GoalCompletedReceiver.class);

    @Inject
    GoalRepository goalRepository;

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
                showNotificationAccordingGoal(goal, context);
                goalRepository.updateGoalAsCompleted(goal.getDescription());
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void showNotificationAccordingGoal(Goal goal, Context context) {
        if (goal.isDone()) return;

        switch (goal.getDescription()) {
            case Constants.GOAL_INTRODUCTION_MODULE_COMPLETED: {
                Toast.makeText(context, "Introduction module is completed", Toast.LENGTH_SHORT).show();
                break;
            }

            case Constants.GOAL_ORGANIZE_MODULE_COMPLETED: {
                Toast.makeText(context, "Organize home module is completed", Toast.LENGTH_SHORT).show();
                break;
            }

            case Constants.GOAL_ACTION_MODULE_COMPLETED: {
                Toast.makeText(context, "Action time module is completed", Toast.LENGTH_SHORT).show();
                break;
            }

            case Constants.GOAL_EXTRA_MODULE_COMPLETED: {
                Toast.makeText(context, "Extra module is completed", Toast.LENGTH_SHORT).show();
                break;
            }

            case Constants.GOAL_FIRST_QUIZ: {
                Toast.makeText(context, "Good resul!!", Toast.LENGTH_SHORT).show();
                break;
            }

            case Constants.GOAL_FIRST_MATCH_QUIZ: {
                //TODO
                break;
            }
        }
    }
}
