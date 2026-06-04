package com.wipro.day10;

public class Recordclass {
    public static void main(String[] args) {

        Student s1 = new Student("Aman", 12);
        Student s2 = new Student("saquib", 13);

        System.out.println(s1.name() + " - " + s1.rollno());
        System.out.println(s2.name() + " - " + s2.rollno());

        s1.display();

        Employee e1 = new Employee(12, "Aman", 23485);
        System.out.println(e1.name() + " - " + e1.salary() + " - " + e1.eid());

        Shape obj = new Shape();
        obj.area(3);
    }
}

record Student(String name, int rollno) {
    void display() {
        System.out.println("working!");
    }
}

record Employee(int eid, String name, int salary) {
}

record Shape() {
    void area(int x) {
        System.out.println(x * x);
    }
}