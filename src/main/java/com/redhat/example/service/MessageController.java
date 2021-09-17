package com.redhat.example.service;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.example.amq.amqp.AmqpConfig;
import com.redhat.example.amq.core.Producer;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private AmqpConfig amqpProducer;
	
	@Autowired
	private Producer producer;
	
	@PostMapping("/post")
	public void send(@RequestBody String message) {
		producer.send(message);
	}
	
	
	@GetMapping("/amqp/{message}")
	public String amqpMessage(@PathVariable String message) throws JMSException{
		amqpProducer.sender(message);
		return "Sent Amqp";
	}
	
	@GetMapping("/jms/{message}")
	public String jmsCore(@PathVariable String message) throws JMSException{
		producer.send(message);
		return "Sent Jms";
	}

}
