package com.example.bloodline.szakdolgozat_v1.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.R;

import java.util.List;

public class StatisticsAdapter extends ArrayAdapter<AddProducts> {

    private Context context;
    private int resource;
    private List<AddProducts> statisticList;

    public StatisticsAdapter(@NonNull Context context, int resource, List<AddProducts> statisticList) {
        super(context, resource, statisticList);
        this.context = context;
        this.resource = resource;
        this.statisticList = statisticList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final AddProducts statItem = statisticList.get(position);

        String seged = " ";
        if (statItem.getUnit()) {
            seged = " KG";
        } else if (!statItem.getUnit()) {
            seged = " Liter";
        }

        TextView txtName = view.findViewById(R.id.itmStatTxtName);
        TextView txtQty = view.findViewById(R.id.itmStatTxtQty);
        txtName.setText(statItem.getMegnevezes());
        txtQty.setText(statItem.getQuantity() + seged);

        return view;
    }
}
