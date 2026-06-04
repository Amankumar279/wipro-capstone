package com.wipro.day2;

import java.util.Scanner;

public class LargestOf3Num {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("enter your 1st no");
		int a=sc.nextInt();
		System.out.println("enter your 2st no");
		int b=sc.nextInt();
		System.out.println("enter your 3st no");
		int c=sc.nextInt();
		if(a>=b && a>=c) {
			System.out.println("larger no is "+a);
		} else if(b>=a && b>=c) {
			System.out.println("larger no is "+b);
		} else {
		
			System.out.println("larger no is "+c);
	}

}
}
