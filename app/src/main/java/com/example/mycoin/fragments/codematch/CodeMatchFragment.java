package com.example.mycoin.fragments.codematch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.example.mycoin.R;
import com.example.mycoin.constants.Constants;
import com.example.mycoin.databinding.FragmentCodeMatchBinding;
import com.example.mycoin.databinding.NavigationMenuBinding;
import com.example.mycoin.fragments.BaseFragment;

public class CodeMatchFragment extends BaseFragment implements View.OnClickListener {

    private FragmentCodeMatchBinding mBinding;
    private NavigationMenuBinding mMenuNavigation;

    private CodeMatchViewModel mViewModel;
    private int mPreviousRoomCode = 0;
    private String mItemSelected;

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
        mItemSelected = "";
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
        mBinding.menu.setOnClickListener(this);
    }

    private void initObservers() {
        mViewModel.getLoadRoomCode().observe(getViewLifecycleOwner(), roomCode -> {
            if (mPreviousRoomCode == roomCode) return;
            mPreviousRoomCode = roomCode;

            if (!TextUtils.isEmpty(mItemSelected)) {
                goQuizScreen(getView(), roomCode, true);
            } else {
                Toast.makeText(getContext(), "Select a theme to create a game", Toast.LENGTH_LONG).show();
            }
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

    private void joinRoom() {
        String codeRoom = mBinding.editRoomCode.getText().toString();

        mViewModel.setCanNavigate(true);
        mViewModel.joinRoom(codeRoom);
    }

    private void openDropdown() {
        String[] itemsDropdown = {Constants.INTRODUCTION, Constants.ORGANIZE_HOME,
                Constants.ACTION_TIME, Constants.EXTRA};

        ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, itemsDropdown);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(mBinding.renderArea);
        listPopupWindow.setBackgroundDrawable(getDrawable(R.drawable.corners_24));
        listPopupWindow.setVerticalOffset(30);
        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            mItemSelected = itemsDropdown[position];
            mBinding.optionSelectedText.setText(mItemSelected);

            listPopupWindow.dismiss();
        });

        listPopupWindow.show();

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
            mViewModel.createRoom(mItemSelected);
        } else if (v.getId() == R.id.button_join) {
            joinRoom();
        } else if (v.getId() == R.id.menu) {
            openDropdown();
        }
    }
}