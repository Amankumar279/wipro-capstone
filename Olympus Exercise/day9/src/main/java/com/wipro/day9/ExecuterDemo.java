package com.wipro.day9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuterDemo {
	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(2);
		//for single thread we use ExecutorService service = Executors.newSingleThreadExecutor();

		service.submit(() -> System.out.println("Task1:- " + Thread.currentThread().getName()));
		service.submit(() -> System.out.println("Task2:- " + Thread.currentThread().getName()));
		service.submit(() -> System.out.println("Task3:- " + Thread.currentThread().getName()));

		service.shutdown();
	}

}
