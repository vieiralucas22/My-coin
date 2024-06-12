package com.example.mycoin.fragments.profile.generalprofile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import com.example.mycoin.R;
import com.example.mycoin.databinding.FragmentGeneralProfileBinding;
import com.example.mycoin.fragments.BaseFragment;
import com.example.mycoin.utils.LogcatUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralProfileFragment  extends BaseFragment implements View.OnClickListener {
    public static final String TAG = LogcatUtil.getTag(GeneralProfileFragment.class);

    private Button mButtonBack, mButtonCamera, mButtonCancel, mButtonConfirm;
    private ViewGroup mEditUserProfileArea, mChangePasswordArea, mLogoutArea;
    private CircleImageView mUserImage;
    private TextView mTextPosition, mTextPoints;
    private Dialog mDialog;

    private FragmentGeneralProfileBinding mBinding;

    private GeneralProfileViewModel mViewModel;

    private final ActivityResultLauncher<Intent> pickPhotoResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    mUserImage.setImageURI(data.getData());
                    mViewModel.uploadPhoto(data.getData());
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGeneralProfileBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel(GeneralProfileViewModel.class);

        Log.d(TAG, "Enter in user profile fragment");

        initComponents();
        mViewModel.initUIWithUserData();
        initListeners();
        initObservers();
    }

    private void initComponents() {
        mButtonBack = mBinding.buttonBack;
        mEditUserProfileArea = mBinding.editProfileArea;
        mChangePasswordArea = mBinding.changePasswordArea;
        mLogoutArea = mBinding.logoutArea;
        mButtonCamera = mBinding.buttonChangePhoto;
        mUserImage = mBinding.userImage;
        mTextPoints = mBinding.textPoints;
        mTextPosition = mBinding.position;
        setUpAlertDialogLogout();
        mButtonCancel = mDialog.findViewById(R.id.button_cancel);
        mButtonConfirm = mDialog.findViewById(R.id.button_confirm_logout);
    }

    private void initListeners() {
        mButtonBack.setOnClickListener(this);
        mEditUserProfileArea.setOnClickListener(this);
        mChangePasswordArea.setOnClickListener(this);
        mLogoutArea.setOnClickListener(this);
        mButtonCamera.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mButtonConfirm.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void initObservers() {
        mViewModel.getLoadUIUsersDetails().observe(getViewLifecycleOwner(), userDetails -> {
            Uri uri = Uri.parse(userDetails.getPhoto());
            Picasso.get().load(uri).into(mUserImage);
            mTextPosition.setText(userDetails.getPosition() + " ");
            mTextPoints.setText(userDetails.getPoints() + " ");
        });
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

    private void goLoginScreen() {
        if (getView() == null) return;
        Navigation.findNavController(getView())
                .navigate(R.id.action_generalProfileFragment_to_loginFragment);
    }

    private void logout() {
        if (mViewModel.logout()) {
            mDialog.cancel();
            goLoginScreen();
        }
    }

    private void setUpAlertDialogLogout() {
        mDialog = new Dialog(getContext());
        mDialog.setContentView(R.layout.logout_alert);

        if (mDialog.getWindow() != null) {
            mDialog.getWindow().setBackgroundDrawableResource(R.drawable.corners_24);
        }
    }

    private void pickPhotoToProfileFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        pickPhotoResultLauncher.launch(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
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
            pickPhotoToProfileFromGallery();
        } else if (v.getId() == R.id.logout_area) {
            mDialog.show();
        } else if (v.getId() == R.id.button_cancel) {
            mDialog.cancel();
        } else if (v.getId() == R.id.button_confirm_logout) {
            logout();
        }
    }
}