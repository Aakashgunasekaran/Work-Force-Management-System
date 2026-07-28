package com.workforce;

import com.workforce.model.*;
import com.workforce.service.WorkForce;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final WorkForce workForce = new WorkForce();
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        seedSampleData();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> departmentMenu();
                case 2 -> employeeMenu();
                case 3 -> payrollMenu();
                case 4 -> leaveMenu();
                case 5 -> attendanceMenu();
                case 6 -> shiftMenu();
                case 7 -> documentMenu();
                case 8 -> workForce.generateReports();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting Workforce Management System. Goodbye!");
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
        sc.close();
    }

    // ---------------- MAIN MENU ----------------

    private static void printMainMenu() {
        System.out.println("\n===== WORKFORCE MANAGEMENT SYSTEM =====");
        System.out.println("1. Department Management");
        System.out.println("2. Employee Management");
        System.out.println("3. Payroll");
        System.out.println("4. Leave Management");
        System.out.println("5. Attendance");
        System.out.println("6. Shift Management");
        System.out.println("7. Document Management");
        System.out.println("8. Generate Workforce Report");
        System.out.println("0. Exit");
    }

    // ---------------- DEPARTMENT ----------------

    private static void departmentMenu() {
        System.out.println("\n-- Department Management --");
        System.out.println("1. Add Department");
        System.out.println("2. View All Departments");
        System.out.println("3. Update Department");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                int id = readInt("Dept ID: ");
                String name = readLine("Dept Name: ");
                String loc = readLine("Location: ");
                Department d = new Department(id, name, loc);
                workForce.addDepartment(d);
            }
            case 2 -> {
                if (workForce.getDepartments().isEmpty()) System.out.println("No departments yet.");
                for (Department d : workForce.getDepartments()) d.getDeptDetails();
            }
            case 3 -> {
                int id = readInt("Dept ID to update: ");
                Department d = workForce.findDepartment(id);
                if (d == null) { System.out.println("Not found."); return; }
                String name = readLine("New Dept Name: ");
                String loc = readLine("New Location: ");
                d.updateDeptDetails(name, loc);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // ---------------- EMPLOYEE ----------------

    private static void employeeMenu() {
        System.out.println("\n-- Employee Management --");
        System.out.println("1. Add Developer");
        System.out.println("2. Add Manager");
        System.out.println("3. Add HR");
        System.out.println("4. View All Employees");
        System.out.println("5. View Profile by ID");
        System.out.println("6. Update Profile by ID");
        System.out.println("7. Remove Employee by ID");
        System.out.println("8. Assign Developer to Manager (manages)");
        System.out.println("9. Developer Dashboard");
        System.out.println("10. Developer: Mark Task Complete");
        System.out.println("11. Manager: View Team Status");
        System.out.println("12. HR: Recruit / Manage Documents");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                String[] basic = readCommonEmployeeFields();
                String role = readLine("Role (e.g. Backend/Frontend): ");
                String project = readLine("Project Assigned: ");
                int completed = readInt("Completed Projects: ");
                int exp = readInt("Experience Level (years): ");
                Developer dev = new Developer(basic[0], basic[1], "", "", basic[2],
                        role, project, completed, exp);
                workForce.addEmployee(dev);
            }
            case 2 -> {
                String[] basic = readCommonEmployeeFields();
                String managerId = readLine("Manager ID: ");
                String managedProject = readLine("Managed Project: ");
                Manager mgr = new Manager(basic[0], basic[1], "", "", basic[2],
                        managerId, managedProject);
                workForce.addEmployee(mgr);
            }
            case 3 -> {
                String[] basic = readCommonEmployeeFields();
                int hrId = readInt("HR ID: ");
                Hr hr = new Hr(basic[0], basic[1], "", "", basic[2], hrId);
                workForce.addEmployee(hr);
            }
            case 4 -> {
                if (workForce.getEmployees().isEmpty()) System.out.println("No employees yet.");
                for (Employee e : workForce.getEmployees()) {
                    System.out.println(e.getEmpId() + " | " + e.getRoleLabel() + " | " + e.getName());
                }
            }
            case 5 -> {
                Employee e = findEmployeeOrPrint();
                if (e != null) e.viewProfile();
            }
            case 6 -> {
                Employee e = findEmployeeOrPrint();
                if (e != null) {
                    String email = readLine("New Email: ");
                    String phone = readLine("New Phone: ");
                    e.updateProfile(email, phone);
                }
            }
            case 7 -> {
                int id = readInt("Emp ID to remove: ");
                workForce.removeEmployee(id);
            }
            case 8 -> {
                Manager m = (Manager) findTypedEmployee(Manager.class, "Manager");
                Developer d = (Developer) findTypedEmployee(Developer.class, "Developer");
                if (m != null && d != null) m.manages(d);
            }
            case 9 -> {
                Developer d = (Developer) findTypedEmployee(Developer.class, "Developer");
                if (d != null) d.displayDashboard();
            }
            case 10 -> {
                Developer d = (Developer) findTypedEmployee(Developer.class, "Developer");
                if (d != null) d.updateTask();
            }
            case 11 -> {
                Manager m = (Manager) findTypedEmployee(Manager.class, "Manager");
                if (m != null) m.viewTeamStatus();
            }
            case 12 -> {
                Hr hr = (Hr) findTypedEmployee(Hr.class, "HR");
                if (hr != null) {
                    System.out.println("a) Recruit employee  b) Manage documents");
                    String sub = readLine("Choice (a/b): ");
                    if (sub.equalsIgnoreCase("a")) hr.addEmployee();
                    else hr.manageDocuments();
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // Returns {name, email, deptID} — shared fields prompted before the subtype-specific ones.
    private static String[] readCommonEmployeeFields() {
        String name = readLine("Name: ");
        String email = readLine("Email: ");
        String deptID = readLine("Dept ID: ");
        return new String[]{name, email, deptID};
    }

    @SuppressWarnings("unchecked")
    private static <T extends Employee> Employee findTypedEmployee(Class<T> type, String label) {
        int id = readInt(label + " Emp ID: ");
        Employee e = workForce.findEmployee(id);
        if (e == null || !type.isInstance(e)) {
            System.out.println("No matching " + label + " found for ID " + id + ".");
            return null;
        }
        return e;
    }

    private static Employee findEmployeeOrPrint() {
        int id = readInt("Emp ID: ");
        Employee e = workForce.findEmployee(id);
        if (e == null) System.out.println("Employee not found.");
        return e;
    }

    // ---------------- PAYROLL ----------------

    private static void payrollMenu() {
        System.out.println("\n-- Payroll --");
        System.out.println("1. Generate Payslip for Employee");
        System.out.println("2. Generate All Payslips");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                Employee e = findEmployeeOrPrint();
                if (e == null) return;
                String month = readLine("Month (e.g. July): ");
                double basic = readDouble("Basic Pay: ");
                double deductions = readDouble("Deductions: ");
                double attendancePct = readDouble("Attendance %: ");
                Payroll p = new Payroll(e, month, basic, deductions, attendancePct);
                workForce.getPayrolls().add(p);
                p.generatePaySlip();
            }
            case 2 -> workForce.generatePayrolls();
            default -> System.out.println("Invalid choice.");
        }
    }

    // ---------------- LEAVE ----------------

    private static void leaveMenu() {
        System.out.println("\n-- Leave Management --");
        System.out.println("1. Apply Leave");
        System.out.println("2. Cancel Leave");
        System.out.println("3. View Employee Leaves");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                Employee e = findEmployeeOrPrint();
                if (e == null) return;
                String reason = readLine("Reason: ");
                LocalDate from = readDate("From date (yyyy-MM-dd): ");
                LocalDate to = readDate("To date (yyyy-MM-dd): ");
                String type = readLine("Leave Type (Casual/Sick/Earned): ");
                Leave leave = new Leave(e, reason, from, to, type);
                leave.applyLeave();
            }
            case 2 -> {
                Employee e = findEmployeeOrPrint();
                if (e == null) return;
                int leaveId = readInt("Leave ID to cancel: ");
                e.getLeaves().stream().filter(l -> l.getLeaveId() == leaveId).findFirst()
                        .ifPresentOrElse(Leave::cancelLeave, () -> System.out.println("Leave not found."));
            }
            case 3 -> {
                Employee e = findEmployeeOrPrint();
                if (e == null) return;
                List<Leave> leaves = e.getLeaves();
                if (leaves.isEmpty()) System.out.println("No leave records.");
                leaves.forEach(System.out::println);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // ---------------- ATTENDANCE ----------------

    private static void attendanceMenu() {
        System.out.println("\n-- Attendance --");
        Employee e = findEmployeeOrPrint();
        if (e == null) return;
        LocalDate date = readDate("Date (yyyy-MM-dd): ");
        LocalTime checkIn = readTime("Check-In (HH:mm): ");
        LocalTime checkOut = readTime("Check-Out (HH:mm): ");
        Attendance a = new Attendance(e, date, checkIn, checkOut);
        workForce.markAttendance(a);
        a.calculatePercentage();
        a.calculateLeave();
    }

    // ---------------- SHIFT ----------------

    private static void shiftMenu() {
        System.out.println("\n-- Shift Management --");
        System.out.println("1. Create Shift");
        System.out.println("2. View Shift Duration & Headcount");
        System.out.println("3. Add Employee to Shift");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                String name = readLine("Shift Name: ");
                LocalTime start = readTime("Start Time (HH:mm): ");
                LocalTime end = readTime("End Time (HH:mm): ");
                Shift s = new Shift(name, start, end);
                workForce.getShifts().add(s);
                System.out.println("Created " + s);
            }
            case 2 -> {
                Shift s = findShiftOrPrint();
                if (s == null) return;
                s.getDuration();
                s.getTotalEmployee();
            }
            case 3 -> {
                Shift s = findShiftOrPrint();
                if (s == null) return;
                Employee e = findEmployeeOrPrint();
                if (e == null) return;
                s.addEmployee(e);
                System.out.println(e.getName() + " added to shift " + s.getShiftName() + ".");
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static Shift findShiftOrPrint() {
        int id = readInt("Shift ID: ");
        Shift s = workForce.getShifts().stream().filter(sh -> sh.getShiftId() == id).findFirst().orElse(null);
        if (s == null) System.out.println("Shift not found.");
        return s;
    }

    // ---------------- DOCUMENT ----------------

    private static void documentMenu() {
        System.out.println("\n-- Document Management --");
        System.out.println("1. Upload Document");
        System.out.println("2. View Document");
        System.out.println("3. Delete Document");
        int choice = readInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                String empId = readLine("Emp ID: ");
                String type = readLine("Document Type: ");
                String fileName = readLine("File Name: ");
                Document doc = new Document(empId, type, fileName);
                doc.uploadDocument();
                workForce.getDocuments().add(doc);
            }
            case 2 -> {
                Document doc = findDocumentOrPrint();
                if (doc != null) doc.viewDocument();
            }
            case 3 -> {
                Document doc = findDocumentOrPrint();
                if (doc != null && doc.deleteDocument()) workForce.getDocuments().remove(doc);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static Document findDocumentOrPrint() {
        int id = readInt("Document ID: ");
        Document doc = workForce.getDocuments().stream().filter(d -> d.getDocumentId() == id).findFirst().orElse(null);
        if (doc == null) System.out.println("Document not found.");
        return doc;
    }

    // ---------------- INPUT HELPERS ----------------

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(sc.nextLine().trim(), DATE_FMT);
            } catch (Exception ex) {
                System.out.println("Please use format yyyy-MM-dd.");
            }
        }
    }

    private static LocalTime readTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalTime.parse(sc.nextLine().trim(), TIME_FMT);
            } catch (Exception ex) {
                System.out.println("Please use format HH:mm.");
            }
        }
    }

    // ---------------- SAMPLE DATA ----------------

    private static void seedSampleData() {
        Department eng = new Department(1, "Engineering", "Chennai");
        Department hrDept = new Department(2, "Human Resources", "Bangalore");
        workForce.addDepartment(eng);
        workForce.addDepartment(hrDept);

        Developer dev = new Developer("Ravi Kumar", "ravi@company.com", "pwd", "9990001111", "1",
                "Backend Developer", "Payroll Module", 3, 2);
        Manager mgr = new Manager("Priya Sharma", "priya@company.com", "pwd", "9990002222", "1",
                "MGR-01", "Workforce Platform");
        Hr hrEmp = new Hr("Anita Rao", "anita@company.com", "pwd", "9990003333", "2", 501);

        workForce.addEmployee(dev);
        workForce.addEmployee(mgr);
        workForce.addEmployee(hrEmp);
        mgr.manages(dev);

        System.out.println("(Sample data loaded: 2 departments, 3 employees.)\n");
    }
}
