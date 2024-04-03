package com.example.mycoin.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static final String TAG = ViewModelFactory.class.getSimpleName();

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mCreators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        mCreators = creators;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> clazz) {
        Provider<ViewModel> provider = mCreators.get(clazz);

        if (provider == null) {
            throw new NullPointerException(clazz.getSimpleName() + " is not valid! It`s null.");
        }

        return (T) provider.get();
    }
}
