package com.example.bloodline.szakdolgozat_v1;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FinishedfFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finishedf,  null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //TODO kitalálni hogy a hozzávalók hozzáadása hogyan valósuljon meg
        super.onViewCreated(view, savedInstanceState);
    }
}
