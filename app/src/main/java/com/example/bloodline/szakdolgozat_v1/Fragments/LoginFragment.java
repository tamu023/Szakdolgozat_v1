package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Activities.MainActivity;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.Firebase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private EditText edtEmail;
    private EditText edtPassw;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Reklám
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button btnLogin = view.findViewById(R.id.loginButton);
        TextView txtRegister = view.findViewById(R.id.loginRegister);
        edtEmail = view.findViewById(R.id.loginEmail);
        edtPassw = view.findViewById(R.id.loginPassw);

        //Regisztráció gomb
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisterFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.loginfragmentplace, fragment);
                ft.commit();
            }
        });

        //Bejelentkezési gomb
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.setEmail(edtEmail.getText().toString());
                String passw = edtPassw.getText().toString();
                if (CheckInputs(Functions.getEmail(), passw)) {
                    Login(Functions.getEmail(), passw);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Missing or Invalid parameters.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean CheckInputs(String email, String password) {
        boolean ok = true;
        if (email.isEmpty() || password.isEmpty()) {
            ok = false;
        }
        return ok;
    }

    private void Login(String email, String password) {
        Functions.getmAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Functions.setUser(Functions.getmAuth().getCurrentUser());
                            Functions.setUID(Functions.getUser().getUid());
                            Firebase.setAndroidContext(getActivity().getApplicationContext());
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            edtPassw.setText(null);
                            Toast.makeText(getActivity().getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
