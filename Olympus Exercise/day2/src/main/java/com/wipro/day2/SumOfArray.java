package com.wipro.day2;

import java.util.Scanner;

public class SumOfArray {

	public static void main(String[] args) {
		System.out.println("enter the size of array");
		Scanner sc =new Scanner(System.in);
		int size=sc.nextInt();
		int arr[]=new int[size];
		
		System.out.println("enter array elment");
		for(int i=0;i<arr.length;i++) {
			arr[i]=sc.nextInt();
		}
		int sum=0;
		for(int i=0;i<arr.length;i++) {
			sum=sum+arr[i];
		}
		
		System.out.println("sum of array element "+sum);

	}

}
