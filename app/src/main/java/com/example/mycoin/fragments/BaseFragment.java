package com.example.mycoin.fragments;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mycoin.di.ViewModelFactory;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;

import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;

import dagger.internal.DaggerGenerated;

public class BaseFragment extends Fragment {
    public static final String TAG = LogcatUtil.getTag(BaseFragment.class);
    @Inject
    ViewModelFactory viewModelFactory;

    protected void backScreen(View v) {
        Navigation.findNavController(v).popBackStack();
    }

    protected <T extends ViewModel> T getViewModel(Class<T> clazz) {
        ViewModelFactory factory = DaggerAppComponent.builder()
                .applicationModule(new AppModule(getContext()))
                .build()
                .getViewModelFactory();
        ViewModelProvider provider = new ViewModelProvider(this, factory);
        return provider.get(clazz);
    }

    protected void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        requireActivity().registerReceiver(receiver, filter);
    }
}
