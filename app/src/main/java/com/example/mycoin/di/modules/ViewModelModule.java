package com.example.mycoin.di.modules;

import androidx.lifecycle.ViewModel;

import com.example.core.gateway.services.FirebaseService;
import com.example.core.usecases.interfaces.Login;
import com.example.mycoin.di.ViewModelKey;
import com.example.mycoin.fragments.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    ViewModel bindsLoginViewModel(LoginViewModel loginViewModel);

}
