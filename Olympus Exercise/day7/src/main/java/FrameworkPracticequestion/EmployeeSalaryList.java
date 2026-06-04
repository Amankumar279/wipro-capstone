package FrameworkPracticequestion;

import java.util.ArrayList;

public class EmployeeSalaryList {

    public static void main(String[] args) {

        // Store salaries
        ArrayList<Double> salaries = new ArrayList<>();

        salaries.add(25000.0);
        salaries.add(30000.0);
        salaries.add(40000.0);
        salaries.add(35000.0);
        salaries.add(50000.0);

        System.out.println("Original Salaries:");
        System.out.println(salaries);

        // Increase all salaries by 10%
        for (int i = 0; i < salaries.size(); i++) {

            double updatedSalary = salaries.get(i) + (salaries.get(i) * 0.10);

            salaries.set(i, updatedSalary);
        }

        System.out.println("\nUpdated Salaries (+10%):");
        System.out.println(salaries);
    }
}
