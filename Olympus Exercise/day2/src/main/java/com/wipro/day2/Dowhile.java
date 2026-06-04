package com.wipro.day2;

import java.util.Scanner;

/*diff in do  and do while is that in do while first it will execute the program then it will check*/
/*but in while loop it will check the condition then it will execute the code*/



public class Dowhile {
	 public static void main( String args[] )
	    {
	        Scanner  s=new Scanner(System.in);  
	        boolean choice=true;
	        
	        
	        do {
	        	System.out.println("enter 2 numbers");
	    	int  num1= s.nextInt();
	        int  num2= s.nextInt();
	        
	        System.out.println(" Welcome to Java Calculator ");
	        System.out.println("1. Addition ");
	        System.out.println("2. Sub ");
	        System.out.println("3. Multiplication ");
	        System.out.println("4. Division ");
	        int operation=s.nextInt();
	          if(operation==1)
	        	  System.out.println(num1+num2);
	          else if (operation==2)
	        	  System.out.println(num1-num2);
	          else if (operation==3)
	        	  System.out.println(num1*num2);
	          else if (operation==4)
	        	  System.out.println(num1/num2);
	          else
	        	  System.out.println("invalid choice");
	        System.out.println("do you want to coontinue?(true/false)");
	        choice=s.nextBoolean();
	    }
	    while(choice);    
	    
	    }

}
