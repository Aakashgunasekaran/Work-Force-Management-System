package com.workforce.model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {
    private String managerId;
    private int teamSize;
    private String managedProject;

    private final List<Developer> managedDevelopers = new ArrayList<>();
    private final List<Shift> shifts = new ArrayList<>();

    public Manager(String name, String email, String password, String phone, String deptID,
                    String managerId, String managedProject) {
        super(name, email, password, phone, deptID);
        this.managerId = managerId;
        this.managedProject = managedProject;
        this.teamSize = 0;
    }

    public String getManagerId() { return managerId; }
    public List<Developer> getManagedDevelopers() { return managedDevelopers; }
    public List<Shift> getShifts() { return shifts; }

    @Override
    public String getRoleLabel() { return "Manager"; }

    @Override
    protected void printSpecificDetails() {
        System.out.println("Manager ID     : " + managerId);
        System.out.println("Team Size      : " + teamSize);
        System.out.println("Managed Project: " + managedProject);
    }

    public void manages(Developer dev) {
        dev.setManager(this);
        managedDevelopers.add(dev);
        teamSize = managedDevelopers.size();
        System.out.println(name + " now manages " + dev.getName() + ". Team size: " + teamSize);
    }

    public void approveLeave(Leave leave, boolean approve) {
        leave.setStatus(approve ? "APPROVED" : "REJECTED");
        System.out.println("Leave " + leave.getLeaveId() + " " + leave.getStatus() + " by " + name);
    }

    public void assignShift(Shift shift) {
        shifts.add(shift);
        System.out.println(name + " assigned shift " + shift.getShiftName() + " to the team.");
    }

    public void viewTeamStatus() {
        System.out.println("=== Team Status: " + name + " (Project: " + managedProject + ") ===");
        if (managedDevelopers.isEmpty()) {
            System.out.println("No developers assigned yet.");
        }
        for (Developer d : managedDevelopers) {
            System.out.println(" - " + d.getName() + " | Project: " + d.getProjectAssigned()
                    + " | Completed: " + d.getCompletedProject());
        }
    }
}
