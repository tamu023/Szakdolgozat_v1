package com.example.bloodline.szakdolgozat_v1;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Functions {
    private static String email;
    private static String UID;
    private static FirebaseUser user;
    private static FirebaseAuth mAuth;

    public static String getEmail() {
        return email;
    }

    public static String getUID() {
        return UID;
    }

    public static FirebaseUser getUser() {
        return user;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static void setEmail(String email) {
        Functions.email = email;
    }

    public static void setUID(String UID) {
        Functions.UID = UID;
    }

    public static void setUser(FirebaseUser user) {
        Functions.user = user;
    }

    public static void setmAuth(FirebaseAuth mAuth) {
        Functions.mAuth = mAuth;
    }
}
