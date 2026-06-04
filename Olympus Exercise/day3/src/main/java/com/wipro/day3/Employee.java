package com.wipro.day3;

import java.util.Scanner;

// Class Employee
class Employee {
    String name;
    double basicSalary;

    // Constructor
    Employee(String name, double basicSalary) {
        this.name = name;
        this.basicSalary = basicSalary;
    }

    // Calculate HRA (20%)
    double calculateHRA() {
        return basicSalary * 0.20;
    }

    // Calculate DA (10%)
    double calculateDA() {
        return basicSalary * 0.10;
    }

    // Calculate Total Salary
    double calculateTotalSalary() {
        return basicSalary + calculateHRA() + calculateDA();
    }

    // Display Salary Breakdown
    void displaySalary() {
        System.out.println("\n===== Salary Breakdown =====");
        System.out.println("Employee Name : " + name);
        System.out.println("Basic Salary  : ₹" + basicSalary);
        System.out.println("HRA (20%)     : ₹" + calculateHRA());
        System.out.println("DA (10%)      : ₹" + calculateDA());
        System.out.println("Total Salary  : ₹" + calculateTotalSalary());
    }


// Main Class

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input employee details
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        double salary = sc.nextDouble();

        // Create object
        Employee emp = new Employee(name, salary);

        // Display result
        emp.displaySalary();

        sc.close();
    }
}