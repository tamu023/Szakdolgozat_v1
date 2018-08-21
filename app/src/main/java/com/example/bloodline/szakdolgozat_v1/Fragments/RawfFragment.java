package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class RawfFragment extends Fragment {

    private EditText edtMegnev;
    private boolean exist;
    private RadioButton rbSolid;
    private RadioButton rbLiquid;
    private boolean unit;
    private Firebase ref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rawf, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtMegnev = view.findViewById(R.id.rawName);

        final Switch swFlour = view.findViewById(R.id.rawFluor);
        final Switch swMilk = view.findViewById(R.id.rawMilk);
        final Switch swMeat = view.findViewById(R.id.rawMeat);
        rbSolid = view.findViewById(R.id.rawSolid);
        rbLiquid = view.findViewById(R.id.rawLiquid);

        Button btnAdd = view.findViewById(R.id.rawAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_params()) {
                    //létezik e már a listában a termék
                    if (Functions.getAcctype()) {
                        ref = new Firebase(Global_Vars.rawProdRef);
                    } else {
                        ref = new Firebase(Global_Vars.rawpendingProdRef);
                    }
                    if (rbLiquid.isChecked()) {
                        unit = false;
                    } else if (rbSolid.isChecked()) {
                        unit = true;
                    }
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            exist = false;
                            for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                                if (elsoszint.getKey().equals(edtMegnev.getText().toString())) {
                                    exist = true;
                                    break;
                                }
                            }
                            //miután lefutott a keresés az adatbázisban utána fut csak le a kiértékelés
                            if (!exist) {
                                //Alert Dialog ami megkérdezi hogy biztosan hozzáadjuk e ebben az esetben a getactivity().get.... helyett getcontext() kell használni
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
                                a_builder.setMessage("Do you want to add this Ingredient?").setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AddProducts uj = new AddProducts(edtMegnev.getText().toString(), swFlour.isChecked(), swMilk.isChecked(), swMeat.isChecked(), unit);
                                                //adatbázisba beírás
                                                ref.child(edtMegnev.getText().toString()).setValue(uj);
                                                ref = ref.child(edtMegnev.getText().toString());
                                                //törölni azokat a childokat amelyek nem szükségesek
                                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                                                            if (elsoszint.getKey().equals("carbohydrate") || elsoszint.getKey().equals("recept") || elsoszint.getKey().equals("ingredients") || elsoszint.getKey().equals("quantity")) {
                                                                ref.child(elsoszint.getKey()).removeValue();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {

                                                    }
                                                });
                                                //hozzáadás után visszalépünk az előző képernyőre
                                                Fragment fragm = new ProductTypeFragment();
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction ft = fm.beginTransaction();
                                                ft.replace(R.id.mainframeplace, fragm);
                                                ft.commit();
                                                Toast.makeText(getActivity().getApplicationContext(), "Added to database", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("New Item");
                                alert.show();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "This ingredient is already in the database", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill Name and Pick one Radio Button", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //ellenörzi hogy nev mező ki vannak e töltve
    private boolean check_params() {
        boolean ok = true;
        if (edtMegnev.getText().toString().isEmpty() || (!rbLiquid.isChecked() && !rbSolid.isChecked())) {
            ok = false;
        }
        return ok;
    }
}
