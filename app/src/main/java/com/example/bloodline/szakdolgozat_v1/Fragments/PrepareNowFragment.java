package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bloodline.szakdolgozat_v1.Adapters.PrepareNowAdapter;
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


public class PrepareNowFragment extends Fragment {

    private List<FinishedFood> finishedFoodList;
    private ListView listView;
    private List<AddProducts> storageList;
    private List<FinishedFoodIngredient> ingredientList;
    private Firebase ref;
    private List<Double> maxPortionList;
    private List<Long> prepCountList;
    private List<Long> likesList;
    private List<Long> dislikesList;
    private double maxPortion;
    private boolean ok;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prepare_now, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listPrepareNow);
        finishedFoodList = new ArrayList<>();
        storageList = new ArrayList<>();
        maxPortionList = new ArrayList<>();
        prepCountList = new ArrayList<>();
        likesList = new ArrayList<>();
        dislikesList = new ArrayList<>();

        //felhasználó raktára
        ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    storageList.add(new AddProducts((String) elsoszint.child("megnevezes").getValue(), (boolean) elsoszint.child("unit").getValue(), (double) elsoszint.child("quantity").getValue()));
                }
                //készételek kiolvasása
                ref = new Firebase(Global_Vars.finProdRef);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (final DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            ingredientList = new ArrayList<>();
                            for (final DataSnapshot masodikszint : dataSnapshot.child(elsoszint.getKey()).child("ingredientList").getChildren()) {
                                ingredientList.add(new FinishedFoodIngredient((String) masodikszint.child("megnevezes").getValue(), (double) masodikszint.child("mennyiseg").getValue()));
                            }
                            Long likes = (long) 0;
                            Long dislikes = (long) 0;
                            for (DataSnapshot harmadikszint : elsoszint.child("likes").getChildren()) {
                                if ((boolean) harmadikszint.getValue()) {
                                    likes = likes + 1;
                                } else {
                                    dislikes = dislikes + 1;
                                }
                            }

                            //felhasználó raktárában megnézzük hogy megtalálható e a hozzávaló és ha igen esetleg oszthazó e többel és így kiderül hány adagot tud elkészíteni
                            maxPortion = 0;
                            for (int j = 0; j < ingredientList.size(); j++) {
                                ok = false;
                                FinishedFoodIngredient ingredientcheck = ingredientList.get(j);
                                for (int i = 0; i < storageList.size(); i++) {
                                    AddProducts check = storageList.get(i);
                                    //egyezik e a termék neve
                                    if (check.getMegnevezes().equals(ingredientcheck.getMegnevezes())) {
                                        //mennyiség elegendő e
                                        if (check.getQuantity() >= ingredientcheck.getMennyiseg()) {
                                            if (maxPortion >= Math.floor(check.getQuantity() / ingredientcheck.getMennyiseg()) || maxPortion == 0) {
                                                maxPortion = Math.floor(check.getQuantity() / ingredientcheck.getMennyiseg());
                                                ok = true;
                                                break;
                                            } else if (maxPortion < Math.floor(check.getQuantity() / ingredientcheck.getMennyiseg())) {
                                                ok = true;
                                                break;
                                            }
                                        } else {
                                            ok = false;
                                            break;
                                        }
                                    }
                                }
                                if (!ok) {
                                    break;
                                }
                            }
                            if (ok) {
                                //adapter
                                Long prepcount = (long) 0;
                                if (elsoszint.child("prepcount").getValue() != null) {
                                    prepcount = (long) elsoszint.child("prepcount").getValue();
                                }
                                likesList.add(likes);
                                dislikesList.add(dislikes);
                                prepCountList.add(prepcount);
                                maxPortionList.add(maxPortion);
                                finishedFoodList.add(new FinishedFood(elsoszint.getKey(), (long) elsoszint.child("carb").getValue(), (boolean) elsoszint.child("flour").getValue(), (boolean) elsoszint.child("milk").getValue(), (boolean) elsoszint.child("meat").getValue(), (String) elsoszint.child("recipe").getValue(), (double) elsoszint.child("preptime").getValue(), ingredientList));
                                PrepareNowAdapter adapter = new PrepareNowAdapter(getActivity().getApplicationContext(), R.layout.item_prepare_now, finishedFoodList, storageList, maxPortionList, prepCountList, likesList, dislikesList);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
