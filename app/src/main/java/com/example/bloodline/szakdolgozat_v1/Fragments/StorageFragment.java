package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.bloodline.szakdolgozat_v1.Adapters.StorageAdapter;
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

public class StorageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock, null);
    }

    private List<AddProducts> storageItemList;
    private ListView listView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.storageListRaw);
        storageItemList = new ArrayList<>();

        Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    storageItemList.add(new AddProducts(elsoszint.child("megnevezes").getValue().toString(), (boolean) elsoszint.child("unit").getValue(), (long) elsoszint.child("quantity").getValue()));
                    StorageAdapter adapter = new StorageAdapter(getActivity().getApplicationContext(), R.layout.item_storageingredient, storageItemList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //TODO új raw ingredient fragment amiben be tudjuk állítani hogy az adott alapanyagból mennyit szeretnénk hozzáadni
        Button btnAdd = view.findViewById(R.id.storageBtnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
