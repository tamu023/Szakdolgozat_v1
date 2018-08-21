package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.R;

public class AdminPenRawIngredientModFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_pen_raw_ingredient_mod, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView btnBack = view.findViewById(R.id.rawModBtnBack);
        Button btnAccept = view.findViewById(R.id.rawModBtnAccept);
        Button btnReject = view.findViewById(R.id.rawModBtnReject);
        TextView txtName = view.findViewById(R.id.rawModTxtName);
        Switch swFlour = view.findViewById(R.id.rawModSwFlour);
        Switch swMeat = view.findViewById(R.id.rawModSwMeat);
        Switch swMilk = view.findViewById(R.id.rawModSwMilk);
        RadioButton rbSolid = view.findViewById(R.id.rawModRbSolid);
        RadioButton rbLiquid = view.findViewById(R.id.rawModRbLiquid);
        RadioGroup rbgUnit = view.findViewById(R.id.rawModRbgUnit);

        SharedPreferences prefs = getContext().getSharedPreferences("seged", Context.MODE_PRIVATE);
        txtName.setText(prefs.getString("Name", ""));
        if (prefs.getBoolean("Flour", false)) {
            swFlour.setChecked(true);
        }
        if (prefs.getBoolean("Milk", false)) {
            swMilk.setChecked(true);
        }
        if (prefs.getBoolean("Meat", false)) {
            swMeat.setChecked(true);
        }
        if (prefs.getBoolean("Unit", false)) {
            rbgUnit.check(rbSolid.getId());
        } else {
            rbgUnit.check(rbLiquid.getId());
        }

        //TODO megcsinálni a gombokat
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeFragment(R.id.mainframeplace, new AdminPenRawIngredientFragment());
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
