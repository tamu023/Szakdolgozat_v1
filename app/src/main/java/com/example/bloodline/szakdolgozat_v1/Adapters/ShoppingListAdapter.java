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
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<AddProducts> {
    private Context context;
    private int resource;
    private List<AddProducts> shoppingList;
    private Firebase ref;
    private double quantity;

    public ShoppingListAdapter(@NonNull Context context, int resource, List<AddProducts> shoppingList) {
        super(context, resource, shoppingList);
        this.context = context;
        this.resource = resource;
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("shopping list");
                ref.child(shopItem.getMegnevezes()).removeValue();
                shoppingList.remove(position);
                notifyDataSetChanged();
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
                ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("shopping list");
                ref.child(shopItem.getMegnevezes()).removeValue();
                ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        quantity = 0;
                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            if (elsoszint.getKey().equals(shopItem.getMegnevezes())) {
                                quantity = (double) elsoszint.child("quantity").getValue();
                            }
                        }
                        if (quantity != 0) {
                            ref.child(shopItem.getMegnevezes()).child("quantity").setValue(shopItem.getQuantity() + quantity);
                        } else {
                            AddProducts uj = new AddProducts(shopItem.getMegnevezes(),shopItem.getUnit(),shopItem.getQuantity());
                            ref.child(shopItem.getMegnevezes()).setValue(uj);
                        }
                        //törli az olyan childokat amelyek fölöslegesen vannak benne
                        Functions.cleanPath(shopItem.getMegnevezes(),"shopping list");

                        Toast.makeText(getContext(), shopItem.getMegnevezes() + "added to Storage", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                shoppingList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
