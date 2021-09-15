package com.redhat.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.example.amq.Producer;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private Producer producer;
	
	@PostMapping("/post")
	public void send(@RequestBody String message) {
		producer.send(message);
	}
	
	
	@GetMapping("/hello")
	public String hello(){
		producer.send("teste");
		return "hello";
	}

}
