package com.example.mycoin.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    protected void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && getContext() != null)  {
            getContext().registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
        }
    }

    protected void unregisterReceiver(BroadcastReceiver receiver) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && getContext() != null)  {
            getContext().unregisterReceiver(receiver);
        }
    }

    protected void sendBroadcast(Intent intent) {
        if (getContext() == null) return;
        getContext().sendBroadcast(intent);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    protected Drawable getDrawable(int drawableId) {
        return requireContext().getDrawable(drawableId);
    }
}
