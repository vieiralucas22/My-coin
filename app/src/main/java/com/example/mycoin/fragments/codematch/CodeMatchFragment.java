package com.example.mycoin.fragments.codematch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentCodeMatchBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;

public class CodeMatchFragment extends BaseFragment implements View.OnClickListener {

    private FragmentCodeMatchBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;

    private CodeMatchViewModel mViewModel;
    private int mPreviousRoomCode = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCodeMatchBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = getViewModel(CodeMatchViewModel.class);
        initComponents();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mMenuNavigation = mBinding.navigationMenu;
    }

    private void initListeners() {
        mBinding.buttonBack.setOnClickListener(this);
        mMenuNavigation.viewHome.setOnClickListener(this);
        mMenuNavigation.viewPerson.setOnClickListener(this);
        mMenuNavigation.viewRanking.setOnClickListener(this);
        mMenuNavigation.viewGoals.setOnClickListener(this);
        mBinding.buttonCreateRoom.setOnClickListener(this);
        mBinding.buttonJoin.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getLoadRoomCode().observe(getViewLifecycleOwner(), roomCode -> {
            if (mPreviousRoomCode == roomCode) return;
            mPreviousRoomCode = roomCode;
            goQuizScreen(getView(), roomCode, true);
        });

        mViewModel.getJoinRoom().observe(getViewLifecycleOwner(), roomCode -> {
            if (!mViewModel.canNavigate()) return;
            mViewModel.setCanNavigate(false);
            goQuizScreen(getView(), roomCode, false);
        });
    }

    private void goGoalsScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_codeMatchFragment_to_goalsFragment);
    }

    private void goHomeScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_codeMatchFragment_to_homeFragment);
    }

    private void goRankingScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_codeMatchFragment_to_rankingFragment);
    }

    private void goEditProfileScreen(View v) {
        Navigation.findNavController(v).navigate(R.id.action_codeMatchFragment_to_generalProfileFragment);
    }

    private void goQuizScreen(View v, int roomCode, boolean isOwnerRoom) {
        NavDirections action = CodeMatchFragmentDirections.actionCodeMatchFragmentToQuizFragment()
                .setRoomCode(roomCode)
                .setOwnerRoom(isOwnerRoom);

        Navigation.findNavController(v).navigate(action);
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
        } else if (v.getId() == R.id.button_create_room) {
            mViewModel.createRoom();
        } else if (v.getId() == R.id.button_join) {
            joinRoom();
        }
    }

    private void joinRoom() {
        String codeRoom = mBinding.editRoomCode.getText().toString();

        mViewModel.setCanNavigate(true);
        mViewModel.joinRoom(codeRoom);
    }

}