package com.redhat.example.amq.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	@JmsListener(destination = "add-test::exampleQueue", containerFactory = "jmsListenerContainerFactory")
	public void printMessage(String message) {
		logger.info("receiving message='{}'",message);
	}
}
