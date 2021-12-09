package com.redhat.example.service;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String readAmqpMessage() throws JMSException{
		return amqp.consumer();
	}
	

	@GetMapping("/amqp/broken/consumer")
	public String readAmqpMessageBroken() throws JMSException{
		for(int i = 0; i<5; i++) {
			amqp.consumerBroken();
		}
		return "Sent to DLQ";
	}
	
	@GetMapping("/jms/{message}")
	public String jmsCore(@PathVariable String message) throws JMSException{
		producer.send(message);
		return "Sent Jms";
	}
	
	@GetMapping("/openwire")
	public String OpenWireMessage(@RequestParam(name = "message") String message) throws JMSException{
		openWire.sender(message);
		return "Sent OpenWire";
	}
	
	@GetMapping("/openwire/read/last")
	public String readOpenWireMessage() throws JMSException{
		return openWire.readLast();
	}
	

}
