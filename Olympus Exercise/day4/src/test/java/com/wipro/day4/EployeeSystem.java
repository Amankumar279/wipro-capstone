package com.wipro.day4;

public class EployeeSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Manager e1 = new Manager("Aman", 50000);
	        Developer e2 = new Developer("Rahul", 40000);

	        e1.displayDetails();
	        e2.displayDetails();

	}

}

abstract class Employee {
    String name;
    double salary;

    // Constructor
    Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // Abstract method
    abstract double calculateBonus();

    // Display details
    void displayDetails() {
        System.out.println("Name   : " + name);
        System.out.println("Salary : ₹" + salary);
        System.out.println("Bonus  : ₹" + calculateBonus());
        System.out.println("----------------------");
    }
}

class Manager extends Employee {

    Manager(String name, double salary) {
        super(name, salary);
    }

    double calculateBonus() {
        return salary * 0.20;
    }
}

// Developer subclass
class Developer extends Employee {

    Developer(String name, double salary) {
        super(name, salary);
    }

    double calculateBonus() {
        return salary * 0.10;
    }
}

