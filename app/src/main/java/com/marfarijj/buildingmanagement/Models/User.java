package com.marfarijj.buildingmanagement.Models;

public class User {
    String name, password, confirmPassword, flatNo, phoneNo;
    String maintenanceFeesStatus, isCollector;

    public User() {
    }

    public User(String name, String password, String confirmPassword, String flatNo, String phoneNo, String maintenanceFeesStatus, String isCollector) {
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.flatNo = flatNo;
        this.phoneNo = phoneNo;
        this.maintenanceFeesStatus = maintenanceFeesStatus;
        this.isCollector = isCollector;
    }

    public User(String name, String maintenanceFeesStatus, String flatNo, String phoneNo) {
        this.name = name;
        this.flatNo = flatNo;
        this.phoneNo = phoneNo;
        this.maintenanceFeesStatus = maintenanceFeesStatus;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getMaintenanceFeesStatus() {
        return maintenanceFeesStatus;
    }

    public String getIsCollector() {
        return isCollector;
    }
}
