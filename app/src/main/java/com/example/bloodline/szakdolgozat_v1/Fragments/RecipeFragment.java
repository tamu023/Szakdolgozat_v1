package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.Firebase;

public class RecipeFragment extends Fragment {

    private Firebase ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //foodname
        SharedPreferences foodpref = getContext().getSharedPreferences("foodname", Context.MODE_PRIVATE);
        String foodname = foodpref.getString("foodname", "");
        //recept
        SharedPreferences prefs = getContext().getSharedPreferences("recept", Context.MODE_PRIVATE);
        String recept = prefs.getString("recept", "");
        TextView txtRecept = view.findViewById(R.id.recipeTxtRecept);
        txtRecept.setText(recept);

        Button btnLike = view.findViewById(R.id.recipeBtnLike);
        Button btnDislike = view.findViewById(R.id.recipeBtnDislike);
        final LinearLayout linLikes = view.findViewById(R.id.recipeLinLikes);

        ref = new Firebase(Global_Vars.finProdRef).child(foodname).child("likes").child(Functions.getUID());
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.setValue(true);
                Toast.makeText(getContext(), "Thank you for your feedback", Toast.LENGTH_SHORT).show();
                linLikes.setVisibility(View.GONE);
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.setValue(false);
                Toast.makeText(getContext(), "Thank you for your feedback", Toast.LENGTH_SHORT).show();
                linLikes.setVisibility(View.GONE);
            }
        });
    }
}
