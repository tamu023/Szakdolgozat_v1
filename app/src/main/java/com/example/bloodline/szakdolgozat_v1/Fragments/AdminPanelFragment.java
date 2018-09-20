package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bloodline.szakdolgozat_v1.R;

public class AdminPanelFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_panel, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnPendingRaw = view.findViewById(R.id.admbtnPendingRaw);
        Button btnPendingFinished = view.findViewById(R.id.admbtnPendingFinished);
        Button btnRaw = view.findViewById(R.id.admbtnRaw);
        Button btnFinished = view.findViewById(R.id.admbtnFinished);
        Button btnAdmin = view.findViewById(R.id.admbtnUserVerify);

        btnPendingRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragment(R.id.mainframeplace, new AdminPenRawIngredientFragment());
            }
        });

        btnPendingFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO elkészíteni a pending finishedhez a listát és a fragmentet

            }
        });

        btnRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragment(R.id.mainframeplace, new AdminRawIngredientFragment());
            }
        });

        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragment(R.id.mainframeplace,new AdminFinishedIngredientFragment());
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragment(R.id.mainframeplace, new AdminVerifyFragment());
            }
        });
    }

    private void ChangeFragment(int position, Fragment fr) {
        Fragment startfragment = fr;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(position, startfragment);
        ft.commit();
    }
}
