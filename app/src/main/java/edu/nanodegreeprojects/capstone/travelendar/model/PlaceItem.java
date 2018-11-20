package edu.nanodegreeprojects.capstone.travelendar.model;

import com.google.android.gms.maps.model.LatLng;

public class PlaceItem {

    private String placeName;
    private LatLng latLng;
    private String address;

    public PlaceItem(String placeName, LatLng latLng, String address) {
        this.placeName = placeName;
        this.latLng = latLng;
        this.address = address;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
