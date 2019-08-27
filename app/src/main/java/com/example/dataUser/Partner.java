package com.example.dataUser;

import java.io.Serializable;
import java.util.ArrayList;

public class Partner implements Serializable {
    private int minAge;
    private int maxAge;
    private String gender;
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

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }
}
