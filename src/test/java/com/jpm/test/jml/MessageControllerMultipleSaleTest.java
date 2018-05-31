package com.jpm.test.jml;

import org.junit.Assert;
import org.junit.Test;

import com.jpm.test.jml.domain.Message;
import com.jpm.test.jml.domain.MessageType;
import com.jpm.test.jml.sale.SaleRecorder;

public class MessageControllerMultipleSaleTest extends AbstractControllerTest {

	@Test
	public void testMultipleSaleAmountsForMultipleProductTypes() {
		testMultipleSaleAmounts("1", 10.1d, 20, 4);
		testMultipleSaleAmounts("2", 10.19d, 20, 5);
		// We have an accuracy problem with 6 or more decimal points
		// Reverted to using Double
		Assert.assertTrue(controller.getTotalSales() == 40);
		testMultipleSaleAmounts("3", 0.0000041d, 1, 6);
		Assert.assertTrue(controller.getTotalSales() == 41);
		testMultipleSaleAmounts("4", 0.0000004d, 1, 7);
		testMultipleSaleAmounts("5", 0.00000004d, 1, 8);
		testMultipleSaleAmounts("6", 0.000000004d, 1, 9);
	}
	
	@Test
	public void testExhaustSaleCacheViaMultipleSingleSale() {
		for (int i = 0; i < SaleRecorder.MAXIUM_TOTAL_SALES; i++) {
			testAddSingleSale("1",1d);
			Assert.assertTrue(controller.getTotalSales() == i + 1);
		}
		// And one more for tipping point
		Message message = MessageControllerTestUtils.createMessage(MessageType.ONE_SALE, "1", 1);
		Assert.assertFalse(controller.acceptMessage(message));
	}
	
	
}
