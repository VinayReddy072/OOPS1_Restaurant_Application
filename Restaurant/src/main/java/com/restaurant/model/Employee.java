package com.restaurant.model;

public class Employee extends Person {
    private String employeeId;
    private double salary;

    // Chain to the full constructor to avoid duplication
    public Employee(String name, int age, String employeeId, double salary) {
        this(name, age, null, employeeId, salary);
    }

    public Employee(String name, int age, String email, String employeeId, double salary) {
        super(name, age, email);
        if (employeeId == null)
            employeeId = "";
        if (salary < 0)
            throw new IllegalArgumentException("Salary must be non-negative");
        this.employeeId = employeeId;
        this.salary = salary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0)
            throw new IllegalArgumentException("Salary must be non-negative");
        this.salary = salary;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Employee ID: " + this.employeeId);
        System.out.println("Salary: $" + this.salary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + getName() + "', " +
                "age=" + getAge() + ", " +
                "employeeId='" + employeeId + "', " +
                "salary=" + salary + "}";
    }
}