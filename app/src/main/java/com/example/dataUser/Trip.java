package com.example.dataUser;

import android.provider.Telephony;

import com.example.recycleViewPack.ContactPOJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Trip implements Serializable {
    private String country;
    private String city;
    private ArrayList<String> styleTrip;
    private int arriveDay;
    private int arriveMonth;
    private int arriveYear;
    private int leftDay;
    private int leftMonth;
    private int leftYear;
    private Partner partner;
    private PartnerManager partnerList;
    private ArrayList<ContactPOJO> favPartner;


    public Trip() {
        styleTrip = new ArrayList<>();
        partner = new Partner();
        partnerList = new PartnerManager();
        favPartner = new ArrayList<>();
    }

    public ArrayList<String> getStyleTrip() {
        return styleTrip;
    }

    public int getArriveDay() {
        return arriveDay;
    }

    public int getArriveMonth() {
        return arriveMonth;
    }

    public int getArriveYear() {
        return arriveYear;
    }

    public int getLeftDay() {
        return leftDay;
    }

    public int getLeftMonth() {
        return leftMonth;
    }

    public int getLeftYear() {
        return leftYear;
    }

    public Partner getPartner() {
        return partner;
    }

    public PartnerManager getPartnerList() {
        return partnerList;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }


    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void setStyleTrip(ArrayList<String> styleTrip) {
        this.styleTrip = styleTrip;
    }

    public void setPartnerList(PartnerManager partnerList) {
        this.partnerList = partnerList;
    }

    public void setArriveDay(int arriveDay) {
        this.arriveDay = arriveDay;
    }

    public void setArriveMonth(int arriveMonth) {
        this.arriveMonth = arriveMonth;
    }

    public void setArriveYear(int arriveYear) {
        this.arriveYear = arriveYear;
    }

    public void setLeftDay(int leftDay) {
        this.leftDay = leftDay;
    }

    public void setLeftMonth(int leftMonth) {
        this.leftMonth = leftMonth;
    }

    public void setLeftYear(int leftYear) {
        this.leftYear = leftYear;
    }
}