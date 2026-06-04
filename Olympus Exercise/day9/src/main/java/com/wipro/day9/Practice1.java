package com.wipro.day9;

public class Practice1 extends Thread{
public static void main(String[] args) {
	Practice1 p1=new Practice1();
	p1.setName("a1");
	p1.start();

Practice1 p2=new Practice1();
	p2.setName("a2");
	p2.start();


	
}


void reverseCounting() {
	for (int i = 10; i >= 1; i--)
		System.out.println(i + " - " + currentThread().getName());
}
void count() {
	for (int i =1; i <= 10; i++)
	System.out.println(i+"-"+currentThread().getName());
}

public void run() {
	if(currentThread().getName().equals("a1"))reverseCounting();
	else count();
}
}
