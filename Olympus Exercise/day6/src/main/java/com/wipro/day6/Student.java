package com.wipro.day6;

 class Student {
	String name, id;

    Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String toString() {
        return name + " " + id;
    }
}
