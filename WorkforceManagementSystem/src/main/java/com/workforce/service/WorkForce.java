package com.workforce.service;

import com.workforce.model.*;

import java.util.ArrayList;
import java.util.List;

public class WorkForce {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Department> departments = new ArrayList<>();
    private final List<Document> documents = new ArrayList<>();
    private final List<Payroll> payrolls = new ArrayList<>();
    private final List<Shift> shifts = new ArrayList<>();
    private final List<Attendance> attendanceRecords = new ArrayList<>();

    public List<Employee> getEmployees() { return employees; }
    public List<Department> getDepartments() { return departments; }
    public List<Document> getDocuments() { return documents; }
    public List<Payroll> getPayrolls() { return payrolls; }
    public List<Shift> getShifts() { return shifts; }
    public List<Attendance> getAttendanceRecords() { return attendanceRecords; }

    public void addEmployee(Employee e) {
        employees.add(e);
        System.out.println(e.getRoleLabel() + " added: " + e.getName() + " (ID " + e.getEmpId() + ")");
    }

    public boolean removeEmployee(int empId) {
        boolean removed = employees.removeIf(e -> e.getEmpId() == empId);
        System.out.println(removed ? "Employee " + empId + " removed." : "Employee " + empId + " not found.");
        return removed;
    }

    public Employee findEmployee(int empId) {
        return employees.stream().filter(e -> e.getEmpId() == empId).findFirst().orElse(null);
    }

    public void addDepartment(Department d) {
        departments.add(d);
        System.out.println("Department added: " + d.getDeptName());
    }

    public Department findDepartment(int deptId) {
        return departments.stream().filter(d -> d.getDeptId() == deptId).findFirst().orElse(null);
    }

    public void markAttendance(Attendance a) {
        attendanceRecords.add(a);
        System.out.println("Attendance marked for " + a.getEmployee().getName() + ".");
    }

    public void generateReports() {
        System.out.println("===================================");
        System.out.println("        WORKFORCE SUMMARY REPORT");
        System.out.println("===================================");
        System.out.println("Total Departments : " + departments.size());
        System.out.println("Total Employees   : " + employees.size());

        long devCount = employees.stream().filter(e -> e instanceof Developer).count();
        long mgrCount = employees.stream().filter(e -> e instanceof Manager).count();
        long hrCount = employees.stream().filter(e -> e instanceof Hr).count();
        System.out.println("  Developers      : " + devCount);
        System.out.println("  Managers        : " + mgrCount);
        System.out.println("  HR Staff        : " + hrCount);

        System.out.println("Total Documents   : " + documents.size());
        System.out.println("Total Shifts      : " + shifts.size());
        System.out.println("Total Payrolls    : " + payrolls.size());
        System.out.println("Attendance Records: " + attendanceRecords.size());
        System.out.println("===================================");
    }

    public void generatePayrolls() {
        System.out.println("Generating payslips for all employees with an unfinalized payroll...");
        for (Payroll p : payrolls) {
            p.generatePaySlip();
        }
        if (payrolls.isEmpty()) {
            System.out.println("No payroll records exist yet.");
        }
    }
}
