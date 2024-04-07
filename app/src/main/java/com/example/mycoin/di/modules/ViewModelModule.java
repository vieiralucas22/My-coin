package com.example.mycoin.di.modules;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.di.ViewModelKey;
import com.example.mycoin.fragments.confirmcode.ConfirmCodeViewModel;
import com.example.mycoin.fragments.forgotpassword.ForgotPasswordViewModel;
import com.example.mycoin.fragments.login.LoginViewModel;
import com.example.mycoin.fragments.signup.SignUpViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    ViewModel bindsLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    ViewModel bindsSignUpViewModel(SignUpViewModel signUpViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel.class)
    ViewModel bindsForgotPasswordViewModel(ForgotPasswordViewModel forgotPasswordViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmCodeViewModel.class)
    ViewModel bindsConfirmCodeViewModel(ConfirmCodeViewModel confirmCodeViewModel);

}
