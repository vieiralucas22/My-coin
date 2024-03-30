package com.example.core.usecases.interfaces;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Login {
    AtomicBoolean authenticate(String email, String password);
}
