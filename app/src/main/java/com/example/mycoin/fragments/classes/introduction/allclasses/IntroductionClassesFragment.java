package com.example.mycoin.fragments.classes.introduction.allclasses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentIntroductionClassesBinding;

public class IntroductionClassesFragment extends Fragment implements View.OnClickListener {

    private TextView mText;
    private FragmentIntroductionClassesBinding mBinding;

    private IntroductionClassesViewModel mViewModel;

    public static IntroductionClassesFragment newInstance() {
        return new IntroductionClassesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_introduction_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = view.findViewById(R.id.text_title);
        mText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_title) {
            Navigation.findNavController(v).navigate(R.id.action_introductionClassesFragment_to_introductionVideoPlayerFragment);
        }
    }
}
