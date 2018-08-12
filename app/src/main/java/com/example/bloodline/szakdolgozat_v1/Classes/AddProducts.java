package com.example.bloodline.szakdolgozat_v1.Classes;

public class AddProducts {
    String megnevezes;
    long carbohydrate;
    boolean flour;
    boolean milk;
    boolean meat;
    String recept;
    String[] ingredients;

    //Finished food add
    public AddProducts(String megnevezes, long carbohydrate, String recept, String[] ingredients) {
        this.megnevezes = megnevezes;
        this.carbohydrate = carbohydrate;
        this.recept = recept;
        this.ingredients = ingredients;
    }

    //Raw food add
    //TODO átgondolni hogy szükséges-e megadni szénhidrátot alapanyagokhoz
    public AddProducts(String megnevezes, long carbohydrate, boolean flour, boolean milk, boolean meat) {
        this.megnevezes = megnevezes;
        this.carbohydrate = carbohydrate;
        this.flour = flour;
        this.milk = milk;
        this.meat = meat;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public long getCarbohydrate() {
        return carbohydrate;
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

    public String getRecept() {
        return recept;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
    }

    public void setCarbohydrate(long carbohydrate) {
        this.carbohydrate = carbohydrate;
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

    public void setRecept(String recept) {
        this.recept = recept;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
