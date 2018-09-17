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
import android.widget.ListView;
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
    private ListView ingredientListView;

    public FinishedIngredientRawAdapter(Context context, int resource, List<AddProducts> rawIngredientList, ListView ingredientListView) {
        super(context, resource, rawIngredientList);
        this.context = context;
        this.resource = resource;
        this.rawIngredientList = rawIngredientList;
        this.ingredientListView = ingredientListView;
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
        final Spinner spnUnit = view.findViewById(R.id.itmFinRawSpnUnit);
        Button btnOk = view.findViewById(R.id.itmFinRawBtnOk);

        txtName.setText(rawIngredient.getMegnevezes());

        //TODO egyedi spinner itemet készíteni neki mert től nagy a betüméret
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

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtQuantity.getText().toString().isEmpty() && !edtQuantity.getText().toString().equals("0")) {
                    int existInt = -1;
                    double exchangedQuantity = Functions.calcExchangeUnit(unit, spnUnit.getSelectedItem().toString(), edtQuantity.getText().toString());
                    ingredientList = FinishedfFragment.getIngredientList();
                    //ellenörzés hogy benne van e a hozzáadni kívánt elem a listában, ha igen módosítunk az értékén
                    for (int i = 0; i < ingredientList.size(); i++) {
                        AddProducts check = ingredientList.get(i);
                        if (check.getMegnevezes().equals(txtName.getText().toString())) {
                            existInt = i;
                            break;
                        }
                    }
                    if (existInt != -1) {
                        AddProducts check = ingredientList.get(existInt);
                        check.setQuantity(check.getQuantity() + exchangedQuantity);
                    } else {
                        ingredientList.add(new AddProducts(txtName.getText().toString(), unit, exchangedQuantity));
                    }
                    //végigmegyünk újra a listán miután beleraktuk az új elemet és újra kiiratjuk
                    for (int i = 0; i < ingredientList.size(); i++) {
                        edtQuantity.setText(null);
                        FinishedIngredientAdapter adapter = new FinishedIngredientAdapter(context, R.layout.item_finishedingredient, ingredientList);
                        ingredientListView.setAdapter(adapter);
                    }
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
