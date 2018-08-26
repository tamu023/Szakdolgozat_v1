package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.Fragments.StorageItemModFragment;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
        TextView txtUnit = view.findViewById(R.id.storageitemTxtUnit);
        Button btnMod = view.findViewById(R.id.storageitemBtnMod);
        Button btnDelete = view.findViewById(R.id.storageitemBtnDelete);

        String seged = " ";
        if (storageItem.getUnit()) {
            seged = " KG";
        } else if (!storageItem.getUnit()) {
            seged = " Liter";
        }
        txtName.setText(storageItem.getMegnevezes());
        txtUnit.setText(storageItem.getQuantity() + seged);
        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = parent.getContext().getSharedPreferences("seged", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Name", storageItem.getMegnevezes());
                editor.putBoolean("Unit",storageItem.getUnit());
                //Stringként rakjuk bele mert Doublet nem lehet Shared Preferencesbe
                editor.putString("Quantity", String.valueOf(storageItem.getQuantity()));
                editor.apply();
                Fragment startfragment = new StorageItemModFragment();
                Context context = parent.getContext();
                FragmentManager fm = ((Activity) context).getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainframeplace, startfragment);
                ft.commit();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage").child(txtName.getText().toString());
                ref.removeValue();
                storageList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
