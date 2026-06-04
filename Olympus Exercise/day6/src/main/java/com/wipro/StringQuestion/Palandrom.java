package com.wipro.StringQuestion;

public class Palandrom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str="car";
		 String reverse =
	                new StringBuilder(str).reverse().toString();

	        System.out.println(str.equalsIgnoreCase(reverse)); 

	}

}
