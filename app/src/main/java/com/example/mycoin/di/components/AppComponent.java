package com.example.mycoin.di.components;

import com.example.mycoin.di.ViewModelFactory;
import com.example.mycoin.di.modules.FirebaseModule;
import com.example.mycoin.di.modules.ServicesModule;
import com.example.mycoin.di.modules.UseCaseModule;
import com.example.mycoin.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
@Component(
        modules = {
                ViewModelModule.class, ServicesModule.class, UseCaseModule.class,
                FirebaseModule.class
        }
)
@Singleton
public interface AppComponent {
    ViewModelFactory getViewModelFactory();
}
