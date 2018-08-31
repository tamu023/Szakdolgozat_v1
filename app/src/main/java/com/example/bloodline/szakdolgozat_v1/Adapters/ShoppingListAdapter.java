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

public class ShoppingListAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> shoppingList;

    public ShoppingListAdapter(@NonNull Context context, int resource, List<AddProducts> shoppingList) {
        super(context, resource, shoppingList);
        this.context = context;
        this.resource = resource;
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final AddProducts shopItem = shoppingList.get(position);

        final String unit;
        if (shopItem.getUnit()) {
            unit = "KG";
        } else {
            unit = "L";
        }

        TextView txtName = view.findViewById(R.id.itmShpTxtName);
        final TextView txtQuantity = view.findViewById(R.id.itmShpTxtQuantity);
        Button btnCancel = view.findViewById(R.id.itmShpBtnCancel);
        Button btnSub = view.findViewById(R.id.itmShpBtnSub);
        Button btnAdd = view.findViewById(R.id.itmShpBtnAdd);
        Button btnAccept = view.findViewById(R.id.itmShpBtnAccept);

        txtName.setText(shopItem.getMegnevezes());
        txtQuantity.setText(shopItem.getQuantity() + " " + unit);
        //TODO befejezni a gombokat
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopItem.getQuantity() > 1) {
                    shopItem.setQuantity(shopItem.getQuantity() - 1);
                    txtQuantity.setText(shopItem.getQuantity() + " " + unit);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopItem.setQuantity(shopItem.getQuantity() + 1);
                txtQuantity.setText(shopItem.getQuantity() + " " + unit);
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
