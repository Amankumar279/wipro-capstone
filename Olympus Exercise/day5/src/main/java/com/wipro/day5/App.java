package com.wipro.day5;


public class App 

{
	
	static void vote(int age) throws InavlidAgeException {
		if(age<18) {
			throw new  InavlidAgeException("not eligible for voting");
		}
		System.out.println("u can vote");
	}
    public static void main( String[] args )
    {
        try {
        	vote(16);
        } catch(InavlidAgeException e) {
        	System.out.println(e.getMessage());
        }
    }
}
