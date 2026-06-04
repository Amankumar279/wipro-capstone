package com.wipro.day10;

public class Sealedclass {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Shape s1=new Circle();
		s1.area();
		Shape s2=new Rectangle();
		s2.area();

      
	}

}


sealed abstract class Shape permits Circle,Rectangle
{
	public abstract void area();
}


// A sealed class is a class that restricts inheritance to a fixed set of classes.


final class Circle extends Shape
{

	
	public void area() {
	System.out.println(3.14*2.4*2.4);
		
	}
	
}

final class Rectangle extends Shape
{


	public void area() {
		System.out.println(3*6);
		
	}
	
}

