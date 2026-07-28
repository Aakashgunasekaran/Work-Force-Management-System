package com.workforce.model;

public class Developer extends Employee {
    private String role;
    private String projectAssigned;
    private int completedProject;
    private int expLevel;

    // A Developer can be managed by a Manager (the "manages" association in the diagram).
    private Manager manager;

    public Developer(String name, String email, String password, String phone, String deptID,
                      String role, String projectAssigned, int completedProject, int expLevel) {
        super(name, email, password, phone, deptID);
        this.role = role;
        this.projectAssigned = projectAssigned;
        this.completedProject = completedProject;
        this.expLevel = expLevel;
    }

    public String getProjectAssigned() { return projectAssigned; }
    public void setProjectAssigned(String projectAssigned) { this.projectAssigned = projectAssigned; }
    public int getCompletedProject() { return completedProject; }
    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }

    @Override
    public String getRoleLabel() { return "Developer"; }

    @Override
    protected void printSpecificDetails() {
        System.out.println("Role            : " + role);
        System.out.println("Project Assigned: " + projectAssigned);
        System.out.println("Completed Proj. : " + completedProject);
        System.out.println("Exp Level       : " + expLevel);
        System.out.println("Manager         : " + (manager != null ? manager.getName() : "Unassigned"));
    }

    public void displayDashboard() {
        System.out.println("=== Dashboard: " + name + " ===");
        System.out.println("Current Project : " + projectAssigned);
        System.out.println("Completed Tasks : " + completedProject);
        System.out.println("Experience Level: " + expLevel + " yrs");
        System.out.println("Pending Leaves  : " + leaves.stream().filter(l -> "PENDING".equalsIgnoreCase(l.getStatus())).count());
    }

    public void updateTask() {
        this.completedProject++;
        System.out.println(name + " marked a task complete. Total completed: " + completedProject);
    }
}
