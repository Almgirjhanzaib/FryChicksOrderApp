package com.example.frychicksorderapp.ui.Models;

public class UserModel {
    String name,pass,phon;

    public UserModel() {
    }

    public UserModel(String name, String pass, String phon) {
        this.name = name;
        this.pass = pass;
        this.phon = phon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhon() {
        return phon;
    }

    public void setPhon(String phon) {
        this.phon = phon;
    }
}
