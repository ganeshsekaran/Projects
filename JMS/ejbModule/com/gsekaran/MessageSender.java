package com.gsekaran;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class MessageSender {

	public static void main(String[] args) throws Exception {

		Connection connection = null;
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		props.put(Context.PROVIDER_URL, "localhost:1099");
		props.put(Context.URL_PKG_PREFIXES,
				"org.jboss.naming:org.jnp.interfaces");

		try {

			InitialContext iniCtx = new InitialContext(props);
			QueueConnectionFactory qcf = (QueueConnectionFactory) iniCtx
					.lookup("ConnectionFactory"); // step 1
			Queue que = (Queue) iniCtx.lookup("MessageQueue");
			Destination dest = (Destination) que;

			int i = 0;
			connection = qcf.createConnection(); // step 2
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE); // step 3
			MessageProducer producer = session.createProducer(dest); // step 4

			for (i = 0; i < 10; i++) {
				TextMessage message = session
						.createTextMessage("This is text message : " + (i + 1)); // step
																					// 5

				System.out.println("Sending message: " + message.getText());
				producer.send(message); // step 6
			}

			for (i = 0; i < 10; i++) {
				MessageEvent messageEvent = new MessageEvent(1,
						"This is Object message : " + (i + 1));
				ObjectMessage message = session.createObjectMessage();
				message.setObject(messageEvent);
				System.out.println("Sending message: "
						+ messageEvent.getMessage());
				producer.send(message); // step 6
			}

		} catch (JMSException e) {
			System.err.println("Exception occurred: " + e.toString());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					System.err.println("Exception occurred: " + e.toString());
				}
			}
		}
	}
}
