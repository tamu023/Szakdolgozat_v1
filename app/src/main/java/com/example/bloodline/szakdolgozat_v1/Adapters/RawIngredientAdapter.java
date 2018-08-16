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

public class RawIngredientAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> rawIngredientList;


    public RawIngredientAdapter(Context context, int resource, List<AddProducts> rawIngredientList) {
        super(context, resource, rawIngredientList);
        this.context = context;
        this.resource = resource;
        this.rawIngredientList = rawIngredientList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final AddProducts rawIngredient = rawIngredientList.get(position);

        TextView txtName = view.findViewById(R.id.itmRawTxtName);
        TextView txtFlour = view.findViewById(R.id.itmRawTxtFluor);
        TextView txtMilk = view.findViewById(R.id.itmRawTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmRawTxtMeat);
        Button btnMod = view.findViewById(R.id.itmRawBtnMod);

        txtName.setText(rawIngredient.getMegnevezes());
        if (rawIngredient.getFlour()) {
            txtFlour.setBackgroundColor(0xFFFF4A4D);
        }
        if (rawIngredient.getMilk()) {
            txtMilk.setBackgroundColor(0xFFFF4A4D);
        }
        if (rawIngredient.getMeat()) {
            txtMeat.setBackgroundColor(0xFFFF4A4D);
        }
        //TODO a gombok beállítása
        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
