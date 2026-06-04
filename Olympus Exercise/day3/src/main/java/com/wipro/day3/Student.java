package com.wipro.day3;

public class Student {
	String Name;
	int Rollno,marks;
	
	Student(String Name,int Rollno,int marks){
		this.Name=Name;
		this.Rollno=Rollno;
		this.marks=marks;
	}
	
	 String calcgrade() {
		 if(marks>80) {
			return"Grade A";
		 } else if (marks < 80 && marks >70) {
			 return "Grade B";
		 } else if(marks<70&& marks>55){
			 return "Grade c";
		 } else {
			return "Fail";
		 }
		
	}
	 
	 void displayinfo() {
		 System.err.println("name "+ Name+"\n rollNo "+Rollno+"\n marks "+marks+calcgrade());
	 }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student obj=new Student ("Aman",70,87);
		obj.displayinfo();
		
		

	}

	
}
