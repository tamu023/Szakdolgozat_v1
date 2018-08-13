package com.example.bloodline.szakdolgozat_v1.Classes;

public class RawIngredient {
    private String ingredientName;
    private boolean flour;
    private boolean milk;
    private boolean meat;

    public RawIngredient(String ingredientName, boolean flour, boolean milk, boolean meat) {
        this.ingredientName = ingredientName;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
    }

    public String getIngredientName() {
        return ingredientName;
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

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
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
}
