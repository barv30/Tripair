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

    public void setTripList(ArrayList<Trip> tripList) {
        this.tripList = tripList;
    }
}
