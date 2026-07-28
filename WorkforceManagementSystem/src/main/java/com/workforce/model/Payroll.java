package com.workforce.model;

public class Payroll {
    private static int nextPayId = 1;

    private final int payId;
    private String month;
    private double basicPay;
    private double netSalary;
    private double deductions;
    private double attendance; // attendance percentage used in salary calc

    private final Employee employee;

    public Payroll(Employee employee, String month, double basicPay, double deductions, double attendance) {
        this.payId = nextPayId++;
        this.employee = employee;
        this.month = month;
        this.basicPay = basicPay;
        this.deductions = deductions;
        this.attendance = attendance;
        this.netSalary = 0;
    }

    public int getPayId() { return payId; }
    public double getNetSalary() { return netSalary; }
    public Employee getEmployee() { return employee; }
    public String getMonth() { return month; }

    public void calculateSalary() {
        double attendanceAdjustedPay = basicPay * (attendance / 100.0);
        netSalary = attendanceAdjustedPay - deductions;
        if (netSalary < 0) netSalary = 0;
    }

    public void generatePaySlip() {
        calculateSalary();
        System.out.println("========= PAY SLIP =========");
        System.out.println("Pay ID       : " + payId);
        System.out.println("Employee     : " + employee.getName() + " (ID " + employee.getEmpId() + ")");
        System.out.println("Month        : " + month);
        System.out.println("Basic Pay    : " + basicPay);
        System.out.println("Attendance % : " + attendance);
        System.out.println("Deductions   : " + deductions);
        System.out.println("Net Salary   : " + netSalary);
        System.out.println("=============================");
    }
}
