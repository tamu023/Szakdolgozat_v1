package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bloodline.szakdolgozat_v1.Adapters.AdminPendingFinishedIngredientAdapter;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPenFinishedIngredientFragment extends Fragment {

    private List<FinishedFood> finishedFoodList;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_pen_finished_ingredient, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.admfinList);
        finishedFoodList = new ArrayList<>();
        //TODO elkészíteni ugyanezt a Fragmentet a Finishedhez is
        Firebase ref = new Firebase(Global_Vars.finpendingProdRef);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    finishedFoodList.add(new FinishedFood(elsoszint.getKey(), (long) elsoszint.child("carb").getValue(), (boolean) elsoszint.child("flour").getValue(), (boolean) elsoszint.child("milk").getValue(), (boolean) elsoszint.child("meat").getValue()));
                    AdminPendingFinishedIngredientAdapter adapter = new AdminPendingFinishedIngredientAdapter(getActivity().getApplicationContext(), R.layout.item_admin_pen_finished_ingredient, finishedFoodList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
