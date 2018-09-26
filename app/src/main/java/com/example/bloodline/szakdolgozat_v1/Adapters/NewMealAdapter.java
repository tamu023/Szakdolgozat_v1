package com.example.bloodline.szakdolgozat_v1.Adapters;

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
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFoodIngredient;
import com.example.bloodline.szakdolgozat_v1.Classes.Functions;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

public class NewMealAdapter extends ArrayAdapter<FinishedFood> {
    private Context context;
    private int resource;
    private List<FinishedFood> newmealList;
    private List<FinishedFoodIngredient> ingredientList;
    private List<AddProducts> storageList;
    private List<AddProducts> shoppingList;
    private List<AddProducts> alterStorageList;

    public NewMealAdapter(@NonNull Context context, int resource, List<FinishedFood> newmealList) {
        super(context, resource, newmealList);
        this.context = context;
        this.resource = resource;
        this.newmealList = newmealList;
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

        //ingredientList beolvasása
        Firebase ref = new Firebase(Global_Vars.finProdRef).child(newmealItem.getFoodname()).child("ingredientList");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    ingredientList.add(new FinishedFoodIngredient((String) elsoszint.child("megnevezes").getValue(), (double) elsoszint.child("mennyiseg").getValue()));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //TODO TESZTELNI
        //gomb lenyomására ellenőrizzük a raktárat hogy van e elegendő alapanyag az elkészítéshez és alertDialogban megkérdezzük hogy kívánja e elkészíteni, ha nincsen akkor AlertDialogban megkérdezzük hogy kívánja e hozzáadni a bevásárló listához a termékeket
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adag beolvasása
                SharedPreferences prefs = parent.getContext().getSharedPreferences("seged", Context.MODE_PRIVATE);
                final int adag = prefs.getInt("Adag", 1);
                Firebase ref = new Firebase(Global_Vars.usersRef).child(Functions.getUID()).child("storage");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //storagebol listába kiolvasás (AddProducts)
                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            storageList.add(new AddProducts((String) elsoszint.child("megnevezes").getValue(), (boolean) elsoszint.child("unit").getValue(), (double) elsoszint.child("quantity").getValue()));
                        }
                        //shopping list feltöltése ha valamilyen hozzávalóból hiányzik
                        boolean ok;
                        //először a shopping listet állítjuk össze ha valami nincs meg
                        for (int i = 0; i < ingredientList.size(); i++) {
                            ok = false;
                            for (int j = 0; j < storageList.size(); j++) {
                                if (ingredientList.get(i).getMegnevezes().equals(storageList.get(j).getMegnevezes())) {
                                    if (adag * ingredientList.get(i).getMennyiseg() <= storageList.get(j).getQuantity()) {
                                        alterStorageList.add(new AddProducts(storageList.get(j).getMegnevezes(), storageList.get(j).getUnit(), storageList.get(j).getQuantity() - adag * ingredientList.get(i).getMennyiseg()));
                                        ok = true;
                                        break;
                                    } else {
                                        shoppingList.add(new AddProducts(storageList.get(j).getMegnevezes(), storageList.get(j).getUnit(), (storageList.get(j).getQuantity() - adag * ingredientList.get(i).getMennyiseg()) * -1));
                                        ok = true;
                                        break;
                                    }
                                }
                            }
                            if (!ok) {
                                //TODO kitalálni hogyan adjunk neki Unit booleant
                                //shoppingList.add(new AddProducts(ingredientList.get(i).getMegnevezes(), ingredientList.get(i).getUnit(), adag * ingredientList.get(i).getMennyiseg()));
                            }
                        }
                        if (shoppingList.isEmpty()) {
                            //alert dialog, el lehet készíteni az ételt, elkészíti?
                            //étel elkészítése alterstorage lista elemeivel átíráa a mostani storaget
                        } else {
                            //alert dialog, nincs elég hozzávaló hozzáadja a bevásárló listához?
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
