package com.example.mycoin.fragments.classes.allclasses;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentAllClassesBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.di.components.DaggerAppComponent;
import com.example.mycoin.di.modules.AppModule;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.gateway.repository.ClassRepository;
import com.example.mycoin.utils.ListUtil;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import javax.inject.Inject;

public class ClassesFragment extends BaseFragment implements View.OnClickListener {

    private FragmentAllClassesBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;
    private CircularProgressIndicator mCircularProgressIndicator;

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
        mMenuNavigation = mBinding.navigationMenu;
        mRecyclerView = mBinding.recyclerView;
        mCircularProgressIndicator = mBinding.progressClasses;
        loadClassesByModule();
        mBinding.textTitle.setText(mModule);
        mAdapter = new ClassAdapter(mViewModel.getClassList(), classRepository,
                mModule, true, mViewModel);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListeners() {
        mBinding.buttonBack.setOnClickListener(this);
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
        mMenuNavigation.viewRanking.setOnClickListener(this);
        mMenuNavigation.viewGoals.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void initObservers() {
        mViewModel.getLoadData().observe(getViewLifecycleOwner(), this::showClasses);
        mViewModel.getProgress().observe(getViewLifecycleOwner(), percentage -> {
            ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mCircularProgressIndicator,
                    "progress", mCircularProgressIndicator.getProgress(), percentage);
            progressAnimator.setDuration(300); // Duração da animação em milissegundos
            progressAnimator.start();
            String totalProgress = String.valueOf(percentage);
            mBinding.textPercentage.setText(totalProgress + "%");
            mBinding.progressClasses.setProgress(percentage);
        });
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

    private void goRankingScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_classesFragment_to_rankingFragment);
    }

    private void goHomeScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_classesFragment_to_homeFragment);
    }

    private void goEditProfileScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_classesFragment_to_generalProfileFragment);
    }

    private void goGoalsScreen(View v) {
        Navigation.findNavController(v)
                .navigate(R.id.action_classesFragment_to_goalsFragment);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        } else if (v.getId() == R.id.view_person) {
            goEditProfileScreen(v);
        } else if (v.getId() == R.id.view_ranking) {
            goRankingScreen(v);
        } else if (v.getId() == R.id.view_home) {
            goHomeScreen(v);
        } else if (v.getId() == R.id.view_goals) {
            goGoalsScreen(v);
        }
    }
}
