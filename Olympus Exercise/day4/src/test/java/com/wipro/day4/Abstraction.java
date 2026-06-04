package com.wipro.day4;

public class Abstraction {

public static void main(String[] args) {
		
		Student obj=new Student("Aman","123"); //50 students
		obj.updateCollege();// UPDATING COLLEGE TO ABC
		obj.display();
	
		Student.show();
	
		
		
		Student obj1=new Student("diksha","126"); //50 students
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

}
