package com.example.bloodline.szakdolgozat_v1;

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
import android.widget.Switch;
import android.widget.Toast;

public class RawfFragment extends Fragment {

    private EditText edtMegnev;
    private EditText edtCarb;

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
        edtCarb = view.findViewById(R.id.rawCarb);

        final Switch swFlour = view.findViewById(R.id.rawFluor);
        final Switch swMilk = view.findViewById(R.id.rawMilk);
        final Switch swMeat = view.findViewById(R.id.rawMeat);

        Button btnAdd = view.findViewById(R.id.rawAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_params()) {
                    //Alert Dialog ami megkérdezi hogy biztosan hozzáadjuk e
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                    a_builder.setMessage("Do you want to add this Ingredient?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AddProducts uj = new AddProducts(edtMegnev.getText().toString(), Integer.parseInt(edtCarb.getText().toString()), swFlour.isChecked(), swMilk.isChecked(), swMeat.isChecked());
                            if (uj.add_raw()) {
                                //hozzáadás után visszalépünk az előző képernyőre
                                Fragment fragm = new ProductTypeFragment();
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.mainframeplace, fragm);
                                ft.commit();
                                Toast.makeText(getActivity().getApplicationContext(), "Added to database", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Failed to add to the database", Toast.LENGTH_SHORT).show();
                            }
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
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill Name and Carbohydrate fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //ellenörzi hogy nev és szénhidrát mezők ki vannak e töltve
    private boolean check_params() {
        boolean ok = true;
        if (edtMegnev.getText().toString().isEmpty() || edtCarb.getText().toString().isEmpty()) {
            ok = false;
        }
        return ok;
    }
}
