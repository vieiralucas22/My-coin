package com.example.mycoin.di.modules;

import com.example.mycoin.gateway.services.EmailService;
import com.example.mycoin.gateway.services.FirebaseService;
import com.example.mycoin.services.EmailServiceImpl;
import com.example.mycoin.services.FirebaseServiceImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface ServicesModule {
    @Binds
    FirebaseService bindsFirebaseService(FirebaseServiceImpl impl);

    @Binds
    EmailService bindsEmailService(EmailServiceImpl impl);
}
