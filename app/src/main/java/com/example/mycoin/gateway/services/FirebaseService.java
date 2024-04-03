package com.example.mycoin.gateway.services;

import java.util.concurrent.atomic.AtomicBoolean;

public interface FirebaseService {
    boolean authenticate(String email, String password);
}
