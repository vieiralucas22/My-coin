package com.example.mycoin.di.modules;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.di.ViewModelKey;
import com.example.mycoin.fragments.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    ViewModel bindsLoginViewModel(LoginViewModel loginViewModel);

}
