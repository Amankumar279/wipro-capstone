package com.wipro.day2;

import java.util.Scanner;

/*when we to store more than 1 value in continuous manner then we can use array 
 * but is of fix size we can't change the size of the array
 */

public class Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s=new Scanner(System.in);
		
		/* Declaration of array*/
		int arr[]=new int[5];
		
		for(int i=0;i<arr.length;i++) {
			arr[i]=s.nextInt();
		}
		for(int i=0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}

	}

}
