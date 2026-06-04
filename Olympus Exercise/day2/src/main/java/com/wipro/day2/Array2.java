package com.wipro.day2;

import java.util.Scanner;

/*example of string array*/

public class Array2 {
	public static void main(String arg[]) {
		Scanner sc=new Scanner(System.in);
		System.out.println("how many language do you know");
		
		int size=sc.nextInt();
		
		String language[]=new String[size];
		System.out.println("enter your language");
		for(int i=0;i<language.length;i++) {
			language[i]=sc.next();
		}
		
		for(int i=0;i<language.length;i++) {
			System.out.println(language[i]);
					
		}
		
		
		
		
		
		
	}

}
