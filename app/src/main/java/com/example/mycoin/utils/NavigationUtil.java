package com.example.mycoin.utils;

import android.text.TextUtils;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;

public class NavigationUtil {
    public static final String TAG = LogcatUtil.getTag(NavigationUtil.class);

    public static void navigate(View v, String origin, String destination) {
        if (TextUtils.isEmpty(origin) || TextUtils.isEmpty(destination)) return;

        switch (origin) {
            case Constants.LOGIN_FRAGMENT:
                goLoginDestination(v, destination);
                break;
            case Constants.SIGN_UP_FRAGMENT:
                break;
            case Constants.CONFIRMATION_CODE_FRAGMENT:
                goConfirmCodeDestination(v);
                break;
            case Constants.FORGOT_PASSWORD_FRAGMENT:
                goConfirmCodeScreen(v);
                break;
            case Constants.HOME_FRAGMENT:
                break;
            case Constants.CHANGE_PASSWORD_FRAGMENT:
                break;
            case Constants.EDIT_USER_FRAGMENT:
                break;
            case Constants.GENERAL_PROFILE_FRAGMENT:
                break;
        }
    }

    private static void goLoginDestination(View v, String destination) {
        switch (destination) {
            case Constants.HOME_FRAGMENT:
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment);
                break;
            case Constants.FORGOT_PASSWORD_FRAGMENT:
                Navigation.findNavController(v)
                        .navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
                break;
            case Constants.SIGN_UP_FRAGMENT:
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment);
                break;
        }
    }

    private static void goConfirmCodeScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_forgotPasswordFragment_to_confirmCodeFragment);
    }

    private static void goConfirmCodeDestination(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_confirmCodeFragment_to_loginFragment);
    }
}
