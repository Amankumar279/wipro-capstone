package com.wipro.day2;

import java.util.Scanner;

public class SumOfN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner(System.in);
		
		System.out.println("enter the value");
		
		int N=input.nextInt();
		int sum=0;
		for(int i=1;i<=N;i++) {
			sum=sum+i;
			/*(n*(n+1)/2*/
		}
		
		System.out.println("Sum of N number is "+sum);

	}

}
