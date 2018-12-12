package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Adapters.StorageAdapter;
import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;


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
    private TextView txtName;
    private TextView txtEmail;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //navigation drawer header text set
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        txtName =  headerView.findViewById(R.id.navName);
        txtEmail =  headerView.findViewById(R.id.navEmail);
        listView = view.findViewById(R.id.storageListRaw);
        storageItemList = new ArrayList<>();

        //adatok lekérése adatbázisból
        Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot masodikszint : dataSnapshot.getChildren()) {
                    switch (masodikszint.getKey()) {
                        case "name":
                            Functions.setName(masodikszint.getValue().toString());
                            break;
                        case "email":
                            Functions.setEmail(masodikszint.getValue().toString());
                            break;
                        case "cukorbetegseg":
                            Functions.setCukorbetegseg((boolean) masodikszint.getValue());
                            break;
                        case "liszterzekenyeg":
                            Functions.setLiszterzekenyseg((boolean) masodikszint.getValue());
                            break;
                        case "weight":
                            Functions.setWeight((long) masodikszint.getValue());
                            break;
                        case "height":
                            Functions.setHeight((long) masodikszint.getValue());
                            break;
                        case "gender":
                            Functions.setGender((boolean) masodikszint.getValue());
                            break;
                        case "bmiindex":
                            Functions.setBmiindex((double) masodikszint.getValue());
                            break;
                        case "laktozerzekenyseg":
                            Functions.setLaktozerzekenyseg((boolean) masodikszint.getValue());
                            break;
                        case "acctype":
                            Functions.setAcctype((boolean) masodikszint.getValue());
                            break;
                        default:
                    }
                }
                txtName.setText(Functions.getName());
                txtEmail.setText(Functions.getEmail());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    storageItemList.add(new AddProducts(elsoszint.getKey(), (boolean) elsoszint.child("unit").getValue(), (double) elsoszint.child("quantity").getValue()));
                    StorageAdapter adapter = new StorageAdapter(getActivity().getApplicationContext(), R.layout.item_storageingredient, storageItemList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Button btnAdd = view.findViewById(R.id.storageBtnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment startfragment = new StorageItemAddFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainframeplace, startfragment);
                ft.commit();
            }
        });
    }
}
