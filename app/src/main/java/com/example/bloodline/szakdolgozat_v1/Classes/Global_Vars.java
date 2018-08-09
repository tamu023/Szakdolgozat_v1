package com.example.bloodline.szakdolgozat_v1.Classes;

public interface Global_Vars {
    String rootRef = "https://szakdolgozatv1.firebaseio.com";
    String usersRef = rootRef + "/Users";
    String finProdRef = rootRef + "/Products/Finished Products";
    String rawProdRef = rootRef + "/Products/Raw Ingredients";
    String finpendingProdRef = rootRef + "/Pending Products/Finished Products";
    String rawpendingProdRef = rootRef + "/Pending Products/Raw Ingredients";
    String pendingUserRef = rootRef + "/Pending Admin Users";
}
