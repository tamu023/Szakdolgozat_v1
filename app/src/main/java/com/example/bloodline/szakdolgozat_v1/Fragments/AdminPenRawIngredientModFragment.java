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
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AdminPenRawIngredientModFragment extends Fragment {

    private boolean exist;
    private String name;

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
        final Switch swFlour = view.findViewById(R.id.rawModSwFlour);
        final Switch swMeat = view.findViewById(R.id.rawModSwMeat);
        final Switch swMilk = view.findViewById(R.id.rawModSwMilk);
        final RadioButton rbSolid = view.findViewById(R.id.rawModRbSolid);
        RadioButton rbLiquid = view.findViewById(R.id.rawModRbLiquid);
        final RadioGroup rbgUnit = view.findViewById(R.id.rawModRbgUnit);

        final SharedPreferences prefs = getContext().getSharedPreferences("seged", Context.MODE_PRIVATE);
        name = prefs.getString("Name", "");
        txtName.setText(name);
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

        btnAccept.setOnClickListener(new View.OnClickListener() {
            Firebase ref = new Firebase(Global_Vars.rawProdRef);

            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        exist = false;
                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            if (elsoszint.getKey().equals(name)) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            AddProducts uj = new AddProducts(name, swFlour.isChecked(), swMilk.isChecked(), swMeat.isChecked(), rbSolid.isChecked());
                            //adatbázisba beírás
                            ref.child(name).setValue(uj);
                            ref = ref.child(name);
                            //törölni azokat a childokat amelyek nem szükségesek
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                                        if (elsoszint.getKey().equals("carbohydrate") || elsoszint.getKey().equals("recept") || elsoszint.getKey().equals("ingredients") || elsoszint.getKey().equals("quantity")) {
                                            ref.child(elsoszint.getKey()).removeValue();
                                        }
                                    }
                                    Firebase delref = new Firebase(Global_Vars.rawpendingProdRef).child(name);
                                    delref.removeValue();
                                    Toast.makeText(getActivity().getApplicationContext(), "Ingredient Confirmed", Toast.LENGTH_SHORT).show();
                                    ChangeFragment(R.id.mainframeplace, new AdminPenRawIngredientFragment());
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        } else {
                            ref = new Firebase(Global_Vars.rawpendingProdRef).child(name);
                            ref.removeValue();
                            Toast.makeText(getActivity().getApplicationContext(), "Ingredient already exist", Toast.LENGTH_SHORT).show();
                            ChangeFragment(R.id.mainframeplace, new AdminPenRawIngredientFragment());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            Firebase ref = new Firebase(Global_Vars.rawpendingProdRef);

            @Override
            public void onClick(View v) {
                ref.child(prefs.getString("Name", "")).removeValue();
                Toast.makeText(getActivity().getApplicationContext(), "Delete Complete", Toast.LENGTH_SHORT).show();
                ChangeFragment(R.id.mainframeplace, new AdminPenRawIngredientFragment());
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
