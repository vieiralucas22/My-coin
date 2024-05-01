package com.example.mycoin.fragments;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.example.mycoin.databinding.FragmentLoginBinding;
import com.example.mycoin.di.ViewModelFactory;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;

import com.example.mycoin.utils.LogcatUtil;

import javax.inject.Inject;


public abstract class BaseFragment  extends Fragment {
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
