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

import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFood;
import com.example.bloodline.szakdolgozat_v1.Classes.FinishedFoodIngredient;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminPendingFinishedIngredientAdapter extends ArrayAdapter<FinishedFood> {
    private Context context;
    private int resource;
    private List<FinishedFood> finishedFoodList;
    private String recept;
    private long prepTime;
    private Firebase ref;
    private boolean exist;

    public AdminPendingFinishedIngredientAdapter(Context context, int resource, List<FinishedFood> finishedFoodList) {
        super(context, resource, finishedFoodList);
        this.context = context;
        this.resource = resource;
        this.finishedFoodList = finishedFoodList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        final FinishedFood finishedFood = finishedFoodList.get(position);

        TextView txtName = view.findViewById(R.id.itmAdmPenFinTxtName);
        TextView txtKcal = view.findViewById(R.id.itmAdmPenFinTxtKcal);
        TextView txtFlour = view.findViewById(R.id.itmAdmPenFinTxtFlour);
        TextView txtMilk = view.findViewById(R.id.itmAdmPenFinTxtMilk);
        TextView txtMeat = view.findViewById(R.id.itmAdmPenFinTxtMeat);
        Button btnAdd = view.findViewById(R.id.itmAdmPenFinBtnOk);
        Button btnDecline = view.findViewById(R.id.itmAdmPenFinBtnDecline);

        txtName.setText(finishedFood.getFoodname());
        txtKcal.setText(finishedFood.getCarb() + "Kcal");

        setColors(finishedFood.getFlour(), txtFlour);
        setColors(finishedFood.getMeat(), txtMeat);
        setColors(finishedFood.getMilk(), txtMilk);
        //TODO TESZTELNI
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exist = false;
                ref = new Firebase(Global_Vars.finProdRef);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                            if (elsoszint.getKey().equals(finishedFood.getFoodname())) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            ref = new Firebase(Global_Vars.finpendingProdRef);
                            final List<FinishedFoodIngredient> hozzavalok = new ArrayList<>();
                            //recept és prep time kiolvasása
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    recept = (String) dataSnapshot.child("recipe").getValue();
                                    prepTime = (long) dataSnapshot.child("preptime").getValue();
                                    ref = ref.child(finishedFood.getFoodname()).child("ingredientList");
                                    //hozzávalók kiolvasása
                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                                                hozzavalok.add(new FinishedFoodIngredient((String) elsoszint.child("megnevezes").getValue(), (double) elsoszint.child("mennyiseg").getValue()));
                                            }
                                            //adatbázishoz hozzáadás
                                            FinishedFood newfood = new FinishedFood(finishedFood.getFoodname(), finishedFood.getCarb(), finishedFood.getFlour(), finishedFood.getMilk(), finishedFood.getMeat(), recept, prepTime, hozzavalok);
                                            ref = new Firebase(Global_Vars.finProdRef);
                                            ref.child(finishedFood.getFoodname()).setValue(newfood);
                                            //adatbázisból régi törlése
                                            ref = new Firebase(Global_Vars.finpendingProdRef);
                                            ref.child(finishedFood.getFoodname()).removeValue();
                                            finishedFoodList.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(getContext(), "Successfully Confirmed.", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(FirebaseError firebaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "This food is already in the database", Toast.LENGTH_SHORT).show();
                        }
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
                Firebase ref = new Firebase(Global_Vars.finpendingProdRef);
                ref.child(finishedFood.getFoodname()).removeValue();
                finishedFoodList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "Deleted from Pending Database.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setColors(boolean bl, TextView txt) {
        if (bl) {
            txt.setBackgroundColor(0xFF5EFF66);
        }
    }
}
