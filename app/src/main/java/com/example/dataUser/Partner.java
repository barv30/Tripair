package com.example.dataUser;

import java.util.ArrayList;

public class Partner {
    private int minAge;
    private int maxAge;
    private String gender;
    private ArrayList<String> languages;
    private boolean isSmoking;

    public Partner() {
        languages = new ArrayList<>();
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isSmoking() {
        return isSmoking;
    }


    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }
}
