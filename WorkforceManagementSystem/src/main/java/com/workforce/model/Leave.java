package com.workforce.model;

import java.time.LocalDate;

public class Leave {
    private static int nextLeaveId = 1;

    private final int leaveId;
    private String reason;
    private LocalDate from;
    private LocalDate to;
    private String status;
    private String leaveType;

    private final Employee employee;

    public Leave(Employee employee, String reason, LocalDate from, LocalDate to, String leaveType) {
        this.leaveId = nextLeaveId++;
        this.employee = employee;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.leaveType = leaveType;
        this.status = "PENDING";
    }

    public int getLeaveId() { return leaveId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Employee getEmployee() { return employee; }

    public void applyLeave() {
        employee.getLeaves().add(this);
        System.out.println("Leave " + leaveId + " applied by " + employee.getName()
                + " (" + leaveType + ", " + from + " to " + to + "). Status: " + status);
    }

    public void cancelLeave() {
        this.status = "CANCELLED";
        System.out.println("Leave " + leaveId + " has been cancelled.");
    }

    @Override
    public String toString() {
        return "Leave#" + leaveId + " [" + leaveType + "] " + from + " - " + to
                + " | Reason: " + reason + " | Status: " + status;
    }
}
