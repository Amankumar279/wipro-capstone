package com.wipro.day2;

import java.util.Scanner;

public class Factorial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner(System.in);
		
		System.out.println("enter the number to calculate the factorial");
		
		int num=input.nextInt();
		int fact=1;
		for(int i=1;i<=num;i++) {
			fact=fact*i;
		}
		
		System.out.println("Factorial of num is " +fact);

	}

}
