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
}
