package com.example.mycoin.di.modules;

import com.example.core.usecases.impl.LoginImpl;
import com.example.core.usecases.interfaces.Login;

import dagger.Binds;
import dagger.Module;

@Module
public interface UseCaseModule {
    @Binds
    Login bindsLogin(LoginImpl impl);
}
