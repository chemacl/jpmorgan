package com.jpm.test.jml;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.jpm.test.jml.domain.Message;
import com.jpm.test.jml.domain.MessageType;
import com.jpm.test.jml.sale.SaleRecorder;

public class MessageControllerSingleSaleTest extends AbstractControllerTest {
	
	@Test
	public void testSingleSaleTotals() {
		testAddMultipleSingleSales("1",2d, 20);
	}

	
	@Test 
	public void testLogMessagePrintedEveryTenMessages() {
		MessageController control = new MessageControllerImpl(MessageControllerTestUtils.createDoNothingSaleRecord(), MessageControllerTestUtils.createAlwaysValidSale()) {
			protected void logSalesOfEachProductAndTotal() {
				logSalesOfEachProductAndTotalCount++;
			}
		};
		
		Message message = MessageControllerTestUtils.createEmptySale();
		for (int i = 0; i < 10; i++) {
			Assert.assertTrue(control.acceptMessage(message));
		}
		Assert.assertTrue(logSalesOfEachProductAndTotalCount == 1);
		for (int i = 0; i < 100; i++) {
			Assert.assertTrue(control.acceptMessage(message));
		}
		Assert.assertTrue(logSalesOfEachProductAndTotalCount == 11);
	}
	
	@Test
	public void testSingleSaleAmounts() {
		Message message = MessageControllerTestUtils.createEmptySale();
		Assert.assertFalse(controller.acceptMessage(message));
		message = MessageControllerTestUtils.createMessage(MessageType.ONE_SALE, "1", 2l);
		for (int i = 0; i < 10; i++) {
			Assert.assertTrue(controller.acceptMessage(message));
			float totalSales = controller.getTotalSales("1").floatValue();
			BigDecimal mult = new BigDecimal((i + 1) * 1f + "").multiply(new BigDecimal(2f + ""));
			float tot = mult.floatValue();
			Assert.assertTrue(totalSales == tot);
		}
	}

	@Test
	public void testExhaustSaleCacheViaSingleSale() {
		for (int i = 0; i < SaleRecorder.MAXIUM_TOTAL_SALES; i++) {
			testAddSingleSale("1",1d);
			Assert.assertTrue(controller.getTotalSales() == i + 1);
		}
		// Over the limit
		Message message = MessageControllerTestUtils.createMessage(MessageType.ONE_SALE, "2", 100);
		Assert.assertFalse(controller.acceptMessage(message));
	}
}
