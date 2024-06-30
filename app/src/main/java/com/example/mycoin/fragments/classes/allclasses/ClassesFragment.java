package com.example.mycoin.fragments.classes.allclasses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentAllClassesBinding;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.utils.ListUtil;

import javax.inject.Inject;

public class ClassesFragment extends BaseFragment implements View.OnClickListener {

    private FragmentAllClassesBinding mBinding;

    private ClassesViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private ClassAdapter mAdapter;
    private String mModule;

    @Inject
    ClassRepository classRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAllClassesBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DaggerAppComponent.builder()
                .applicationModule(new AppModule(getContext()))
                .build()
                .inject(this);

        mViewModel = getViewModel(ClassesViewModel.class);
        initComponents();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mRecyclerView = mBinding.recyclerView;
        loadClassesByModule();
        mAdapter = new ClassAdapter(mViewModel.getClassList(), classRepository, mModule);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListeners() {
        mBinding.buttonBack.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getLoadData().observe(getViewLifecycleOwner(), this::showClasses);
    }

    private void showClasses(Boolean isLoad) {
        if (isLoad && !ListUtil.isEmpty(mViewModel.getClassList())) {
            mAdapter.setItems(mViewModel.getClassList());
        }
    }

    private ClassesFragmentArgs getArgs() {
        if (getArguments() == null) return null;

        return ClassesFragmentArgs.fromBundle(getArguments());
    }

    private void loadClassesByModule() {
        if (getArgs() == null) return;

        mModule = getArgs().getModule();

        mViewModel.loadClassesInBD(mModule);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        }
    }
}
