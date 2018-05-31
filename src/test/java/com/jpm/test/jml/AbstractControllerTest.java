package com.jpm.test.jml;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.jpm.test.jml.domain.Message;
import com.jpm.test.jml.domain.MessageType;

public class AbstractControllerTest {

	protected MessageController controller;
	protected int logSalesOfEachProductAndTotalCount;
	protected int logAdjustmentRecordCount;
	@Before
	public void setUp() throws Exception {
		controller = MessageControllerFactory.create();
		logSalesOfEachProductAndTotalCount = 0;
		logAdjustmentRecordCount = 0;
	}

	@After
	public void tearDown() throws Exception {
	}

	protected void testAddSingleSale(String productId, Double amount) {
		Message message = MessageControllerTestUtils.createMessage(MessageType.ONE_SALE, productId, amount);
		Assert.assertTrue(controller.acceptMessage(message));
	}
	
	protected void testAddMultipleSingleSales(String productId, Double amount, int numberOfSales) {
		for (int i = 0; i < numberOfSales; i++) {
			testAddSingleSale(productId,amount);
			Assert.assertTrue(controller.getTotalSales() == i + 1);
		}
	}
	
	protected void testMultipleSaleAmounts(String productId, Double initialAmount, int occurrences, int decimalPoints) {
		Message message = MessageControllerTestUtils.createMessage(MessageType.MULTIPLE_SALES, productId, initialAmount,
				occurrences);
		Assert.assertTrue(controller.acceptMessage(message));
		Double totalSales = MessageControllerTestUtils.round(controller.getTotalSales(productId), decimalPoints);
		Double mult = MessageControllerTestUtils.round((initialAmount * occurrences * 1d), decimalPoints);
		Assert.assertTrue(totalSales.equals(mult));
	}
	
	public void testMultiplyAdjustmentToDecimalPlaces(String productId, Double initialAmount, int occurrences,
			Double adjustmentAmount, int decimalPlaces, Double total) {
		// Load some sales
		testMultipleSaleAmounts(productId, initialAmount, occurrences, 4);
		controller.acceptMessage(MessageControllerTestUtils.createAdjustmentMessage(MessageType.ADJUSTMENT, productId, adjustmentAmount, "*"));
		Double totalSales = MessageControllerTestUtils.round(controller.getTotalSales(productId), decimalPlaces);
		Assert.assertTrue(totalSales.equals(total));
	}

}
