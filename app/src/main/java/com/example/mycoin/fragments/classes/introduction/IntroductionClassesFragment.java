package com.example.mycoin.fragments.classes.introduction;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;

public class IntroductionClassesFragment extends Fragment {

    private IntroductionClassesViewModel mViewModel;

    public static IntroductionClassesFragment newInstance() {
        return new IntroductionClassesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_introduction_classes, container, false);
    }
}
