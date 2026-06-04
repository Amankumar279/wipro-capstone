package com.wipro.day2;

import java.util.Scanner;

public class CalculatorusingSwitch {

	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		int a=sc.nextInt();
		int b=sc.nextInt();
		System.out.print("Enter operator (+, -, *, /): ");
		char op=sc.next().charAt(0);
		int result;
		
		switch(op) {
		case '+':
            result = a + b; 
            System.out.println(result);
            break;
            
		case '-':
            result = a - b; 
            System.out.println(result);
            break;
            
		case '*':
            result = a * b; 
            System.out.println(result);
            break;
            
		case '/':
            result = a / b; 
            System.out.println(result);
            break;
            
		default:
            System.out.println("Invalid input!");
            
        
		}
		
		
		

	}

}
