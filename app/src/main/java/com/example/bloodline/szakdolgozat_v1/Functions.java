package com.example.bloodline.szakdolgozat_v1;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Functions {
    //nem szabad int-et használni mert Firebase long-ként tárolja a számokat
    private static String email;
    private static String UID;
    private static FirebaseUser user;
    private static FirebaseAuth mAuth;
    private static String name;
    private static boolean cukorbetegseg;
    private static boolean liszterzekenyseg;
    private static boolean laktozerzekenyseg;
    private static long weight;
    private static long height;
    private static boolean gender; //true = man, false = woman
    private static double bmiindex;
    private static boolean acctype; //true = admin, false = use

    public static String getEmail() {
        return email;
    }

    public static String getUID() {
        return UID;
    }

    public static FirebaseUser getUser() {
        return user;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static String getName() {
        return name;
    }

    public static boolean getCukorbetegseg() {
        return cukorbetegseg;
    }

    public static boolean getLiszterzekenyseg() {
        return liszterzekenyseg;
    }

    public static boolean getLaktozerzekenyseg() {
        return laktozerzekenyseg;
    }

    public static long getWeight() {
        return weight;
    }

    public static long getHeight() {
        return height;
    }

    public static boolean getGender() {
        return gender;
    }

    public static double getBmiindex() {
        return bmiindex;
    }

    public static boolean getAcctype() {
        return acctype;
    }

    public static void setEmail(String email) {
        Functions.email = email;
    }

    public static void setUID(String UID) {
        Functions.UID = UID;
    }

    public static void setUser(FirebaseUser user) {
        Functions.user = user;
    }

    public static void setmAuth(FirebaseAuth mAuth) {
        Functions.mAuth = mAuth;
    }

    public static void setName(String name) {
        Functions.name = name;
    }

    public static void setCukorbetegseg(boolean cukorbetegseg) {
        Functions.cukorbetegseg = cukorbetegseg;
    }

    public static void setLiszterzekenyseg(boolean liszterzekenyseg) {
        Functions.liszterzekenyseg = liszterzekenyseg;
    }

    public static void setLaktozerzekenyseg(boolean laktozerzekenyseg) {
        Functions.laktozerzekenyseg = laktozerzekenyseg;
    }

    public static void setWeight(long weight) {
        Functions.weight = weight;
    }

    public static void setHeight(long height) {
        Functions.height = height;
    }

    public static void setGender(boolean gender) {
        Functions.gender = gender;
    }

    public static void setBmiindex(double bmiindex) {
        Functions.bmiindex = bmiindex;
    }

    public static void setAcctype(boolean acctype) {
        Functions.acctype = acctype;
    }

    public static void clearAccdata() {
        name = null;
        email = null;
        UID = null;
        user = null;
        cukorbetegseg = false;
        liszterzekenyseg = false;
        laktozerzekenyseg = false;
        weight = 0;
        height = 0;
        gender = false;
        bmiindex = 0;
        acctype = false;

    }

    public static void readAccdata() {
        Firebase ref = new Firebase(Global_Vars.usersRef);
        //Firebase rulsenél auth read = true nem olvassa be az adatokat
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elsoszint : dataSnapshot.getChildren()) {
                    if (elsoszint.getKey().equals(Functions.getUID())) {
                        for (DataSnapshot masodikszint : elsoszint.getChildren()) {
                            switch (masodikszint.getKey()) {
                                case "name":
                                    Functions.setName(masodikszint.getValue().toString());
                                    break;
                                case "email":
                                    Functions.setEmail(masodikszint.getValue().toString());
                                    break;
                                case "cukorbetegseg":
                                    Functions.setCukorbetegseg((boolean) masodikszint.getValue());
                                    break;
                                case "liszterzekenyeg":
                                    Functions.setLiszterzekenyseg((boolean) masodikszint.getValue());
                                    break;
                                case "weight":
                                    Functions.setWeight((long) masodikszint.getValue());
                                    break;
                                case "height":
                                    Functions.setHeight((long) masodikszint.getValue());
                                    break;
                                case "gender":
                                    Functions.setGender((boolean) masodikszint.getValue());
                                    break;
                                case "bmiindex":
                                    Functions.setBmiindex((double) masodikszint.getValue());
                                    break;
                                case "laktozerzekenyseg":
                                    Functions.setLaktozerzekenyseg((boolean) masodikszint.getValue());
                                    break;
                                case "acctype":
                                    Functions.setAcctype((boolean) masodikszint.getValue());
                                    break;
                                default:
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
