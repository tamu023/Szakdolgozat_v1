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
    List<AddProducts> ingredientList;

    public FinishedFood(String foodname, long carb, boolean flour, boolean milk, boolean meat, String recipe, double preptime, List<AddProducts> ingredientList) {
        this.foodname = foodname;
        this.carb = carb;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
        this.recipe = recipe;
        this.preptime = preptime;
        this.ingredientList = ingredientList;
    }
}
