package com.example.mycoin.usecases.interfaces;

public interface Register {
    boolean signUp(String email, String password, String dateBirth, String username);
}
