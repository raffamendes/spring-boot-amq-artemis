package com.redhat.example.amq.amqp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redhat.example.amq.core.Consumer;

@Component
public class AmqpConfig {

	@Value("${amqp.broker-url}")
	private String brokerUrl;

	@Value("${amqp.queue.name}")
	private String queueName;
	
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);


	public void sender(String message) throws JMSException {
		Connection connection = null;
		logger.info("camqp connection uri={}",brokerUrl);
		ConnectionFactory connectionFactory = new JmsConnectionFactory(brokerUrl);

		try {

			// Step 1. Create an amqp qpid 1.0 connection
			connection = connectionFactory.createConnection();

			// Step 2. Create a session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Step 3. Create a sender
			Queue queue = session.createQueue(queueName);
			MessageProducer sender = session.createProducer(queue);

			// Step 4. send a few simple message
			sender.send(session.createTextMessage(message));

			connection.start();

			// Step 5. create a moving receiver, this means the message will be removed from the queue
			MessageConsumer consumer = session.createConsumer(queue);

			// Step 7. receive the simple message
			TextMessage m = (TextMessage) consumer.receive(5000);
			System.out.println("message = " + m.getText());

		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				// Step 9. close the connection
				connection.close();
			}
		}

	}

}
