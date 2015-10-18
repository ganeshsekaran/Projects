package com.gsekaran;

import java.io.Serializable;

public class MessageEvent implements Serializable {

	private int MessageStatus;
	private String message;

	public MessageEvent() {
		super();
	}

	public MessageEvent(int messageStatus, String message) {
		super();
		MessageStatus = messageStatus;
		this.message = message;
	}

	public int getMessageStatus() {
		return MessageStatus;
	}

	public void setMessageStatus(int messageStatus) {
		MessageStatus = messageStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
