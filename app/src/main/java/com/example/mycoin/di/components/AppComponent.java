package com.example.mycoin.di.components;

import com.example.mycoin.di.ViewModelFactory;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.di.modules.FirebaseModule;
import com.example.mycoin.di.modules.RepositoryModule;
import com.example.mycoin.di.modules.ServicesModule;
import com.example.mycoin.di.modules.UseCaseModule;
import com.example.mycoin.di.modules.ViewModelModule;
import com.example.mycoin.fragments.classes.allclasses.ClassesFragment;
import com.example.mycoin.fragments.classes.videoclasses.VideoPlayerFragment;
import com.example.mycoin.receivers.GoalCompletedReceiver;
import com.example.mycoin.receivers.LocaleChangeReceiver;

import javax.inject.Singleton;

import dagger.Component;
@Component(
        modules = {
                ViewModelModule.class, ServicesModule.class, UseCaseModule.class,
                FirebaseModule.class, AppModule.class, RepositoryModule.class
        }
)
@Singleton
public interface AppComponent {
    @Component.Builder
    interface Builder {
        AppComponent.Builder applicationModule(AppModule appModule);
        AppComponent build();
    }
    ViewModelFactory getViewModelFactory();

    void inject(ClassesFragment classesFragment);
    void inject(VideoPlayerFragment videoPlayerFragment);
    void inject(GoalCompletedReceiver goalCompletedReceiver);
    void inject(LocaleChangeReceiver localeChangeReceiver);
}
