package com.example.mycoin.fragments;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;

public class FirestoreDocumentLiveData extends LiveData<DocumentSnapshot> {

    private final DocumentReference documentReference;
    private ListenerRegistration listenerRegistration;

    public FirestoreDocumentLiveData(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    protected void onActive() {
        listenerRegistration = documentReference.addSnapshotListener(
                MetadataChanges.INCLUDE, (snapshot, e) -> {
            if (e != null) {
                return;
            }
            setValue(snapshot);
        });
    }

    @Override
    protected void onInactive() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }
}
