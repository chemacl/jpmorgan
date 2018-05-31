package com.jpm.test.jml;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.jpm.test.jml.sale.SaleRecorder;
import com.jpm.test.jml.sale.SaleValidator;

public class MessageControllerTest2 {
	
	@Test(expected = AssertionError.class)
	public void setNullMessageProcessingStrategy() {
		SaleRecorder saleRecorder = null;
		SaleValidator saleValidator = null;
		MessageControllerFactory.create(saleRecorder, saleValidator);
	}


	@Test(expected = AssertionError.class)
	public void setNullSaleRecordertrategy() {
		SaleRecorder saleRecorder = null;
		SaleValidator saleValidator = null;
		MessageControllerFactory.create(saleRecorder, saleValidator);
	}

	@Test(expected = AssertionError.class)
	public void testNullSaleRecorderStrategyExceptionMessage() {
			SaleRecorder saleRecorder = null;
			SaleValidator saleValidator = null;
			MessageControllerFactory.create(saleRecorder, saleValidator);
	}

	@Test
	public void testValidCreation() {
		try {
			SaleRecorder saleRecorder = MessageControllerTestUtils.createDoNothingSaleRecord();
			SaleValidator saleValidator = null;
			MessageControllerFactory.create(saleRecorder, saleValidator);
		} catch (Exception e) {
			fail("Exceptions of any sort should not be thrown");
		}
	}
}
