package com.jpm.test.jml.domain;

public interface Message {
	MessageType getMessageType();
	Object getPayload();
}
