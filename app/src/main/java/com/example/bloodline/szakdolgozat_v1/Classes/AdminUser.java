package com.example.bloodline.szakdolgozat_v1.Classes;

public class AdminUser {
    private String name;
    private String UID;

    public AdminUser(String name, String UID) {
        this.name = name;
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

}
