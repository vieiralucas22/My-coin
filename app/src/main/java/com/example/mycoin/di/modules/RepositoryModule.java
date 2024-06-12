package com.example.mycoin.di.modules;

import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.repository.UserRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface RepositoryModule {
    @Binds
    UserRepository bindsUserRepository(UserRepositoryImpl impl);
}
