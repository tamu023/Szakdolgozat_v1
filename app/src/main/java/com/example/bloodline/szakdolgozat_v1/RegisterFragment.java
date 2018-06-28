package com.example.bloodline.szakdolgozat_v1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private EditText edtEmail;
    private EditText edtPassw;
    private EditText edtConfpassw;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Functions.setmAuth(FirebaseAuth.getInstance());
        Button btnReg = view.findViewById(R.id.regButton);
        TextView txtBack = view.findViewById(R.id.regBack);
        edtEmail = view.findViewById(R.id.regEmail);
        edtPassw = view.findViewById(R.id.regPass);
        edtConfpassw = view.findViewById(R.id.regConfpass);

        //Reklám
        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Back gomb
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.loginfragmentplace, fragment);
                ft.commit();

            }
        });

        //Bejelentkezési gomb
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.setEmail(edtEmail.getText().toString());
                String passw = edtPassw.getText().toString();
                String confpassw = edtConfpassw.getText().toString();

                if (CheckInputs(Functions.getEmail(), passw, confpassw)) {
                    Register(Functions.getEmail(), passw);
                    //teszt adatbázisba beírás
                    RegLog reg = new RegLog("emai@l.com", "pisti", false, true, false, 78, 185, true, false);
                    reg.setVariables();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Missing or Invalid parameters.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean CheckInputs(String email, String password, String confpassword) {
        boolean ok = true;
        if (email.isEmpty() || password.isEmpty() || confpassword.isEmpty() || !password.equals(confpassword)) {
            ok = false;
        }
        return ok;
    }

    private void Register(String email, String password) {
        Functions.getmAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Functions.setUser(Functions.getmAuth().getCurrentUser());
                            Functions.setUID(Functions.getUser().getUid());
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
