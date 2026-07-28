package com.workforce.model;

public class Department {
    private int deptId;
    private String deptName;
    private String location;

    public Department(int deptId, String deptName, String location) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.location = location;
    }

    public int getDeptId() { return deptId; }
    public String getDeptName() { return deptName; }
    public String getLocation() { return location; }

    public void getDeptDetails() {
        System.out.println("---------------------------------");
        System.out.println("Dept ID   : " + deptId);
        System.out.println("Dept Name : " + deptName);
        System.out.println("Location  : " + location);
        System.out.println("---------------------------------");
    }

    public void updateDeptDetails(String deptName, String location) {
        this.deptName = deptName;
        this.location = location;
        System.out.println("Department " + deptId + " updated successfully.");
    }
}
