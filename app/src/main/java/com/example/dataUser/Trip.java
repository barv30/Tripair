package com.example.dataUser;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    private String country;
    private String city;
    private ArrayList<String> styleTrip;
    private Date arriveDate;
    private Date leftDate;
    private Partner partner;
    private PartnerManager partnerList;


    public Trip() {
        styleTrip = new ArrayList<>();
    }

    public ArrayList<String> getStyleTrip() {
        return styleTrip;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public Date getLeftDate() {
        return leftDate;
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

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLeftDate(Date leftDate) {
        this.leftDate = leftDate;
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
}