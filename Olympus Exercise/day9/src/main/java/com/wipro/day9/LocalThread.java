package com.wipro.day9;

public class LocalThread {
	static ThreadLocal<Integer> local=new ThreadLocal<Integer>();

    public static void main(String[] args)
    {

        Thread obj= new Thread( () ->   
        {
                    local.set(2);
         System.out.println(local.get());          
        }		);
    	
        obj.start();
    	
        

        Thread obj2= new Thread( () ->   
        {
                    local.set(22);
         System.out.println(local.get());          
        }		);
    	
        obj2.start();
    	
    }
}

