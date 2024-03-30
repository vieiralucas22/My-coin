package com.example.mycoin.fragments;

import android.view.View;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mycoin.fragments.login.LoginViewModel;

public class BaseFragment extends Fragment {

    protected void backScreen(View v) {
        Navigation.findNavController(v).popBackStack();
    }

}
