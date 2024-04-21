package com.example.mycoin.di.modules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    public FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public FirebaseFirestore providesFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    public DatabaseReference providesDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    public StorageReference providesStorageReference() {
        return FirebaseStorage.getInstance().getReference();
    }

    @Provides
    public FirebaseStorage providesFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }
}
