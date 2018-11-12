package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.Firebase;

import java.util.List;

public class StorageAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> storageList;
    private TextView txtName;

    public StorageAdapter(Context context, int resource, List<AddProducts> storageList) {
        super(context, resource, storageList);
        this.context = context;
        this.resource = resource;
        this.storageList = storageList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final AddProducts storageItem = storageList.get(position);

        txtName = view.findViewById(R.id.storageitemTxtName);
        final TextView txtUnit = view.findViewById(R.id.storageitemTxtUnit);
        final EditText edtQuantity = view.findViewById(R.id.storageitemEdtQuantity);
        Button btnMod = view.findViewById(R.id.storageitemBtnMod);
        Button btnDelete = view.findViewById(R.id.storageitemBtnDelete);
        edtQuantity.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        String seged = " ";
        if (storageItem.getUnit()) {
            seged = " KG";
        } else if (!storageItem.getUnit()) {
            seged = " Liter";
        }
        txtName.setText(storageItem.getMegnevezes());
        edtQuantity.setText(storageItem.getQuantity() + "");
        txtUnit.setText(seged);

        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtQuantity.isEnabled()) {
                    Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage").child(storageItem.getMegnevezes()).child("quantity");
                    if (!edtQuantity.getText().toString().isEmpty()) {
                        if (Double.parseDouble(edtQuantity.getText().toString()) > 0) {
                            ref.setValue(Double.parseDouble(edtQuantity.getText().toString()));
                            Toast.makeText(getContext(), storageItem.getMegnevezes() + "quantity has changed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Please fill with number higher than zero.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please fill with number higher than zero.", Toast.LENGTH_SHORT).show();
                    }

                    edtQuantity.setEnabled(false);
                } else {
                    edtQuantity.setEnabled(true);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage").child(storageItem.getMegnevezes());
                ref.removeValue();
                storageList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
