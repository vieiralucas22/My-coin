package com.example.mycoin.fragments.profile.changeuserpassword;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;

public class ChangeUserPasswordFragment extends Fragment {

    private ChangeUserPasswordViewModel mViewModel;

    public static ChangeUserPasswordFragment newInstance() {
        return new ChangeUserPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_user_password, container, false);
    }

}