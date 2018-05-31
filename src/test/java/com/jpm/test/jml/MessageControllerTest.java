package com.jpm.test.jml;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.jpm.test.jml.domain.Message;
import com.jpm.test.jml.domain.MessageType;

public class MessageControllerTest extends AbstractControllerTest {
	
	@Test(expected = NullPointerException.class)
	public void testAcceptNullMessage() {
		controller.acceptMessage(null);
	}

	@Test(expected = NullPointerException.class)
	public void testNullMessageType() {
		Message msg = MessageControllerTestUtils.createNullMessageType();
		controller.acceptMessage(msg);
	}

	@Test
	public void testNullPayloadMessageTypes() {
		for (MessageType messageType : MessageType.values()) {
			Message msg = MessageControllerTestUtils.createMessage(messageType, null);
			try {
				controller.acceptMessage(msg);
				fail("NPE was not thrown");
			} catch (NullPointerException e) {
			}
		}
	}

	@Test
	public void testInvalidObjectPayloadMessageTypes() {
		for (MessageType messageType : MessageType.values()) {
			Message msg = MessageControllerTestUtils.createMessage(messageType, Integer.valueOf(100));
			try {
				controller.acceptMessage(msg);
				fail("Class cast exception was not thrown");
			} catch (ClassCastException e) {
			}
		}
	}

}
