package com.marfarijj.buildingmanagement.Models;

public class Maintenance {
    String name,  flatNo;
    Boolean status;

    public Maintenance() {
    }

    public Maintenance(String name, Boolean status, String flatNo) {
        this.name = name;
        this.status = status;
        this.flatNo = flatNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}
