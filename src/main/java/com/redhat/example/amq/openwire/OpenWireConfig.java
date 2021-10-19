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
	
	private ConnectionFactory getConnectionFactory() {
		return new ActiveMQConnectionFactory(brokerUser, brokerPass, brokerUrl);
	}

	public void sender(String message) throws JMSException {
		 Connection connection = null;
	      try {
	         connection = getConnectionFactory().createConnection();
	         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	         Queue queue = session.createQueue(queueName);
	         MessageProducer producer = session.createProducer(queue);
	         TextMessage txtMessage = session.createTextMessage(message);
	         System.out.println("Sent message: " + txtMessage.getText());
	         producer.send(txtMessage);
	         connection.start();
	      } catch (JMSException e) {
			e.printStackTrace();
		} finally {
	         if (connection != null) {
	            connection.close();
	         }
	      }

	}
	
	public String readLast() throws JMSException {
		Connection connection = null;
		try {
			connection = getConnectionFactory().createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(queueName);
			connection.start();
			MessageConsumer consumer = session.createConsumer(queue);
			TextMessage message = (TextMessage) consumer.receive(5000);
			logger.info("receiving message={}",message.getText());
			return message.getText();
		} catch (JMSException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(connection != null) {
				connection.close();
			}
		}
	}
	
}
