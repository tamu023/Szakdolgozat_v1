package com.example.bloodline.szakdolgozat_v1.Classes;

public class AddProducts {
    String megnevezes;
    boolean flour;
    boolean milk;
    boolean meat;
    boolean unit; //True = solid(KG), False = liquid(L)
    double quantity;

    //Raw food add
    public AddProducts(String megnevezes, boolean flour, boolean milk, boolean meat, boolean unit) {
        this.megnevezes = megnevezes;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
        this.unit = unit;
    }

    //Storage Constructor
    public AddProducts(String megnevezes, boolean unit, double quantity) {
        this.megnevezes = megnevezes;
        this.unit = unit;
        this.quantity = quantity;
    }

    //Storage Add Item
    public AddProducts(String megnevezes, boolean unit) {
        this.megnevezes = megnevezes;
        this.unit = unit;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public boolean getFlour() {
        return flour;
    }

    public boolean getMilk() {
        return milk;
    }

    public boolean getMeat() {
        return meat;
    }

    public boolean getUnit() {
        return unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
    }

    public void setFlour(boolean flour) {
        this.flour = flour;
    }

    public void setMilk(boolean milk) {
        this.milk = milk;
    }

    public void setMeat(boolean meat) {
        this.meat = meat;
    }

    public void setUnit(boolean unit) {
        this.unit = unit;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
