package com.example.dataUser;

import java.io.Serializable;
import java.util.ArrayList;

public class TripManager implements Serializable
{
    private ArrayList<Trip> tripList;


    public TripManager()
    {
        tripList = new ArrayList<>();
    }

    public ArrayList<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }

    public void updateTripList(Trip trip) {
        tripList.add(trip);
    }

    public Trip findInTripList(String country, String city) {
        for (int i =0 ;i < tripList.size(); i++) {
            if (tripList.get(i).getCountry().equals(country)  && tripList.get(i).getCity().equals(city))
                return tripList.get(i);
        }
        return null;
    }
}
