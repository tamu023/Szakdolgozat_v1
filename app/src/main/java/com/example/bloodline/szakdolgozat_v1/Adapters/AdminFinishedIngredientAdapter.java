package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;

import java.util.List;

public class AdminFinishedIngredientAdapter extends ArrayAdapter<FinishedFood> {
    private Context context;
    private int resource;
    private List<FinishedFood> finishedFoodList;

    public AdminFinishedIngredientAdapter(Context context, int resource, List<FinishedFood> finishedFoodList) {
        super(context, resource, finishedFoodList);
        this.context = context;
        this.resource = resource;
        this.finishedFoodList = finishedFoodList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        //TODO elkészíteni az adatptert

        return view;
    }
}
