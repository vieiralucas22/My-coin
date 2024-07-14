package com.example.mycoin.di.modules;

import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.gateway.repository.GoalRepository;
import com.example.mycoin.gateway.repository.UserRepository;
import com.example.mycoin.repository.ClassRepositoryImpl;
import com.example.mycoin.repository.GoalRepositoryImpl;
import com.example.mycoin.repository.UserRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public interface RepositoryModule {
    @Binds
    UserRepository bindsUserRepository(UserRepositoryImpl impl);

    @Binds
    ClassRepository bindsClassRepository(ClassRepositoryImpl impl);

    @Binds
    GoalRepository bindsGoalRepository(GoalRepositoryImpl impl);
}
