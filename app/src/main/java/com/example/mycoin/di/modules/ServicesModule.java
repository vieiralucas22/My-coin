package com.example.mycoin.di.modules;

import com.example.core.gateway.services.FirebaseService;
import com.example.mycoin.services.FirebaseServiceImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface ServicesModule {
    @Binds
    FirebaseService bindsFirebaseService(FirebaseServiceImpl impl);
}
