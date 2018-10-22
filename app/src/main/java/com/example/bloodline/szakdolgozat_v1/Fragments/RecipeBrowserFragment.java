package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bloodline.szakdolgozat_v1.Adapters.RecipeBrowserAdapter;
import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RecipeBrowserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_browser, null);
    }

    private List<FinishedFood> recipeList;
    private ListView listView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listRecipeBrowser);
        recipeList = new ArrayList<>();
        Firebase ref = new Firebase(Global_Vars.finProdRef);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    recipeList.add(new FinishedFood(elsoszint.child("foodname").getValue().toString(), (boolean) elsoszint.child("flour").getValue(), (boolean) elsoszint.child("milk").getValue(), (boolean) elsoszint.child("meat").getValue(), elsoszint.child("recipe").getValue().toString()));
                    RecipeBrowserAdapter adapter = new RecipeBrowserAdapter(getActivity().getApplicationContext(), R.layout.item_rawingredient, recipeList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
