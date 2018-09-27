package com.example.bloodline.szakdolgozat_v1.Classes;

import java.util.List;

public class FinishedFood {
    String foodname;
    long carb;
    boolean flour;
    boolean milk;
    boolean meat;
    String recipe;
    double preptime;
    List<FinishedFoodIngredient> ingredientList;

    public FinishedFood(String foodname, long carb, boolean flour, boolean milk, boolean meat, String recipe, double preptime, List<FinishedFoodIngredient> ingredientList) {
        this.foodname = foodname;
        this.carb = carb;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
        this.recipe = recipe;
        this.preptime = preptime;
        this.ingredientList = ingredientList;
    }

    //admin lista elkészítéséhez
    public FinishedFood(String foodname, long carb, boolean flour, boolean milk, boolean meat) {
        this.foodname = foodname;
        this.carb = carb;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
    }

    public String getFoodname() {
        return foodname;
    }

    public long getCarb() {
        return carb;
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

    public String getRecipe() {
        return recipe;
    }

    public double getPreptime() {
        return preptime;
    }

    public List<FinishedFoodIngredient> getIngredientList() {
        return ingredientList;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setCarb(long carb) {
        this.carb = carb;
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

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public void setPreptime(double preptime) {
        this.preptime = preptime;
    }

    public void setIngredientList(List<FinishedFoodIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
