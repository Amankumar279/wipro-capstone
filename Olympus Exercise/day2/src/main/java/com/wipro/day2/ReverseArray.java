package com.wipro.day2;

import java.util.Scanner;

public class ReverseArray {

	public static void main(String[] args) {
		
		
			System.out.println("enter the size of array");
			Scanner sc =new Scanner(System.in);
			int size=sc.nextInt();
			int arr[]=new int[size];
			
			System.out.println("enter array elment");
			for(int i=0;i<arr.length;i++) {
				arr[i]=sc.nextInt();
				}
			
			
			System.out.println("reverse array is");
			for(int k=arr.length-1;k>=0;k--) {
				System.out.println(arr[k]);
				
			}
				
	}
}