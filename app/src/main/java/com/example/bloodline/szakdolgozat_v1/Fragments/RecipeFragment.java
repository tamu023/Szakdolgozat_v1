package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.R;

public class RecipeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, null);
    }

    //TODO esetleges kinézet bővítés
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = getContext().getSharedPreferences("recept", Context.MODE_PRIVATE);
        String recept = prefs.getString("recept", "");
        TextView txtRecept = view.findViewById(R.id.recipeTxtRecept);
        txtRecept.setText(recept);
    }
}
