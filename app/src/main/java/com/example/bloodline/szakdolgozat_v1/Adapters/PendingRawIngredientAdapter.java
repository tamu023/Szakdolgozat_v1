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

import com.example.bloodline.szakdolgozat_v1.Classes.RawIngredient;
import com.example.bloodline.szakdolgozat_v1.R;

import java.util.List;

public class PendingRawIngredientAdapter extends ArrayAdapter<RawIngredient> {
    private Context context;
    private int resource;
    private List<RawIngredient> rawIngredientList;

    public PendingRawIngredientAdapter(Context context, int resource, List<RawIngredient> rawIngredientList) {
        super(context, resource, rawIngredientList);
        this.context = context;
        this.resource = resource;
        this.rawIngredientList = rawIngredientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final RawIngredient rawIngredient = rawIngredientList.get(position);

        TextView txtName = view.findViewById(R.id.itmPenRawTxtName);
        TextView txtKcal = view.findViewById(R.id.itmPenRawTxtKcal);
        TextView txtFluor = view.findViewById(R.id.itmPenRawTxtFluor);
        TextView txtMilk = view.findViewById(R.id.itmPenRawTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmPenRawTxtMeat);
        Button btnMod = view.findViewById(R.id.itmPenRawBtnMod);
        Button btnAccept = view.findViewById(R.id.itmPenRawBtnAccept);
        Button btnDecline = view.findViewById(R.id.itmPenRawBtnDecline);

        txtName.setText(rawIngredient.getIngredientName());
        txtKcal.setText(rawIngredient.getCarbohydrate() + " Kcal");
        if (rawIngredient.getFlour()) {
            txtFluor.setBackgroundColor(0xFFFF4A4D);
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

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
