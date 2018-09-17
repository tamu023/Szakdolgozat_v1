package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.R;

import java.util.List;

public class FinishedIngredientAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> rawIngredientList;

    public FinishedIngredientAdapter(Context context, int resource, List<AddProducts> rawIngredientList) {
        super(context, resource, rawIngredientList);
        this.context = context;
        this.resource = resource;
        this.rawIngredientList = rawIngredientList;
    }

    //TODO itemet elkészíteni és ezt befejezni
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //ebben a listában jelenítjük meg a hozzávalókat éa a mennyiségőket, innen csak törölni lehet a hozzávalót
        //item_finishedingredient az iteme
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final AddProducts rawIngredient = rawIngredientList.get(position);

        TextView txtName = view.findViewById(R.id.itmFinTxtName);
        TextView txtQuantity = view.findViewById(R.id.itmFinTxtQuantity);
        TextView txtUnit = view.findViewById(R.id.itmFinTxtUnit);
        Button btnDelete = view.findViewById(R.id.itmFinBtnDelete);

        txtName.setText(rawIngredient.getMegnevezes());
        txtQuantity.setText(rawIngredient.getQuantity() + "");
        if (rawIngredient.getUnit()) {
            txtUnit.setText("KG");
        } else {
            txtUnit.setText("L");
        }

        //TODO unitot beírni

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rawIngredientList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
