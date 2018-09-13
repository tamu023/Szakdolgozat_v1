package com.example.bloodline.szakdolgozat_v1.Adapters;

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
import com.example.bloodline.szakdolgozat_v1.Fragments.FinishedfFragment;
import com.example.bloodline.szakdolgozat_v1.R;

import java.util.List;

public class FinishedIngredientRawAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> rawIngredientList;
    private static List<AddProducts> ingredientList;

    public FinishedIngredientRawAdapter(Context context, int resource, List<AddProducts> rawIngredientList) {
        super(context, resource, rawIngredientList);
        this.context = context;
        this.resource = resource;
        this.rawIngredientList = rawIngredientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        //ebből a listából választjuk ki a hozzávalókat és a mennyiségűket
        //item_finishedingredientraw az iteme
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final AddProducts rawIngredient = rawIngredientList.get(position);

        final TextView txtName = view.findViewById(R.id.itmFinRawTxtName);
        final EditText edtQuantity = view.findViewById(R.id.itmFinRawEdtQuantity);
        Spinner spnUnit = view.findViewById(R.id.itmFinRawSpnUnit);
        Button btnOk = view.findViewById(R.id.itmFinRawBtnOk);

        txtName.setText(rawIngredient.getMegnevezes());

        //TODO tesztelni
        final boolean unit = rawIngredient.getUnit();
        String liquid[] = {"L", "DL", "CL", "ML", "MKK", "KVK", "TK", "EVK", "POH", "BOG"};
        String solid[] = {"KG", "DKG", "G"};

        if (unit) {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, solid);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnUnit.setAdapter(spinnerArrayAdapter);
        } else {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, liquid);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnUnit.setAdapter(spinnerArrayAdapter);
        }

        final double exchangedQuantity = Functions.calcExchangeUnit(unit, spnUnit.getSelectedItem().toString(), edtQuantity.getText().toString());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtQuantity.getText().toString().isEmpty() && !edtQuantity.getText().toString().equals("0")) {
                    ingredientList = FinishedfFragment.getIngredientList();
                    ingredientList.add(new AddProducts(txtName.getText().toString(), unit, exchangedQuantity));
                    //TODO itt kellene a callbacket hívni amellyel frissítjük a fragmentben az ingredient listát

                } else {
                    Toast.makeText(getContext(), "Please fill the Quantity field", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public static List<AddProducts> getIngredientList() {
        return ingredientList;
    }
}
