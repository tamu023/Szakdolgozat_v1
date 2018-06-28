package com.example.bloodline.szakdolgozat_v1;

import com.firebase.client.Firebase;

public class RegLog {
    private String email;
    private String name;
    private Boolean cukorbetegseg;
    private Boolean liszterzekenyseg;
    private Boolean laktozerzekenyseg;
    private Integer weight;
    private Integer height;
    private Boolean gender; //true = man, false = woman
    private Double bmiindex;
    private Boolean acctype; //true = admin, false = use

    //kintről hívjuk meg regisztrációkor
    public RegLog(String email, String name, Boolean cukorbetegseg, Boolean liszterzekenyseg, Boolean laktozerzekenyseg, Integer weight, Integer height, Boolean gender, Boolean acctype) {
        this.email = email;
        this.name = name;
        this.cukorbetegseg = cukorbetegseg;
        this.liszterzekenyseg = liszterzekenyseg;
        this.laktozerzekenyseg = laktozerzekenyseg;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.acctype = acctype;
    }

    //osztályon belülről hívjuk ezzel írjuk bele az adatokat az adatbázisba
    private RegLog(String email, String name, Boolean cukorbetegseg, Boolean liszterzekenyseg, Boolean laktozerzekenyseg, Integer weight, Integer height, Boolean gender, Double bmiindex, Boolean acctype) {
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

    //adatok beírása az adatbázisba
    public void setVariables() {
        Functions.setName(this.name);
        Functions.setEmail(this.email);
        Functions.setCukorbetegseg(this.cukorbetegseg);
        Functions.setLiszterzekenyseg(this.liszterzekenyseg);
        Functions.setLaktozerzekenyseg(this.laktozerzekenyseg);
        Functions.setWeight(this.weight);
        Functions.setHeight(this.height);
        Functions.setGender(this.gender);
        Functions.setBmiindex(calcBMI(this.height, this.weight));
        Functions.setAcctype(this.acctype);
        this.bmiindex = Functions.getBmiindex();
        final Firebase ref = new Firebase(Global_Vars.usersRef);
        RegLog uj = new RegLog(this.email, this.name, this.cukorbetegseg, this.liszterzekenyseg, this.laktozerzekenyseg, this.weight, this.height, this.gender, this.bmiindex, this.acctype);
        ref.child(Functions.getUID()).setValue(uj);
    }

    private double calcBMI(Integer height, Integer weight) {
        return (double) weight / Math.pow((double) height, 2);
    }
}
