package com.example.mycoin.fragments.confirmcode;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.fragments.login.LoginFragment;

public class ConfirmCodeFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = LoginFragment.class.getSimpleName();

    private Button mButtonConfirm, mButtonBack;
    private ConfirmCodeViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_code, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ConfirmCodeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "Enter in confirm code fragment");

        initComponents(view);
    }

    private void initComponents(View view) {
        mButtonConfirm = view.findViewById(R.id.button_confirm);
        mButtonBack = view.findViewById(R.id.button_back);

        initListeners();
    }

    private void initListeners() {
        mButtonConfirm.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
    }

    private void goChangePasswordScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_confirmCodeFragment_to_changePasswordFragment);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_confirm) {
            goChangePasswordScreen(v);
        } else if (id == R.id.button_back) {
            backScreen(v);
        }
    }

}