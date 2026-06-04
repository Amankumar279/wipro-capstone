package com.wipro.day2;

import java.util.Scanner;

public class EvenNumberInArray {

	public static void main(String[] args) {
		

		System.out.println("enter the size of array");
	Scanner sc =new Scanner(System.in);
	int size=sc.nextInt();
	int arr[]=new int[size];
	
	System.out.println("enter array elment");
	for(int i=0;i<arr.length;i++) {
		arr[i]=sc.nextInt();
		}

	System.out.print("Even numbers: ");
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] % 2 == 0) {
            System.out.print(arr[i] + " ");
        }
    }
	}	
}