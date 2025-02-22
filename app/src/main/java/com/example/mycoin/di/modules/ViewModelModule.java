package com.example.mycoin.di.modules;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.di.ViewModelKey;
import com.example.mycoin.fragments.chat.ChatViewModel;
import com.example.mycoin.fragments.classes.allclasses.ClassesViewModel;
import com.example.mycoin.fragments.codematch.CodeMatchViewModel;
import com.example.mycoin.fragments.goals.GoalsViewModel;
import com.example.mycoin.fragments.quizz.QuizViewModel;
import com.example.mycoin.fragments.classes.videoclasses.VideoPlayerViewModel;
import com.example.mycoin.fragments.confirmcode.ConfirmCodeViewModel;
import com.example.mycoin.fragments.forgotpassword.ForgotPasswordViewModel;
import com.example.mycoin.fragments.home.HomeViewModel;
import com.example.mycoin.fragments.login.LoginViewModel;
import com.example.mycoin.fragments.profile.changeuserpassword.ChangeUserPasswordViewModel;
import com.example.mycoin.fragments.profile.editprofile.EditUserProfileViewModel;
import com.example.mycoin.fragments.profile.generalprofile.GeneralProfileViewModel;
import com.example.mycoin.fragments.quizz.result.ResultFragmentViewModel;
import com.example.mycoin.fragments.ranking.RankingViewModel;
import com.example.mycoin.fragments.signup.SignUpViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    ViewModel bindsLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    ViewModel bindsSignUpViewModel(SignUpViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel.class)
    ViewModel bindsForgotPasswordViewModel(ForgotPasswordViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmCodeViewModel.class)
    ViewModel bindsConfirmCodeViewModel(ConfirmCodeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GeneralProfileViewModel.class)
    ViewModel bindsGeneralProfileViewModel(GeneralProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditUserProfileViewModel.class)
    ViewModel bindsEditUserProfileViewModel(EditUserProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    ViewModel bindsHomeViewModel(HomeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChangeUserPasswordViewModel.class)
    ViewModel bindsChangeUserPasswordViewModel(ChangeUserPasswordViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VideoPlayerViewModel.class)
    ViewModel bindsVideoPlayerViewModel(VideoPlayerViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel.class)
    ViewModel bindsQuizViewModel(QuizViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ClassesViewModel.class)
    ViewModel bindsClassesViewModel(ClassesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RankingViewModel.class)
    ViewModel bindsRankingViewModel(RankingViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GoalsViewModel.class)
    ViewModel bindsGoalsViewModel(GoalsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CodeMatchViewModel.class)
    ViewModel bindsCodeMatchViewModel(CodeMatchViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ResultFragmentViewModel.class)
    ViewModel bindsResultFragmentViewModel(ResultFragmentViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel.class)
    ViewModel bindsChatViewModel(ChatViewModel viewModel);

}
