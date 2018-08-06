package com.example.bloodline.szakdolgozat_v1;

import com.firebase.client.Firebase;

public class RegLog {
    //nem lehetnek privát változók mert a Firebaseba való beíráskor nem fog működni
    String email;
    String name;
    boolean cukorbetegseg;
    boolean liszterzekenyseg;
    boolean laktozerzekenyseg;
    long weight;
    long height;
    boolean gender; //true = man, false = woman
    double bmiindex;
    boolean acctype; //true = admin, false = use

    public RegLog(String email, String name, boolean cukorbetegseg, boolean liszterzekenyseg, boolean laktozerzekenyseg, long weight, long height, boolean gender, double bmiindex, boolean acctype) {
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
    public void newUserADD() {
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

    public void currUserMOD() {
        Firebase ref = new Firebase(Global_Vars.usersRef);
        RegLog uj = new RegLog(email, name, cukorbetegseg, liszterzekenyseg, laktozerzekenyseg, weight, height, gender, bmiindex, acctype);
        ref.child(Functions.getUID()).setValue(uj);
    }
}
