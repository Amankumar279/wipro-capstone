package com.wipro.day10;

public class PatternMatching {
	public static void main(String[] args) {

		// example1
		Object obj1 = "Hello";
		if (obj1 instanceof String  x) {
			System.out.println(x.length());
		}

		
		//example2 
		Object obj2= "hello";
		
		if(obj2 instanceof String str)
		System.out.println("String: "+str);
		else if (obj2 instanceof Integer num)
			System.out.println("Integer: "+num);
		else
			System.out.println("Unknown type");
		
		//example 3
		
     	Object obj3="Jaa";
		
		switch(obj3)
		{
		case String s -> System.out.println(s);
		case Integer i -> System.out.println(i);
		case null -> System.out.println("null value");
		default -> System.out.println("unknown type");
		}
		

		
		if(obj3 instanceof String str && str.length()>3)
		{
			System.out.println("Long string : "+str);
		}
		else
			System.out.println("shorter length : ");
	
}
