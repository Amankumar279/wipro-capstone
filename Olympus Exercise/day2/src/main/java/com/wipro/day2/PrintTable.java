package com.wipro.day2;

import java.util.Scanner;

public class PrintTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner(System.in);
		System.out.println("enter the no for table to print");
		int n=input.nextInt();
		
		for(int i=1;i<=10;i++) {
			System.out.println(n+" * "+i+"="+n*i);
		}

	}

}
