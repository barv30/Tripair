package com.example.dataUser;


import java.util.ArrayList;
import java.util.Date;

public class User {
    private String firstName;
    private String lastName;
    private String gender;
    private int birthYear;
    private int birthDay;
    private int birthMonth;
    private ArrayList<String> languages;
    private boolean isSmoking;
    private String aboutMe;
    private TripManager allTrips;


    public User(String firstName,
                String lastName,
                int day,
                int month,
                int year,
                String gender,
                ArrayList<String> languages,
                boolean isSmoking,
                String aboutMe)
    {
        this.allTrips = new TripManager() ;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = day;
        this.birthMonth = month;
        this.birthYear = year;
        this. gender = gender;
        this.languages = new ArrayList<>();
        this.languages = languages;
        this.isSmoking = isSmoking;
        this.aboutMe = aboutMe;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public TripManager getAllTrips() {
        return allTrips;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public boolean isSmoking() {
        return isSmoking;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAllTrips(TripManager allTrips) {
        this.allTrips = allTrips;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

}



