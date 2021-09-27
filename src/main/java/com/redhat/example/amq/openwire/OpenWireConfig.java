package com.redhat.example.amq.openwire;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenWireConfig {
	
	
	@Value("${openwire.broker-url}")
	private String brokerUrl;

	@Value("${openwire.queue.name}")
	private String queueName;
	
	@Value("${amq.cluster.user}")
	private String brokerUser;
	
	@Value("${amq.cluster.pass}")
	private String brokerPass;
	
	private static final Logger logger = LoggerFactory.getLogger(OpenWireConfig.class);

	public void sender(String message) throws JMSException {
		 Connection connection = null;
	      try {
	         ConnectionFactory cf = new ActiveMQConnectionFactory(brokerUser,brokerPass,brokerUrl);
	         connection = cf.createConnection();
	         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	         Queue queue = session.createQueue(queueName);
	         MessageProducer producer = session.createProducer(queue);
	         TextMessage txtMessage = session.createTextMessage(message);
	         System.out.println("Sent message: " + txtMessage.getText());
	         producer.send(txtMessage);
	         MessageConsumer messageConsumer = session.createConsumer(queue);
	         connection.start();
	         TextMessage messageReceived = (TextMessage) messageConsumer.receive(5000);
	         logger.info("message received={}",messageReceived.getText());
	      } catch (JMSException e) {
			e.printStackTrace();
		} finally {
	         if (connection != null) {
	            connection.close();
	         }
	      }

	}
	
}
