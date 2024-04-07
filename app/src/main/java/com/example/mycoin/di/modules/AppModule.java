package com.example.mycoin.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    public Context providesContext() {
        return mContext;
    }

    @Provides
    public SharedPreferences providesSharedPreferences() {
        return mContext.getSharedPreferences("MyCoinShared", Context.MODE_PRIVATE);
    }
}
