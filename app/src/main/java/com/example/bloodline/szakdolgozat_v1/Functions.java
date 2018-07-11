package com.example.bloodline.szakdolgozat_v1;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Functions {
    private static String email;
    private static String UID;
    private static FirebaseUser user;
    private static FirebaseAuth mAuth;
    private static String name;
    private static Boolean cukorbetegseg;
    private static Boolean liszterzekenyseg;
    private static Boolean laktozerzekenyseg;
    private static Integer weight;
    private static Integer height;
    private static Boolean gender; //true = man, false = woman
    private static Double bmiindex;
    private static Boolean acctype; //true = admin, false = use

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

    public static Boolean getCukorbetegseg() {
        return cukorbetegseg;
    }

    public static Boolean getLiszterzekenyseg() {
        return liszterzekenyseg;
    }

    public static Boolean getLaktozerzekenyseg() {
        return laktozerzekenyseg;
    }

    public static Integer getWeight() {
        return weight;
    }

    public static Integer getHeight() {
        return height;
    }

    public static Boolean getGender() {
        return gender;
    }

    public static Double getBmiindex() {
        return bmiindex;
    }

    public static Boolean getAcctype() {
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

    public static void setCukorbetegseg(Boolean cukorbetegseg) {
        Functions.cukorbetegseg = cukorbetegseg;
    }

    public static void setLiszterzekenyseg(Boolean liszterzekenyseg) {
        Functions.liszterzekenyseg = liszterzekenyseg;
    }

    public static void setLaktozerzekenyseg(Boolean laktozerzekenyseg) {
        Functions.laktozerzekenyseg = laktozerzekenyseg;
    }

    public static void setWeight(Integer weight) {
        Functions.weight = weight;
    }

    public static void setHeight(Integer height) {
        Functions.height = height;
    }

    public static void setGender(Boolean gender) {
        Functions.gender = gender;
    }

    public static void setBmiindex(Double bmiindex) {
        Functions.bmiindex = bmiindex;
    }

    public static void setAcctype(Boolean acctype) {
        Functions.acctype = acctype;
    }

    //TODO Letesztelni miért nem működik
    public static void readAccdata() {
        Firebase ref = new Firebase(Global_Vars.usersRef);
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
                                    Functions.setWeight((int) masodikszint.getValue());
                                    break;
                                case "height":
                                    Functions.setHeight((int) masodikszint.getValue());
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
