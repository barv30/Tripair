package com.example.dataUser;


import java.util.ArrayList;
import java.util.Date;

public class User {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String country;
    private ArrayList<String> languages;
    private boolean isSmoking;
    private String aboutMe;
    private TripManager allTrips;


    public User(String firstName,
                String lastName,
                Date birthDate,
                String gender,
                String country,
                ArrayList<String> languages,
                boolean isSmoking,
                String aboutMe)
    {
        this.allTrips = new TripManager() ;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = new Date();
        this.birthDate = birthDate;
        this. gender = gender;
        this.languages = new ArrayList<>();
        this.languages = languages;
        this.isSmoking = isSmoking;
        this.aboutMe = aboutMe;
    }
}


