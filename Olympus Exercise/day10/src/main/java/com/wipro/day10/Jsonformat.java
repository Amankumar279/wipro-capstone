package com.wipro.day10;

public class Jsonformat {
    public static void main(String[] args) {

        Student s = new Student("Patukota", 101, 88);

        String details = """
                Student Details
                -------------------
                Name : %s
                RollNo : %d
                Marks : %.2f
                """.formatted(s.name, s.number, (double)s.marks);

        System.out.println(details);
    }
}

class Student {
    String name;
    int number;
    int marks;

    Student(String name, int number, int marks) {
        this.name = name;
        this.number = number;
        this.marks = marks;
    }
}