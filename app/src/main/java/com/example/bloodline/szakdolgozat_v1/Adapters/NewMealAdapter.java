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

import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.R;

import java.util.List;

public class NewMealAdapter extends ArrayAdapter<FinishedFood> {
    private Context context;
    private int resource;
    private List<FinishedFood> newmealList;

    //TODO befejezni az adaptert és item_new_mealt
    public NewMealAdapter(@NonNull Context context, int resource, List<FinishedFood> newmealList) {
        super(context, resource, newmealList);
        this.context = context;
        this.resource = resource;
        this.newmealList = newmealList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final FinishedFood newmealItem = newmealList.get(position);

        TextView txtName = view.findViewById(R.id.itmNewMTxtName);
        TextView txtKcal = view.findViewById(R.id.itmNewMTxtKcal);
        TextView txtPrep = view.findViewById(R.id.itmNewMTxtPrepT);
        TextView txtMilk = view.findViewById(R.id.itmNewMTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmNewMTxtMeat);
        TextView txtFlour = view.findViewById(R.id.itmNewMTxtFlour);
        Button btnOk = view.findViewById(R.id.itmNewMBtnOk);

        //gomb lenyomására ellenőrizzük a raktárat hogy van e elegendő alapanyag az elkészítéshez és alertDialogban megkérdezzük hogy kívánja e elkészíteni, ha nincsen akkor AlertDialogban megkérdezzük hogy kívánja e hozzáadni a bevásárló listához a termékeket
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
