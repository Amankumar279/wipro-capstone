package com.wipro.day5;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExecptionlHandling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i=1;i<=10;i++)
		{
			try {
				
			if(i==5)
			{
				Scanner s=new Scanner(System.in);
			//	int a=s.nextInt(); //
				i=i/0; // infinity   // risk code
			}
			
			}
			catch(ArithmeticException e)
			{
				System.out.println(e.getMessage());
			}
			catch(InputMismatchException e)
			{
				System.out.println(e.getMessage());
			}
			System.out.println(i);
		}

	}

}


