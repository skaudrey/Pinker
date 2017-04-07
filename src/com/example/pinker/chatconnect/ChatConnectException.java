package com.example.pinker.chatconnect;

public class ChatConnectException extends Exception {
	private static final long serialVersionUID = 1L;

	public ChatConnectException(String message) {
		super(message);
	}

	public ChatConnectException(String message, Throwable cause) {
		super(message, cause);
	}
}
