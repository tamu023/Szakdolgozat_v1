package com.example.bloodline.szakdolgozat_v1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class ProductTypeFragment extends Fragment {
    //TODO Alapanyagra utaló képet szöveggel beilleszteni a bal gombra
    //TODO Kész ételre utaló képet szöveggel beilleszteni a jobb gombra
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_type, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnRaw = view.findViewById(R.id.addRaw);
        ImageButton btnFinished = view.findViewById(R.id.addFinished);

        //raw material felvétele
        btnRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //finished product felvétele
        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
