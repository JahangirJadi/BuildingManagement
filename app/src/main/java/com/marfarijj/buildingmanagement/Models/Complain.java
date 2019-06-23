package com.marfarijj.buildingmanagement.Models;

public class Complain {
    String complainFrom, complainSubject, complainDescription;


    public Complain() {
    }

    public Complain(String complainFrom, String complainSubject, String complainDescription) {
        this.complainFrom = complainFrom;
        this.complainSubject = complainSubject;
        this.complainDescription = complainDescription;
    }

    public String getComplainFrom() {
        return complainFrom;
    }

    public void setComplainFrom(String complainFrom) {
        this.complainFrom = complainFrom;
    }

    public String getComplainSubject() {
        return complainSubject;
    }

    public void setComplainSubject(String complainSubject) {
        this.complainSubject = complainSubject;
    }

    public String getComplainDescription() {
        return complainDescription;
    }

    public void setComplainDescription(String complainDescription) {
        this.complainDescription = complainDescription;
    }
}
