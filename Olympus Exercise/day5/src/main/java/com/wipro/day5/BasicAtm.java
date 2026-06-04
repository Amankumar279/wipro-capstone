package com.wipro.day5;

import java.util.Scanner;

public class BasicAtm {
     static int balance=5000;
     static int pin=1234;
     
     void checkpin(int userpin)throws AtmException {
    	 if(userpin!=pin) {
    		 throw new AtmException("your pin is in correct");
    	 }
     }
     
     void checkBalance() {
    	 System.out.println("your balace is "+balance);
     }
     
     void deposit(double amount) throws AtmException {
    	 if(amount<=0) {
    		 throw new AtmException ("amount is invalid");
    	 }
    	 balance += amount;
    	   System.out.println("Deposit Successful");
    	   System.out.println("Available Balance: " + balance);
     }
     
     void withdraw(double amount)throws  AtmException {
    	 if(amount>balance) {
    		 throw new  AtmException("insufficent balance");
    	 }
    	 if(amount<0) {
    		 throw new  AtmException("plz enter valid amount");
    	 }
    	 
    	 balance-=amount;
    	 System.out.println("Now available balance is "+balance);
     }
     
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BasicAtm at=new BasicAtm();
		Scanner sc = new Scanner(System.in);
		

	    try {
	        System.out.print("Enter ATM PIN: ");
	        int userPin = sc.nextInt();

	        at.checkpin(userPin);

	        int choice;

	        do {
	            System.out.println("\n1.Deposit");
	            System.out.println("2.Withdraw");
	            System.out.println("3.Check Balance");
	            System.out.println("4.Exit");

	            choice = sc.nextInt();

	            switch(choice) {

	                case 1:
	                    at.deposit(sc.nextDouble());
	                    break;

	                case 2:
	                    at.withdraw(sc.nextDouble());
	                    break;

	                case 3:
	                    at.checkBalance();
	                    break;

	                case 4:
	                    System.out.println("Thank You");
	                    break;

	                default:
	                    System.out.println("Invalid Choice");
	            }

	        } while(choice != 4);

	    } catch (AtmException e) {
	        System.out.println(e.getMessage());
	    }

	    sc.close();
	}
}

