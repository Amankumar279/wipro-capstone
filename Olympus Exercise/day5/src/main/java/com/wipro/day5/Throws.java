package com.wipro.day5;

public class Throws {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Throws t=new Throws();
		try {
t.check();
	}catch(Exception e){
		System.out.println(e.getMessage());
	}}
void check() throws InterruptedException{
	Thread.sleep(2000);
}
}
