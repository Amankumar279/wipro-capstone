package com.wipro.day4;

import java.util.Scanner;

class Account {
    String name;
    double balance;

    Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    void credit(double amount) {
        balance += amount;
        System.out.println("Amount Credited: " + amount);
    }

    void debit(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Amount Debited: " + amount);
        } else {
            System.out.println("Insufficient Balance");
        }
    }

    void checkBalance() {
        System.out.println("Current Balance: " + balance);
    }
}

class SavingsAccount extends Account {
    SavingsAccount(String name, double balance) {
        super(name, balance);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        SavingsAccount acc = new SavingsAccount(name, balance);

        int choice;
        do {
            System.out.println("\n1. Credit");
            System.out.println("2. Debit");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to credit: ");
                    double credit = sc.nextDouble();
                    acc.credit(credit);
                    break;

                case 2:
                    System.out.print("Enter amount to debit: ");
                    double debit = sc.nextDouble();
                    acc.debit(debit);
                    break;

                case 3:
                    acc.checkBalance();
                    break;

                case 4:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 4);

        sc.close();
    }
}
