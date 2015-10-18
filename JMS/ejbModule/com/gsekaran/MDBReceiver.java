package com.gsekaran;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: MessageReceiver
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"), @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "MessageQueue")
		}, 
		mappedName = "MessageQueue")
public class MDBReceiver implements MessageListener {

    /**
     * Default constructor. 
     */
    public MDBReceiver() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage msg = (TextMessage) message;
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
