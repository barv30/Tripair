package com.example.dataUser;

import java.util.ArrayList;

public class Partner {
    private int minAge;
    private int maxAge;
    private String gender;
    private String mainLanguage;
    private boolean isSmoking;



    public String getGender() {
        return gender;
    }


    public int getMaxAge() {
        return maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public String getMainLanguage() {
        return mainLanguage;
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

    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage = mainLanguage;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }
}
