package com.wipro.day4;

//Abstract class Product
abstract class Product {
 String name;
 double price;

 Product(String name, double price) {
     this.name = name;
     this.price = price;
 }

 // Abstract method
 abstract double getPrice();
}

//Interface Discount
interface Discount {
 double applyDiscount();
}

//Electronics class
class Electronics extends Product implements Discount {

 Electronics(String name, double price) {
     super(name, price);
 }

 double getPrice() {
     return price;
 }

 public double applyDiscount() {
     return price - (price * 0.10);   // 10% discount
 }
}

//Clothing class
class Clothing extends Product implements Discount {

 Clothing(String name, double price) {
     super(name, price);
 }

 double getPrice() {
     return price;
 }

 public double applyDiscount() {
     return price - (price * 0.20);   // 20% discount
 }
}

//Main class
public class OnlineShoppingSystem {
 public static void main(String[] args) {

     Electronics e1 = new Electronics("Laptop", 50000);
     Clothing c1 = new Clothing("Shirt", 2000);

     System.out.println("Electronics Product: " + e1.name);
     System.out.println("Original Price: ₹" + e1.getPrice());
     System.out.println("Discounted Price: ₹" + e1.applyDiscount());

     System.out.println("----------------------");

     System.out.println("Clothing Product: " + c1.name);
     System.out.println("Original Price: ₹" + c1.getPrice());
     System.out.println("Discounted Price: ₹" + c1.applyDiscount());
 }
}

/*com.wipro.JavaBasics.abstraction;

public class Demo {

	public static void main(String[] args) {
		
		Student obj=new Student("sakshi","123"); //50 students
		obj.updateCollege();// UPDATING COLLEGE TO ABC
		obj.display();
	
		Student.show();
	
		
		
		Student obj1=new Student(" neha","126"); //50 students
		obj1.display();
	}
	
	
}

class Student
{
	static void show()
	{
		System.out.println("class static method is working");
	}
	
	
	
	 String name,rollno; //50 students  // non static variable  or instance variable
	static String college="xyz";   // 1 copy
	
	public Student(String name, String rollno) {
		super();
		this.name = name;
		this.rollno = rollno;
	}

	
	void updateCollege()
	{
		college="ABC";
	}

	 void display()
	{
		System.out.println(name+" "+rollno+" "+college);
	}
}*/