package com.example.mycoin.fragments;

import android.view.View;

import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;

public class BaseFragment extends Fragment {

    protected void backScreen(View v) {
        Navigation.findNavController(v).popBackStack();
    }

}
