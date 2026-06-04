package com.wipro.day2;

import java.util.Scanner;

public class MinNum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("enter the size of array");
		int size=sc.nextInt();
		int arr[]=new int[size];
		
		System.out.println("enter array elment");
		for(int i=0;i<arr.length;i++) {
			arr[i]=sc.nextInt();
		}
		int min=arr[0];
		for(int i=0;i<arr.length;i++) {
			if(min>arr[i]) {
				min=arr[i];
			}
		}
		
		System.out.println("your min element in array is "+min);
		
	}

}
