package com.example.easyprivate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FilterGuru {

    @Expose
    @SerializedName("saw_guru")
    private ArrayList<SawGuru> sawGuru;
    @Expose
    @SerializedName("user")
    private ArrayList<User> user;

    public ArrayList<SawGuru> getSawGuru() {
        return sawGuru;
    }

    public void setSawGuru(ArrayList<SawGuru> sawGuru) {
        this.sawGuru = sawGuru;
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }
}
