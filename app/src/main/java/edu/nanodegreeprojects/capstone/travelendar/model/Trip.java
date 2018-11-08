package edu.nanodegreeprojects.capstone.travelendar.model;

import java.io.Serializable;

public class Trip implements Serializable {

    private String to;
    private String from;
    private String initialDate;
    private String endDate;
    private int rate;
    private float budget;
    private String generalNotes;
    private String status;

    public Trip(String to, String from, String initialDate, String endDate, int rate, float budget, String generalNotes, String status) {
        this.to = to;
        this.from = from;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.rate = rate;
        this.budget = budget;
        this.generalNotes = generalNotes;
        this.status = status;
    }

    public Trip()
    {
        this.to = "Teste";
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
