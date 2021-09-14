package com.redhat.example.amq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class Producer {
	
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void send(String message) {
		logger.info("sending message='{}'",message);
		jmsTemplate.convertAndSend("exampleQueue",message);
	}
}
