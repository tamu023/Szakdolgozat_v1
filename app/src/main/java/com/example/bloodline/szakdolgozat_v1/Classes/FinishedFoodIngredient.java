package com.example.bloodline.szakdolgozat_v1.Classes;

public class FinishedFoodIngredient {
    String megnevezes;
    double mennyiseg;

    public FinishedFoodIngredient(String megnevezes, double mennyiseg) {
        this.megnevezes = megnevezes;
        this.mennyiseg = mennyiseg;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
    }

    public double getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(double mennyiseg) {
        this.mennyiseg = mennyiseg;
    }
}
