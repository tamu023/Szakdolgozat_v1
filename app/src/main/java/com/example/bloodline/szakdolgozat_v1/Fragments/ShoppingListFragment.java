package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bloodline.szakdolgozat_v1.Adapters.ShoppingListAdapter;
import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, null);
    }

    private List<AddProducts> shoppingItemList;
    private ListView listView;

    //TODO manuális hozzáadáshoz elkészíteni a hozzá tartozó fragmentet és a listview hez az adaptert
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.shoppingListView);
        shoppingItemList = new ArrayList<>();

        Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("shopping list");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    shoppingItemList.add(new AddProducts(elsoszint.getKey(), (boolean) elsoszint.child("unit").getValue(), (double) elsoszint.child("quantity").getValue()));
                    ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity().getApplicationContext(), R.layout.item_shopping_list, shoppingItemList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
