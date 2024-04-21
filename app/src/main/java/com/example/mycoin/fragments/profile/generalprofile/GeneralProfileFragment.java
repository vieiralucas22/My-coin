package com.example.mycoin.fragments.profile.generalprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mycoin.R;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralProfileFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(GeneralProfileFragment.class);

    private Button mButtonBack, mButtonCamera;
    private ViewGroup mEditUserProfileArea, mChangePasswordArea, mLogoutArea;
    private CircleImageView mUserImage;
    private GeneralProfileViewModel mViewModel;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    mUserImage.setImageURI(data.getData());
                    mViewModel.changePhoto(data.getData());
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_general_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(GeneralProfileViewModel.class);

        Log.d(TAG, "Enter in user profile fragment");

        initComponents(view);
        mViewModel.loadPhoto(mUserImage);
        initListeners();
        initObservers();
    }

    private void initComponents(View view) {
        mButtonBack = view.findViewById(R.id.button_back);
        mEditUserProfileArea = view.findViewById(R.id.edit_profile_area);
        mChangePasswordArea = view.findViewById(R.id.change_password_area);
        mButtonCamera = view.findViewById(R.id.button_change_photo);
        mUserImage = view.findViewById(R.id.user_image);
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mEditUserProfileArea.setOnClickListener(this);
        mChangePasswordArea.setOnClickListener(this);
        mButtonCamera.setOnClickListener(this);
    }

    private void initObservers() {
    }

    private void goEditProfileScreen() {
        if (getView() == null) return;
        Navigation.findNavController(getView())
                .navigate(R.id.action_generalProfileFragment_to_editUserProfileFragment);
    }

    private void goChangePasswordScreen() {
        if (getView() == null) return;
        Navigation.findNavController(getView())
                .navigate(R.id.action_generalProfileFragment_to_changeUserPasswordFragment);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            backScreen(v);
        } else if (v.getId() == R.id.edit_profile_area) {
            goEditProfileScreen();
        } else if (v.getId() == R.id.change_password_area) {
            goChangePasswordScreen();
        } else if (v.getId() == R.id.button_change_photo) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

            someActivityResultLauncher.launch(intent);
        }
    }
}