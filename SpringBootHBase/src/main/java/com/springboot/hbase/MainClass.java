package com.springboot.hbase;

import java.io.IOException;

public class MainClass {
	
	public static void main(String[] args) throws IOException {
		
		//System.setProperty("hadoop.home.dir", "D:/work/hadoop/");
		
		CustomerRepositoryNew.save("002", "Sandip", "400628", "Panvel", "true");
		
	}

}
