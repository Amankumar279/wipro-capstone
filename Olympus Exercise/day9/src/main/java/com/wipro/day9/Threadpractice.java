package com.wipro.day9;

public class Threadpractice extends Thread {
public static void main(String[] args) {
		
		
	Threadpractice d1=new Threadpractice();  //creating a thread1
		  d1.setName("t1");
		  d1.start(); // starting a thread 1
		  Threadpractice d2=new Threadpractice();  //creating a thread2
		  d2.setName("t2");
		  d2.start(); // starting a thread 2

	}
	
	void printTable(int num)
	{
		for(int i=1;i<=10;i++)
		{
			System.out.println(num+" * "+i+" = "+(num*i)+" - "+currentThread().getName());
			
		}
	}
	
	
	
	public void run()   // running the thread
	{
		
		printTable(3);
	}

}

