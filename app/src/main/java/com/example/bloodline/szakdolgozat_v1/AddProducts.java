package com.example.bloodline.szakdolgozat_v1;

public class AddProducts {
    String megnevezes;
    int carbohydrate;
    boolean flour;
    boolean milk;
    boolean meat;
    String recept;
    String[] ingredients;

    //Finished food add
    public AddProducts(String megnevezes, int carbohydrate, String recept, String[] ingredients) {
        this.megnevezes = megnevezes;
        this.carbohydrate = carbohydrate;
        this.recept = recept;
        this.ingredients = ingredients;
    }

    //Raw food add
    public AddProducts(String megnevezes, int carbohydrate, boolean flour, boolean milk, boolean meat) {
        this.megnevezes = megnevezes;
        this.carbohydrate = carbohydrate;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
    }

}
