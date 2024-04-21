package com.example.mycoin.fragments.profile.generalprofile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.mycoin.usecases.interfaces.EditProfile;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralProfileViewModel extends ViewModel {
    public static final String TAG = LogcatUtil.getTag(GeneralProfileViewModel.class);

    private final FirebaseAuth mAuth;
    private final EditProfile mEditProfile;
    private final Context mContext;
    private final StorageReference storageReference;
    private final FirebaseStorage storage;



    @Inject
    public GeneralProfileViewModel(FirebaseAuth auth, EditProfile editProfile, Context mContext, StorageReference storageReference, FirebaseStorage storage) {
        mAuth = auth;
        mEditProfile = editProfile;
        this.mContext = mContext;
        this.storageReference = storageReference;
        this.storage = storage;
    }

    public void changePhoto(Uri uri) {
        String path = "UsersImages/" + mAuth.getUid();

        StorageReference reference = storageReference.child(path);

        reference.putFile(uri).addOnSuccessListener(task -> {
            Log.d(TAG, "upload done");
        }).addOnFailureListener(e -> {
            Log.d(TAG, e.getMessage());
        });
    }

    public void loadPhoto(CircleImageView mUserImage) {}
}