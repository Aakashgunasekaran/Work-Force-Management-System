package com.workforce.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public class Attendance {
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private double totalHours;

    private final Employee employee;

    public Attendance(Employee employee, LocalDate date, LocalTime checkIn, LocalTime checkOut) {
        this.employee = employee;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalHours = 0;
    }

    public Employee getEmployee() { return employee; }
    public double getTotalHours() { return totalHours; }

    public double calculatePercentage() {
        Duration worked = Duration.between(checkIn, checkOut);
        totalHours = worked.toMinutes() / 60.0;
        double standardDay = 8.0;
        double percentage = Math.min(100.0, (totalHours / standardDay) * 100.0);
        System.out.println(employee.getName() + " on " + date + " worked " + totalHours
                + " hrs (" + String.format("%.1f", percentage) + "% of standard day).");
        return percentage;
    }

    public double calculateLeave() {
        // Employees who fall short of the standard 8hr day accrue a leave shortfall fraction.
        double standardDay = 8.0;
        double shortfall = Math.max(0, standardDay - totalHours) / standardDay;
        System.out.println("Leave shortfall fraction for " + employee.getName() + ": "
                + String.format("%.2f", shortfall));
        return shortfall;
    }
}
