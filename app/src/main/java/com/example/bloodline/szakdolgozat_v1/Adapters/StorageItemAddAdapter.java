package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.Fragments.StorageFragment;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

public class StorageItemAddAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> storageItemList;
    private boolean exist;
    private double exchangedQuantity;
    private Firebase ref;

    public StorageItemAddAdapter(Context context, int resource, List<AddProducts> storageItemList) {
        super(context, resource, storageItemList);
        this.context = context;
        this.resource = resource;
        this.storageItemList = storageItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final AddProducts storageAddItem = storageItemList.get(position);

        final boolean unit = storageAddItem.getUnit();
        String liquid[] = {"L", "DL", "CL", "ML"};
        String solid[] = {"KG", "DKG", "G"};


        TextView txtName = view.findViewById(R.id.addstorTxtName);
        final EditText edtQuantity = view.findViewById(R.id.addstorEdtQuantity);
        final Spinner spnUnit = view.findViewById(R.id.addstorSpnUnit);
        Button btnOk = view.findViewById(R.id.addstorBtnOk);

        if (unit) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, solid);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnUnit.setAdapter(spinnerArrayAdapter);
        } else if (!unit) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, liquid);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnUnit.setAdapter(spinnerArrayAdapter);
        }

        txtName.setText(storageAddItem.getMegnevezes());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtQuantity.getText().toString().isEmpty() && !edtQuantity.getText().toString().equals("0")) {
                    //Beírt összeg átváltása, Solid esetén Kilogrammra ra Liquid esetén Literre
                    //TODO többi listához is Toast okat hozzáadni hogy informatívabb legyen egy egy sikeres művelet
                    exchangedQuantity = 0;
                    if (unit) {
                        if (spnUnit.getSelectedItem().toString().equals("KG")) {
                            exchangedQuantity = Double.parseDouble(edtQuantity.getText().toString());
                        } else if (spnUnit.getSelectedItem().toString().equals("DKG")) {
                            exchangedQuantity = Double.parseDouble(edtQuantity.getText().toString()) / 10;
                        } else if (spnUnit.getSelectedItem().toString().equals("G")) {
                            exchangedQuantity = Double.parseDouble(edtQuantity.getText().toString()) / 1000;
                        }
                    } else {
                        if (spnUnit.getSelectedItem().toString().equals("L")) {
                            exchangedQuantity = Double.parseDouble(edtQuantity.getText().toString());
                        } else if (spnUnit.getSelectedItem().toString().equals("DL")) {
                            exchangedQuantity = Double.parseDouble(edtQuantity.getText().toString()) / 10;
                        } else if (spnUnit.getSelectedItem().toString().equals("CL")) {
                            exchangedQuantity = Double.parseDouble(edtQuantity.getText().toString()) / 100;
                        } else if (spnUnit.getSelectedItem().toString().equals("ML")) {
                            exchangedQuantity = Double.parseDouble(edtQuantity.getText().toString()) / 1000;
                        }
                    }
                    ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            double storageQuantity = 0;
                            exist = false;
                            for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                                if (elsoszint.getKey().equals(storageAddItem.getMegnevezes())) {
                                    exist = true;
                                    storageQuantity = (double) elsoszint.child("quantity").getValue();
                                }
                            }
                            //ha olyan terméket adnánk hozzá a storagehoz ami már van benne akkor összeadja a hozzáadni kívánt mennyiséget a bent lévő mennyiséggel
                            if (exist) {
                                exchangedQuantity = storageQuantity + exchangedQuantity;
                                AddProducts uj = new AddProducts(storageAddItem.getMegnevezes(), storageAddItem.getUnit(), exchangedQuantity);
                                ref.child(storageAddItem.getMegnevezes()).setValue(uj);
                            } else {
                                AddProducts uj = new AddProducts(storageAddItem.getMegnevezes(), storageAddItem.getUnit(), exchangedQuantity);
                                ref.child(storageAddItem.getMegnevezes()).setValue(uj);
                            }
                            //törli az olyan childokat amelyek fölöslegesen vannak benne
                            ref = ref.child(storageAddItem.getMegnevezes());
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot elsoszint : dataSnapshot.getChildren()){
                                        if(!elsoszint.getKey().equals("megnevezes") && !elsoszint.getKey().equals("unit") && !elsoszint.getKey().equals("quantity")){
                                            ref.child(elsoszint.getKey()).removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });

                            Toast.makeText(getContext(), "Ingredient adding to the Storage was Successful", Toast.LENGTH_SHORT).show();
                            Fragment startfragment = new StorageFragment();
                            Context context = parent.getContext();
                            FragmentManager fm = ((Activity) context).getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.mainframeplace, startfragment);
                            ft.commit();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Please fill the empty field.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
