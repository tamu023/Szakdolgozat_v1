package com.example.bloodline.szakdolgozat_v1;

import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AddProducts {
    String megnevezes;
    Integer carbohydrate;
    Boolean flour;
    Boolean milk;
    Boolean meat;
    String recept;
    String[] ingredients;

    //seged valtozok
    private Boolean exist = false;
    private Firebase ref;
    private Boolean siker;

    //Finished food add
    public AddProducts(String megnevezes, Integer carbohydrate, Boolean flour, Boolean milk, Boolean meat, String recept, String[] ingredients) {
        this.megnevezes = megnevezes;
        this.carbohydrate = carbohydrate;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
        this.recept = recept;
        this.ingredients = ingredients;
    }

    //Raw food add
    public AddProducts(String megnevezes, Integer carbohydrate, Boolean flour, Boolean milk, Boolean meat) {
        this.megnevezes = megnevezes;
        this.carbohydrate = carbohydrate;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
    }

    //adatbázisba beírás
    public boolean add_raw() {
        siker = false;
        //megfelelő link kiválasztása a felhasználó jogosultságától függően
        if (Functions.getAcctype()) {
            ref = new Firebase(Global_Vars.rawProdRef);
        } else {
            ref = new Firebase(Global_Vars.rawpendingProdRef);
        }
        AddProducts uj = new AddProducts(megnevezes, carbohydrate, flour, milk, meat);
        if (!check_exist(ref)) {
            ref.child(megnevezes).setValue(uj);
            siker = true;
        }
        return siker;
    }

    public boolean add_finished() {
        siker = false;
        //megfelelő link kiválasztása a felhasználó jogosultságától függően
        if (Functions.getAcctype()) {
            ref = new Firebase(Global_Vars.finProdRef);
        } else {
            ref = new Firebase(Global_Vars.finpendingProdRef);
        }
        AddProducts uj = new AddProducts(megnevezes, carbohydrate, flour, milk, meat);
        if (!check_exist(ref)) {
            ref.child(megnevezes).setValue(uj);
            siker = true;
        }
        return siker;
    }

    //ellenörzés hogy létezik e már az item az adatbázisban
    private boolean check_exist(Firebase ref) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    if (elsoszint.getValue().toString().equals(megnevezes)) {
                        exist = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return exist;
    }
}
