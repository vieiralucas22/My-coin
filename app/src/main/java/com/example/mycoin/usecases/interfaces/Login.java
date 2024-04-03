package com.example.mycoin.usecases.interfaces;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Login {
    boolean authenticate(String email, String password);
}
