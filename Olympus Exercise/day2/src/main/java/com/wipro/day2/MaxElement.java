package com.wipro.day2;

import java.util.Scanner;

public class MaxElement {

	public static void main(String[] args) {
Scanner sc=new Scanner(System.in);
		
		System.out.println("enter the size of array");
		int size=sc.nextInt();
		int arr[]=new int[size];
		
		System.out.println("enter array elment");
		for(int i=0;i<arr.length;i++) {
			arr[i]=sc.nextInt();
		}
		int max=arr[0];
		for(int i=0;i<arr.length;i++) {
			if(max<arr[i]) {
				max=arr[i];
			}
		}
		
		System.out.println("your max element in array is "+max);

	}

}
