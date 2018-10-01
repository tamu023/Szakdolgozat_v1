package com.example.bloodline.szakdolgozat_v1.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewMealAdapter extends ArrayAdapter<FinishedFood> {
    private Context context;
    private int resource;
    private List<AddProducts> rawFoodList;
    private List<FinishedFood> newmealList;
    private List<AddProducts> storageList;
    private List<AddProducts> shoppingList;
    private List<AddProducts> alterStorageList;
    private Firebase ref;
    private boolean exist;

    public NewMealAdapter(@NonNull Context context, int resource, List<FinishedFood> newmealList, List<AddProducts> rawFoodList) {
        super(context, resource, newmealList);
        this.context = context;
        this.resource = resource;
        this.newmealList = newmealList;
        this.rawFoodList = rawFoodList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
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

        txtName.setText(newmealItem.getFoodname());
        txtKcal.setText(newmealItem.getCarb() + " Kcal");
        txtPrep.setText(newmealItem.getPreptime() + " Min");
        if (newmealItem.getFlour()) {
            txtFlour.setBackgroundColor(0xFFFF4A4D);
        }
        if (newmealItem.getMilk()) {
            txtMilk.setBackgroundColor(0xFFFF4A4D);
        }
        if (newmealItem.getMeat()) {
            txtMeat.setBackgroundColor(0xFFFF4A4D);
        }

        //TODO TESZTELNI, Adagot kitalálni hogyan hozzuk be az adapterbe mert minidg 1 et hozunk
        //gomb lenyomására ellenőrizzük a raktárat hogy van e elegendő alapanyag az elkészítéshez és alertDialogban megkérdezzük hogy kívánja e elkészíteni, ha nincsen akkor AlertDialogban megkérdezzük hogy kívánja e hozzáadni a bevásárló listához a termékeket
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingList = new ArrayList<>();
                alterStorageList = new ArrayList<>();
                //adag beolvasása
                SharedPreferences prefs = parent.getContext().getSharedPreferences("seged", Context.MODE_PRIVATE);
                final int adag = prefs.getInt("Adag", 1);
                ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //storagebol listába kiolvasás (AddProducts)
                        storageList = new ArrayList<>();
                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            storageList.add(new AddProducts((String) elsoszint.child("megnevezes").getValue(), (boolean) elsoszint.child("unit").getValue(), (double) elsoszint.child("quantity").getValue()));
                        }
                        //shopping list feltöltése ha valamilyen hozzávalóból hiányzik, amennyiben megvan a szükséges termékből a megfelelő mennyiség letároljuk egy új alternatív storageban, hogy később ezzel írjuk felül a felhasznló storageját
                        boolean ok;
                        for (int i = 0; i < newmealItem.getIngredientList().size(); i++) {
                            ok = false;
                            for (int j = 0; j < storageList.size(); j++) {
                                if (newmealItem.getIngredientList().get(i).getMegnevezes().equals(storageList.get(j).getMegnevezes())) {
                                    if (adag * newmealItem.getIngredientList().get(i).getMennyiseg() <= storageList.get(j).getQuantity()) {
                                        alterStorageList.add(new AddProducts(storageList.get(j).getMegnevezes(), storageList.get(j).getUnit(), storageList.get(j).getQuantity() - adag * newmealItem.getIngredientList().get(i).getMennyiseg()));
                                        ok = true;
                                        break;
                                    } else {
                                        shoppingList.add(new AddProducts(storageList.get(j).getMegnevezes(), storageList.get(j).getUnit(), (storageList.get(j).getQuantity() - adag * newmealItem.getIngredientList().get(i).getMennyiseg()) * -1));
                                        ok = true;
                                        break;
                                    }
                                }
                            }
                            if (!ok) {
                                for (int j = 0; j < rawFoodList.size(); j++) {
                                    if (rawFoodList.get(j).getMegnevezes().equals(newmealItem.getIngredientList().get(i).getMegnevezes())) {
                                        shoppingList.add(new AddProducts(newmealItem.getIngredientList().get(i).getMegnevezes(), rawFoodList.get(j).getUnit(), adag * newmealItem.getIngredientList().get(i).getMennyiseg()));
                                        break;
                                    }
                                }
                            }
                        }
                        if (shoppingList.isEmpty()) {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(parent.getContext());
                            a_builder.setMessage("You have enough ingredient, Do you want to prepare this meal?").setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (int i = 0; i < alterStorageList.size(); i++) {
                                                        AddProducts change = alterStorageList.get(i);
                                                        ref.child(change.getMegnevezes()).setValue(change);
                                                        Functions.cleanPath(change.getMegnevezes(), "storage");
                                                    }
                                                    dialog.cancel();
                                                }

                                                //TODO fragmens készítése amelyen egész oldalon a recept látható és oda betöltés
                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.setTitle("Prepare Meal");
                            alert.show();
                        } else {
                            AlertDialog.Builder a_builder = new AlertDialog.Builder(parent.getContext());
                            a_builder.setMessage("You don't have enough ingredient, Do you want to add these to the Shopping List?").setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("shopping list");
                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (int i = 0; i < shoppingList.size(); i++) {
                                                        exist = false;
                                                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                                                            if (elsoszint.getKey().equals(shoppingList.get(i).getMegnevezes())) {
                                                                exist = true;
                                                                break;
                                                            }
                                                        }
                                                        if (exist) {
                                                            AddProducts add = shoppingList.get(i);
                                                            double currQuantity = (double) dataSnapshot.child(add.getMegnevezes()).child("quantity").getValue();
                                                            ref.child(add.getMegnevezes()).child("quantity").setValue(currQuantity + add.getQuantity());
                                                        } else {
                                                            AddProducts add = shoppingList.get(i);
                                                            ref.child(add.getMegnevezes()).setValue(add);
                                                            Functions.cleanPath(add.getMegnevezes(), "shopping list");
                                                        }
                                                    }
                                                    dialog.cancel();
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = a_builder.create();
                            alert.setTitle("Shopping List");
                            alert.show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
        return view;
    }
}
