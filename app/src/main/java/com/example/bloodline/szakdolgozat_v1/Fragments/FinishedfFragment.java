package com.example.bloodline.szakdolgozat_v1.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bloodline.szakdolgozat_v1.Adapters.FinishedIngredientAdapter;
import com.example.bloodline.szakdolgozat_v1.Adapters.FinishedIngredientRawAdapter;
import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
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
        //TODO item_finishedingredientraw és item_finishedingredient elkészítése
        Firebase ref = new Firebase(Global_Vars.rawProdRef);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    rawingredientList.add(new AddProducts(elsoszint.getKey(), (boolean) elsoszint.child("flour").getValue(), (boolean) elsoszint.child("milk").getValue(), (boolean) elsoszint.child("meat").getValue(), (boolean) elsoszint.child("unit").getValue()));
                    FinishedIngredientRawAdapter adapter = new FinishedIngredientRawAdapter(getActivity().getApplicationContext(), R.layout.item_finishedingredientraw, rawingredientList);
                    rawingredientListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //TODO callbackel vagy valamilyen módon megoldani hogy a FinishedIngredientRawAdapterben az IngerdientListhez hozzáadott itemet megjelenísük a listájában

    }

    public static List<AddProducts> getIngredientList() {
        return ingredientList;
    }
}
