package com.practice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConcurrencyExample {
	
	private static int counter = 0;
	
	public static void main(String[] args) {
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		Future<Integer> result1= service.submit(()->10+11);
		
		try {
			System.out.println(result1.get());
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Future<?> result= service.submit(()->{for(int i=0;i<100;i++) {counter++;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}});
		
		try {
			result.get(1, TimeUnit.SECONDS);
			System.out.println("Reached");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			System.out.println("TimeOut");
		}
	}
}
