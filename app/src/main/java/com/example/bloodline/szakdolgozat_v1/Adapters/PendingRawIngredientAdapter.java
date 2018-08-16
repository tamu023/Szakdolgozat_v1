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
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

public class PendingRawIngredientAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> rawIngredientList;

    private boolean exist;

    public PendingRawIngredientAdapter(Context context, int resource, List<AddProducts> rawIngredientList) {
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

        TextView txtName = view.findViewById(R.id.itmPenRawTxtName);
        TextView txtFlour = view.findViewById(R.id.itmPenRawTxtFluor);
        TextView txtMilk = view.findViewById(R.id.itmPenRawTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmPenRawTxtMeat);
        Button btnMod = view.findViewById(R.id.itmPenRawBtnMod);
        Button btnAccept = view.findViewById(R.id.itmPenRawBtnAccept);
        Button btnDecline = view.findViewById(R.id.itmPenRawBtnDecline);

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

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exist = false;
                Firebase ref = new Firebase(Global_Vars.rawpendingProdRef);
                ref.child(rawIngredient.getMegnevezes()).removeValue();
                ref = new Firebase(Global_Vars.rawProdRef);
                final Firebase finalRef = ref;
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            if (elsoszint.getKey().equals(rawIngredient.getMegnevezes())) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            AddProducts uj = new AddProducts(rawIngredient.getMegnevezes(), rawIngredient.getFlour(), rawIngredient.getMilk(), rawIngredient.getMeat(), rawIngredient.getUnit());
                            finalRef.child(rawIngredient.getMegnevezes()).setValue(uj);
                        }
                        rawIngredientList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase(Global_Vars.rawpendingProdRef);
                ref.child(rawIngredient.getMegnevezes()).removeValue();
                rawIngredientList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
