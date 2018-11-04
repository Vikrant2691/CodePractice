package com.niyogin.kafka.controller;

import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com. niyogin.kafka.storage.MessageStorage;
import com. niyogin.kafka.services.KafkaProducer;
import com.niyogin.kafka.application.ApplicationData;

@RestController
public class KafkaRestController {
	
	
	@Autowired
	KafkaProducer producer;
	
	@Autowired
	MessageStorage storage;
	
	@Autowired
	ApplicationData application;

@RequestMapping(value = "/niyogin-ods/e-link", method = RequestMethod.POST)
public String kafkaRest(@RequestBody String payload,HttpServletRequest request) throws InterruptedException, ExecutionException {
	
	Integer appId= application.getAppName(request.getHeader("application"));
	String header= request.getHeader("application");
	producer.send(appId,header,payload);
	
	System.out.println(payload);
	return "Done";
}
}