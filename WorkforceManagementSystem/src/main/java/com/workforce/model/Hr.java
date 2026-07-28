package com.workforce.model;

public class Hr extends Employee {
    private int hrId;
    private int recruitmentCount;
    private int trainingHandled;

    public Hr(String name, String email, String password, String phone, String deptID,
               int hrId) {
        super(name, email, password, phone, deptID);
        this.hrId = hrId;
        this.recruitmentCount = 0;
        this.trainingHandled = 0;
    }

    public int getHrId() { return hrId; }

    @Override
    public String getRoleLabel() { return "HR"; }

    @Override
    protected void printSpecificDetails() {
        System.out.println("HR ID             : " + hrId);
        System.out.println("Recruitment Count : " + recruitmentCount);
        System.out.println("Training Handled  : " + trainingHandled);
    }

    public void addEmployee() {
        recruitmentCount++;
        System.out.println(name + " recruited a new employee. Total recruits: " + recruitmentCount);
    }

    public void removeEmployee() {
        System.out.println(name + " processed an employee exit.");
    }

    public void manageDocuments() {
        trainingHandled++;
        System.out.println(name + " is managing employee documents. Trainings logged: " + trainingHandled);
    }
}
