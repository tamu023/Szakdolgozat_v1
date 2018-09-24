package com.example.bloodline.szakdolgozat_v1.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.example.bloodline.szakdolgozat_v1.Adapters.ShoppingListItemAddAdapter;
import com.example.bloodline.szakdolgozat_v1.Adapters.StorageItemAddAdapter;
import com.example.bloodline.szakdolgozat_v1.Classes.AddProducts;
import com.example.bloodline.szakdolgozat_v1.Classes.Global_Vars;
import com.example.bloodline.szakdolgozat_v1.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListItemAddFragment extends Fragment {

    private List<AddProducts> shoppingAddItemList;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list_item_add, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.shoppingListView);
        shoppingAddItemList = new ArrayList<>();

        Firebase ref = new Firebase(Global_Vars.rawProdRef);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    shoppingAddItemList.add(new AddProducts(elsoszint.getKey(), (boolean) elsoszint.child("unit").getValue()));
                    ShoppingListItemAddAdapter adapter = new ShoppingListItemAddAdapter(getActivity().getApplicationContext(), R.layout.item_addstorageingredient, shoppingAddItemList);
                    listView.setAdapter(adapter);
                }
                closeKeyboard();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
