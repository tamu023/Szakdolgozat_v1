package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bloodline.szakdolgozat_v1.Adapters.FinishedIngredientAdapter;
import com.example.bloodline.szakdolgozat_v1.Adapters.FinishedIngredientRawAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class FinishedfFragment extends Fragment {

    private ListView ingredientListView;
    private ListView rawingredientListView;
    private static List<AddProducts> ingredientList;
    private List<AddProducts> rawingredientList;
    private EditText edtMegnevezes;
    private EditText edtCarb;
    private EditText edtPrepTime;
    private EditText edtRecipe;
    private Firebase finref;
    private boolean exist;
    private boolean milk;
    private boolean meat;
    private boolean flour;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finishedf, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //TODO folytatni a kinézet kialakítását
        super.onViewCreated(view, savedInstanceState);

        Button btnAdd = view.findViewById(R.id.finBtnAdd);
        edtMegnevezes = view.findViewById(R.id.finName);
        edtCarb = view.findViewById(R.id.finCarb);
        edtPrepTime = view.findViewById(R.id.finPrepTime);
        edtRecipe = view.findViewById(R.id.finRecept);
        ingredientListView = view.findViewById(R.id.finlistIngredient);
        rawingredientListView = view.findViewById(R.id.finlistRaw);

        ingredientList = new ArrayList<>();
        rawingredientList = new ArrayList<>();

        ingredientListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        rawingredientListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        Firebase ref = new Firebase(Global_Vars.rawProdRef);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    rawingredientList.add(new AddProducts(elsoszint.getKey(), (boolean) elsoszint.child("flour").getValue(), (boolean) elsoszint.child("milk").getValue(), (boolean) elsoszint.child("meat").getValue(), (boolean) elsoszint.child("unit").getValue()));
                    FinishedIngredientRawAdapter adapter = new FinishedIngredientRawAdapter(getActivity().getApplicationContext(), R.layout.item_finishedingredientraw, rawingredientList, ingredientListView);
                    rawingredientListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_params()) {
                    //létezik e már a listában a termék
                    if (Functions.getAcctype()) {
                        finref = new Firebase(Global_Vars.finProdRef);
                    } else {
                        finref = new Firebase(Global_Vars.finpendingProdRef);
                    }
                    finref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            exist = false;
                            for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                                if (elsoszint.getKey().equals(edtMegnevezes.getText().toString())) {
                                    exist = true;
                                    break;
                                }
                            }
                            //miután lefutott a keresés az adatbázisban utána fut csak le a kiértékelés
                            if (!exist) {
                                //Alert Dialog ami megkérdezi hogy biztosan hozzáadjuk e ebben az esetben a getactivity().get.... helyett getcontext() kell használni
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
                                a_builder.setMessage("Do you want to add this Food?").setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //TODO tesztelni hogy miért mindig falset ad a milk meat flour ra
                                                SetParams();
                                                //lista létrehozása hogy csak két adatot tároljunk a  hozzávalókban
                                                List<FinishedFoodIngredient> hozzavalok = new ArrayList<>();
                                                //ingredientListből való átpakolás a hozzávalókba
                                                for (int i = 0; i < ingredientList.size(); i++) {
                                                    AddProducts migrate = ingredientList.get(i);
                                                    hozzavalok.add(new FinishedFoodIngredient(migrate.getMegnevezes(), migrate.getQuantity()));
                                                }
                                                FinishedFood uj = new FinishedFood(edtMegnevezes.getText().toString(), Long.parseLong(edtCarb.getText().toString()), flour, milk, meat, edtRecipe.getText().toString(), Long.parseLong(edtPrepTime.getText().toString()), hozzavalok);
                                                //adatbázisba beírás
                                                finref.child(edtMegnevezes.getText().toString()).setValue(uj);
                                                //hozzáadás után visszalépünk az előző képernyőre
                                                Fragment fragm = new ProductTypeFragment();
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction ft = fm.beginTransaction();
                                                ft.replace(R.id.mainframeplace, fragm);
                                                ft.commit();
                                                Toast.makeText(getActivity().getApplicationContext(), "Added to database", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("New Item");
                                alert.show();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "This food is already in the database", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //beállítja a milk meat flour paraméterekt
    private void SetParams() {
        for (int i = 0; i < ingredientList.size(); i++) {
            AddProducts check = ingredientList.get(i);
            if (check.getMeat()) {
                meat = true;
            }
            if (check.getFlour()) {
                flour = true;
            }
            if (check.getMilk()) {
                milk = true;
            }
        }
    }

    public static List<AddProducts> getIngredientList() {
        return ingredientList;
    }

    //ellenörzi hogy nev mező ki vannak e töltve
    private boolean check_params() {
        boolean ok = true;
        if (edtMegnevezes.getText().toString().isEmpty() || edtRecipe.getText().toString().isEmpty() || edtCarb.getText().toString().isEmpty() || edtPrepTime.getText().toString().isEmpty() || ingredientList.isEmpty()) {
            ok = false;
        }
        return ok;
    }
}
