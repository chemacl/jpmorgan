package com.jpm.test.jml;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageControllerTest3 {
	private MessageController salesManager;

	@Before
	public void setUp() throws Exception {
		salesManager = MessageControllerFactory.create(MessageControllerTestUtils.createDoNothingSaleRecord(), null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = NullPointerException.class)
	public void handleNullMessage() {
		salesManager.acceptMessage(null);
	}

	@Test
	public void handleInvalidSaleRecordedWHenNoSaleValidationImplemented() {
		// There is no sale validation so this passes here
		Assert.assertTrue(salesManager.acceptMessage(MessageControllerTestUtils.createEmptySale()));

		// Make sure strategy is being called
		salesManager = MessageControllerFactory.create(MessageControllerTestUtils.createDoNothingSaleRecord(),
				MessageControllerTestUtils.createAlwaysInValidSale());
		// No sale validator so this it will be recorded
		Assert.assertFalse(salesManager.acceptMessage(MessageControllerTestUtils.createEmptySale()));
	}

	@Test
	public void handleValidSaleNotRecordedWithAlwaysValidSaleValidator() {
		salesManager = MessageControllerFactory.create(MessageControllerTestUtils.createDoNothingSaleRecord(),
				MessageControllerTestUtils.createAlwaysValidSale());
		Assert.assertTrue(salesManager.acceptMessage(MessageControllerTestUtils.createEmptySale()));
	}
}
