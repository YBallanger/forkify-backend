package com.forkify_backend.service;

public interface AuthenticationService {
    /**
     * Sign up a new user using Firebase authentication.
     *
     * @param email    a String representing the user's email address
     * @param password a String representing the user's password
     * @return l'UID Firebase de l'utilisateur créé
     */
    String FirebaseSignUp(String email, String password);
}
