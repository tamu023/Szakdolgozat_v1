package com.example.bloodline.szakdolgozat_v1;

import android.arch.core.util.Function;

import com.firebase.client.Firebase;

public class RegLog {
    //nem lehetnek privát változók mert a Firebaseba való beíráskor nem fog működni
    String email;
    String name;
    Boolean cukorbetegseg;
    Boolean liszterzekenyseg;
    Boolean laktozerzekenyseg;
    Long weight;
    Long height;
    Boolean gender; //true = man, false = woman
    Double bmiindex;
    Boolean acctype; //true = admin, false = use

    public RegLog(String email, String name, Boolean cukorbetegseg, Boolean liszterzekenyseg, Boolean laktozerzekenyseg, Long weight, Long height, Boolean gender, Double bmiindex, Boolean acctype) {
        this.email = email;
        this.name = name;
        this.cukorbetegseg = cukorbetegseg;
        this.liszterzekenyseg = liszterzekenyseg;
        this.laktozerzekenyseg = laktozerzekenyseg;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.bmiindex = bmiindex;
        this.acctype = acctype;
    }

    //adatbázisba beírás
    public void write_database() {
        Firebase ref = new Firebase(Global_Vars.usersRef);
        //acctypenak regisztrációkor mindig falset ír de el lesz bírálva amennyiben a felhasználó Admin jogot szeretne
        RegLog uj = new RegLog(email, name, cukorbetegseg, liszterzekenyseg, laktozerzekenyseg, weight, height, gender, bmiindex, false);
        if (acctype) {
            Functions.setAcctype(false);
            Firebase aref = new Firebase(Global_Vars.pendingUserRef);
            aref.child(Functions.getUID()).setValue(Functions.getName());
        }
        ref.child(Functions.getUID()).setValue(uj);
    }
}
