package com.workforce.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    private static int nextEmpId = 1001;

    private final int empId;
    protected String name;
    protected String email;
    protected String password;
    protected String phone;
    protected String deptID;

    protected final List<Leave> leaves = new ArrayList<>();
    protected final List<Document> documents = new ArrayList<>();

    public Employee(String name, String email, String password, String phone, String deptID) {
        this.empId = nextEmpId++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.deptID = deptID;
    }

    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDeptID() { return deptID; }
    public List<Leave> getLeaves() { return leaves; }
    public List<Document> getDocuments() { return documents; }

    public void viewProfile() {
        System.out.println("---------------------------------");
        System.out.println("Emp ID   : " + empId);
        System.out.println("Role     : " + getRoleLabel());
        System.out.println("Name     : " + name);
        System.out.println("Email    : " + email);
        System.out.println("Phone    : " + phone);
        System.out.println("Dept ID  : " + deptID);
        printSpecificDetails();
        System.out.println("---------------------------------");
    }

    public void updateProfile(String email, String phone) {
        this.email = email;
        this.phone = phone;
        System.out.println("Profile updated for " + name + " (ID " + empId + ").");
    }

    // Each subtype prints its own extra fields and identifies its role name.
    protected abstract void printSpecificDetails();
    public abstract String getRoleLabel();
}
