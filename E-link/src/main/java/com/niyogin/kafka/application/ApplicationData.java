package com.niyogin.kafka.application;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ApplicationData {

	private Map<String, Integer> appMap;

	  public ApplicationData() {
	    appMap = new HashMap<>();
	    appMap.put("hunter", 1);
	    appMap.put("cibil", 2);
	    appMap.put("crifcomm", 3);
	    appMap.put("crifcons", 4);
	    appMap.put("expcomm", 5);
	    appMap.put("expcons", 6);
	    appMap.put("ddupe", 7);
	    appMap.put("perfios", 8);
	  }
	  
	  public Integer getAppName(String appName){
		return appMap.get(appName);}
	
	
}
