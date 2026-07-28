package com.workforce.model;

import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Shift {
    private static int nextShiftId = 1;

    private final int shiftId;
    private String shiftName;
    private LocalTime startTime;
    private LocalTime endTime;

    private final List<Employee> employees = new ArrayList<>();

    public Shift(String shiftName, LocalTime startTime, LocalTime endTime) {
        this.shiftId = nextShiftId++;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShiftId() { return shiftId; }
    public String getShiftName() { return shiftName; }
    public List<Employee> getEmployees() { return employees; }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public Double getDuration() {
        double hours = Duration.between(startTime, endTime).toMinutes() / 60.0;
        System.out.println("Shift " + shiftName + " duration: " + hours + " hrs");
        return hours;
    }

    public int getTotalEmployee() {
        System.out.println("Shift " + shiftName + " has " + employees.size() + " employee(s).");
        return employees.size();
    }

    @Override
    public String toString() {
        return "Shift#" + shiftId + " " + shiftName + " (" + startTime + " - " + endTime + ")";
    }
}
