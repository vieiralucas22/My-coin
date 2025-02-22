package com.example.mycoin.di.modules;

import com.example.mycoin.usecases.impl.ChangePasswordImpl;
import com.example.mycoin.usecases.impl.EditProfileImpl;
import com.example.mycoin.usecases.impl.LoginImpl;
import com.example.mycoin.usecases.impl.RegisterImpl;
import com.example.mycoin.usecases.impl.SendForgotPasswordEmailImpl;
import com.example.mycoin.usecases.interfaces.ChangePassword;
import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.usecases.interfaces.Login;
import com.example.mycoin.usecases.interfaces.Register;
import com.example.mycoin.usecases.interfaces.SendForgotPasswordEmail;

import dagger.Binds;
import dagger.Module;

@Module
public interface UseCaseModule {
    @Binds
    Login bindsLogin(LoginImpl loginImpl);

    @Binds
    Register bindsRegister(RegisterImpl registerImpl);

    @Binds
    SendForgotPasswordEmail bindsSendForgotPasswordEmail(
            SendForgotPasswordEmailImpl sendForgotPasswordEmailImpl);

    @Binds
    ChangePassword bindsChangePassword(ChangePasswordImpl changePasswordImpl);

    @Binds
    EditProfile bindsEditProfile(EditProfileImpl editProfile);
}
