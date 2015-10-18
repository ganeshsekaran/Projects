package com.gsekaran;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class AsynchReceiver {

	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		props.put(Context.PROVIDER_URL, "localhost:1099");
		props.put(Context.URL_PKG_PREFIXES,
				"org.jboss.naming:org.jnp.interfaces");

		InitialContext iniCtx = new InitialContext(props);

		QueueConnectionFactory qcf = (QueueConnectionFactory) iniCtx
				.lookup("ConnectionFactory"); // step 1

		Queue que = (Queue) iniCtx.lookup("MessageQueue");
		Destination dest = (Destination) que;

		Connection connection = null;

		try {
			connection = qcf.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(dest);

			MessageListener listener = new MessageListenerImpl();
			consumer.setMessageListener(listener);

			connection.start();
			System.out.println("To end program, type Q or q, "
					+ "then <return>");
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);

			char answer = '\0';
			while (!((answer == 'q') || (answer == 'Q'))) {
				try {
					answer = (char) inputStreamReader.read();
				} catch (IOException e) {
					System.err.println("I/O exception: " + e.toString());
				}
			}
		} catch (JMSException e) {
			System.err.println("Exception occurred: " + e.toString());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	static class MessageListenerImpl implements MessageListener {

		public void onMessage(Message message) {
			TextMessage msg = null;

			try {
				if (message instanceof TextMessage) {
					msg = (TextMessage) message;
					System.out.println("Reading message: " + msg.getText());
				} else if(message instanceof ObjectMessage) {
					MessageEvent messageEvent = (MessageEvent) ((ObjectMessage) message).getObject();
					System.out.println("Reading message: " + messageEvent.getMessage());
				}
			} catch (JMSException e) {
				System.err.println("JMSException in onMessage(): "
						+ e.toString());
			} catch (Throwable t) {
				System.err
						.println("Exception in onMessage():" + t.getMessage());
			}
		}
	}
}
