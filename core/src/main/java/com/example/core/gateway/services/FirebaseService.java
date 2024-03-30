package com.example.core.gateway.services;

import java.util.concurrent.atomic.AtomicBoolean;

public interface FirebaseService {
    AtomicBoolean authenticate(String email, String password);
}
