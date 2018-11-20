package edu.nanodegreeprojects.capstone.travelendar.model;

import java.io.Serializable;

public class Trip implements Serializable {

    private int id;
    private PlaceItem toWhere;
    private PlaceItem fromWhere;
    private String initialDate;
    private String endDate;
    private int rate;
    private float budget;
    private String generalNotes;
    private String status;


    public Trip()
    {
        this.id = 7;
        this.toWhere.setPlaceName("to");
        this.fromWhere.setPlaceName("from");
        this.initialDate = "initialDate";
        this.endDate = "endDate";
        this.rate = 1;
        this.budget = 4500;
        this.generalNotes = "generalNotes";
        this.status = "upcoming";
    }
    public Trip(PlaceItem toWhere, PlaceItem fromWhere, String initialDate, String endDate, int rate, float budget, String generalNotes, String status) {
        this.toWhere = toWhere;
        this.fromWhere = fromWhere;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.rate = rate;
        this.budget = budget;
        this.generalNotes = generalNotes;
        this.status = status;
    }

    public Trip(int id, PlaceItem toWhere, PlaceItem fromWhere, String initialDate, String endDate, int rate, float budget, String generalNotes, String status) {
        this.id = id;
        this.toWhere = toWhere;
        this.fromWhere = fromWhere;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.rate = rate;
        this.budget = budget;
        this.generalNotes = generalNotes;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlaceItem getToWhere() {
        return toWhere;
    }

    public void setToWhere(String to) {
        this.toWhere = toWhere;
    }

    public PlaceItem getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(PlaceItem fromWhere) {
        this.fromWhere = fromWhere;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
