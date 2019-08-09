package com.example.recycleViewPack;

import com.example.dataUser.Trip;

public class TripPOJO
{
    private String mCountry;
    private String mCity;
    private int mDay;
    private int  mMonth;
    private int mYear;

    TripPOJO(){}
    TripPOJO(String iCountry, String iCity,int iDay,int iMonth, int iYear)
    {
        this.mCountry = iCountry;
        this.mCity = iCity;
        this.mDay = iDay;
        this.mMonth = iMonth;
        this.mYear = iYear;
    }

    public int getmDay() {
        return mDay;
    }

    public int getmMonth() {
        return mMonth;
    }

    public int getmYear() {
        return mYear;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }
}
