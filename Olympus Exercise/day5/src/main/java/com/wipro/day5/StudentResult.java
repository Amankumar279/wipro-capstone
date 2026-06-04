package com.wipro.day5;

import java.util.Scanner;

public class StudentResult {

	
	static void checkmarks(int marks) throws InvalidMarksException{
		if(marks<0||marks>100) {
			throw new InvalidMarksException("plzz put the correct marks");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 Scanner sc = new Scanner(System.in);

	        try {

	            System.out.print("Enter Marks of Subject 1: ");
	            int m1 = sc.nextInt();
	            checkmarks(m1);

	            System.out.print("Enter Marks of Subject 2: ");
	            int m2 = sc.nextInt();
	            checkmarks(m2);

	            System.out.print("Enter Marks of Subject 3: ");
	            int m3 = sc.nextInt();
	            checkmarks(m3);

	            int total = m1 + m2 + m3;
	            double avg = total / 3.0;

	            System.out.println("Total Marks = " + total);
	            System.out.println("Average = " + avg);

	            if (m1 >= 35 && m2 >= 35 && m3 >= 35) {
	                System.out.println("Result = Pass");
	            } else {
	                System.out.println("Result = Fail");
	            }

	        } catch (InvalidMarksException e) {
	            System.out.println(e.getMessage());

	        } catch (Exception e) {
	            System.out.println("Invalid Input");
	        }

	        sc.close();
	    }
	}
	


