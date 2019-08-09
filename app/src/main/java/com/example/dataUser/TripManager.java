package com.example.dataUser;

import java.util.ArrayList;

public class TripManager
{
    private ArrayList<Trip> tripList;

    public TripManager()
    {
        tripList = new ArrayList<>();
    }

    public ArrayList<Trip> getTripList() {
        return tripList;
    }

    public void updateTripList(String key, Trip trip) {
        tripList.add(Integer.parseInt(key),trip);
    }

    public Trip findInTripList(String key) {
        return tripList.get(Integer.parseInt(key));
    }
}
