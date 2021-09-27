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
import com.redhat.example.amq.openwire.OpenWireConfig;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private AmqpConfig amqp;
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private OpenWireConfig openWire;
	
	@PostMapping("/post")
	public void send(@RequestBody String message) {
		producer.send(message);
	}
	
	
	@GetMapping("/amqp/{message}")
	public String amqpMessage(@PathVariable String message) throws JMSException{
		amqp.sender(message);
		return "Sent Amqp";
	}
	
	@GetMapping("/amqp/read/last")
	public String readAmqpMessage(@PathVariable String message) throws JMSException{
		return amqp.consumer();
	}
	
	@GetMapping("/jms/{message}")
	public String jmsCore(@PathVariable String message) throws JMSException{
		producer.send(message);
		return "Sent Jms";
	}
	
	@GetMapping("/openwire/{message}")
	public String OpenWireMessage(@PathVariable String message) throws JMSException{
		openWire.sender(message);
		return "Sent OpenWire";
	}
	
	@GetMapping("/openwire/read/last")
	public String readOpenWireMessage() throws JMSException{
		return "Sent OpenWire";
	}
	

}
