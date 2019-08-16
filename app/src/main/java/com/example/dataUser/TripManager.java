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

    public void updateTripList(String key, Trip trip) {
        tripList.add(trip);
    }

    public boolean findInTripList(String key) {
        for (int i =0 ;i < tripList.size(); i++) {
            if (tripList.get(i).getCountry() == key)
                return true;
        }
        return false;
    }
}
