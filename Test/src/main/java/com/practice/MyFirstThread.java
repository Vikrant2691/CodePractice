package com.practice;

public class MyFirstThread {
	
	static int counter=0;
	
	public static void main(String[] args) {

		
		new Thread(() -> {
			
			for(int i=0;i<100;i++)
				System.out.println(counter++);
			
		}).start();
		
		while(counter<40) {
			System.out.println("Not there yet");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("done");
	}
}
