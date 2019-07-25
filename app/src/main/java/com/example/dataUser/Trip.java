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

}