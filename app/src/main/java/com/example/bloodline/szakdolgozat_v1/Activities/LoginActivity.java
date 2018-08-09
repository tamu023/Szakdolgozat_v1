package com.example.bloodline.szakdolgozat_v1.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Fragments.LoginFragment;
import com.example.bloodline.szakdolgozat_v1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Functions.setmAuth(FirebaseAuth.getInstance());
        Fragment startfragment = new LoginFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginfragmentplace, startfragment);
        ft.commit();

        //Reklám
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
