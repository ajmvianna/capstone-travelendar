package edu.nanodegreeprojects.capstone.travelendar.model;

import java.io.Serializable;

public class PlaceItem implements Serializable{

    private String placeName;
    private double latitude;
    private double longitude;
    private String address;

    public PlaceItem(String placeName, double latitude, double longitude, String address) {
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
