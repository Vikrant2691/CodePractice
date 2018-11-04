package com.niyogin.kafka.services;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.niyogin.kafka.application.ApplicationData;

@Service
public class KafkaProducer {
	private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
	
	@Autowired
	ApplicationData application;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${kafka.topic}")
	String kafkaTopic;
	
	public void send(Integer partitionId,String key,String message) throws InterruptedException, ExecutionException {
	    log.info("sending data='{}'", message);
	    //kafkaTemplate.send(kafkaTopic, message);
	    ListenableFuture<SendResult<String, String>> status = kafkaTemplate.send(kafkaTopic, partitionId, key, message);
	    
	    System.out.println(status.get());
	}
}
