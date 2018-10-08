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
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Adapters.NewMealAdapter;
import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFoodIngredient;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NewMealFragment extends Fragment {

    //sima boolean helyett az osztály Booleant kell használni mert szükség van egy harmadik értékre a NULL ra
    private Boolean filFlour = null;
    private Boolean filMilk = null;
    private Boolean filMeat = null;
    private SeekBar sbFlour;
    private SeekBar sbMilk;
    private SeekBar sbMeat;
    private List<FinishedFood> finishedFoodList;
    private ListView listView;
    private List<AddProducts> rawFoodList;
    private List<FinishedFoodIngredient> ingredientList;
    private Firebase ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_meal, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.newmealList);
        finishedFoodList = new ArrayList<>();
        rawFoodList = new ArrayList<>();
        final TextView txtFlour = view.findViewById(R.id.newmealTxtFlour);
        final TextView txtMilk = view.findViewById(R.id.newmealTxtMilk);
        final TextView txtMeat = view.findViewById(R.id.newmealTxtMeat);
        final TextView txtPortion = view.findViewById(R.id.newmealTxtPortion);
        Button btnSub = view.findViewById(R.id.newmealBtnSub);
        Button btnAdd = view.findViewById(R.id.newmealBtnAdd);
        Button btnSearch = view.findViewById(R.id.newmealBtnSearch);
        sbFlour = view.findViewById(R.id.newmealSbFlour);
        sbMilk = view.findViewById(R.id.newmealSbMilk);
        sbMeat = view.findViewById(R.id.newmealSbMeat);

        SharedPreferences prefs = getContext().getSharedPreferences("seged", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Adag", 1);
        editor.apply();

        //raw food listből kiolvassa a megnevezést és a hozzá tartozó mértékegységet, hogy továbbadhassa az adapternek
        ref = new Firebase(Global_Vars.rawProdRef);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    rawFoodList.add(new AddProducts((String) elsoszint.child("megnevezes").getValue(), (boolean) elsoszint.child("unit").getValue()));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //preset seekbars
        if (Functions.getLaktozerzekenyseg()) {
            sbMilk.setProgress(0);
            ChangeFilter(txtMilk, 0);
        }
        if (Functions.getLiszterzekenyseg()) {
            sbFlour.setProgress(0);
            ChangeFilter(txtFlour, 0);
        }

        //seekbar mozgatására változik a textview színe
        sbFlour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ChangeFilter(txtFlour, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbMilk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ChangeFilter(txtMilk, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbMeat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ChangeFilter(txtMeat, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(txtPortion.getText().toString()) == 1) {
                    txtPortion.setText("10");
                } else {
                    txtPortion.setText(Integer.toString(Integer.parseInt(txtPortion.getText().toString()) - 1));
                }
                SavePortion(txtPortion);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(txtPortion.getText().toString()) == 10) {
                    txtPortion.setText("1");
                } else {
                    txtPortion.setText(Integer.toString(Integer.parseInt(txtPortion.getText().toString()) + 1));
                }
                SavePortion(txtPortion);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = finishedFoodList.size() - 1; i >= 0; i--) {
                    finishedFoodList.remove(i);
                    NewMealAdapter adapter = new NewMealAdapter(getActivity().getApplicationContext(), R.layout.item_new_meal, finishedFoodList, rawFoodList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                ref = new Firebase(Global_Vars.finProdRef);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (final DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            ReadFilters();
                            final boolean milk = (boolean) elsoszint.child("milk").getValue();
                            final boolean meat = (boolean) elsoszint.child("meat").getValue();
                            final boolean flour = (boolean) elsoszint.child("flour").getValue();
                            if (filMeat == null) {
                                filMeat = meat;
                            }
                            if (filMilk == null) {
                                filMilk = milk;
                            }
                            if (filFlour == null) {
                                filFlour = flour;
                            }
                            if (filFlour == flour && filMeat == meat && filMilk == milk) {
                                //ingredientList beolvasása
                                ref = new Firebase(Global_Vars.finProdRef).child(elsoszint.getKey()).child("ingredientList");
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ingredientList = new ArrayList<>();

                                        for (DataSnapshot masodikszint : dataSnapshot.getChildren()) {
                                            ingredientList.add(new FinishedFoodIngredient((String) masodikszint.child("megnevezes").getValue(), (double) masodikszint.child("mennyiseg").getValue()));
                                        }
                                        finishedFoodList.add(new FinishedFood(elsoszint.getKey(), (long) elsoszint.child("carb").getValue(), flour, milk, meat, (String) elsoszint.child("recipe").getValue(), (double) elsoszint.child("preptime").getValue(), ingredientList));
                                        NewMealAdapter adapter = new NewMealAdapter(getActivity().getApplicationContext(), R.layout.item_new_meal, finishedFoodList, rawFoodList);
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

    }

    //Portion eltárolása hogy adapterclassban is lekérhessük
    private void SavePortion(TextView txt) {
        SharedPreferences prefs = getContext().getSharedPreferences("seged", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Adag", Integer.parseInt(txt.getText().toString()));
        editor.apply();
    }

    //általunk beállított filtereket állítja be hogy az alapján keressen
    private void ReadFilters() {
        if (sbFlour.getProgress() == 0) {
            filFlour = false;
        } else if (sbFlour.getProgress() == 1) {
            filFlour = null;
        } else if (sbFlour.getProgress() == 2) {
            filFlour = true;
        }
        if (sbMeat.getProgress() == 0) {
            filMeat = false;
        } else if (sbMeat.getProgress() == 1) {
            filMeat = null;
        } else if (sbMeat.getProgress() == 2) {
            filMeat = true;
        }
        if (sbMilk.getProgress() == 0) {
            filMilk = false;
        } else if (sbMilk.getProgress() == 1) {
            filMilk = null;
        } else if (sbMilk.getProgress() == 2) {
            filMilk = true;
        }
    }

    private void ChangeFilter(TextView txt, int progress) {
        switch (progress) {
            case 0:
                txt.setBackgroundColor(0xffff0000);
                break;
            case 1:
                txt.setBackgroundColor(0xff8f8f8f);
                break;
            case 2:
                txt.setBackgroundColor(0xff00ff00);
                break;
        }
    }

}
