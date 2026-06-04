package com.wipro.day10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThread {
	public static void main(String[] args) {
		
		Runnable task= () -> System.out.println("hello thread is working!");
		
		Thread vt=Thread.ofVirtual().start(task);
		
		
		
		for(int i=1;i<=10;i++)
		{
			Thread.ofVirtual().start(
					() -> System.out.println("New virtual thread is created!"+i)
					);
		}
		
		
		
		ExecutorService service=Executors.newVirtualThreadPerTaskExecutor();
		
		//service.submit
	}
}
