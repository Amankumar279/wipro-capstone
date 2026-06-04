package com.wipro.day7;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Demo {

	public static void main(String[] args) {

		List<String> emplist = new ArrayList<String>();

		emplist.add("Aman");
		emplist.add("Saquib");
		emplist.add("Abhishek");

		System.out.println(emplist);

		List list = new ArrayList(); // Object
		list.add(24);
		list.add("Aman");
		list.add(true);
		list.add(34.5f);
		
		System.out.println(list);
	}
}