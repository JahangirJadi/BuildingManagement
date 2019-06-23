package com.marfarijj.buildingmanagement.Models;

public class MaintenanceExpense {

    String maintenanceItemName, maintenanceItemMonth, maintenanceItemCost;

    public MaintenanceExpense() {
    }

    public MaintenanceExpense(String maintenanceItemName, String maintenanceItemMonth, String maintenanceItemCost) {
        this.maintenanceItemName = maintenanceItemName;
        this.maintenanceItemMonth = maintenanceItemMonth;
        this.maintenanceItemCost = maintenanceItemCost;
    }

    public String getMaintenanceItemName() {
        return maintenanceItemName;
    }

    public String getMaintenanceItemMonth() {
        return maintenanceItemMonth;
    }

    public String getMaintenanceItemCost() {
        return maintenanceItemCost;
    }
}
