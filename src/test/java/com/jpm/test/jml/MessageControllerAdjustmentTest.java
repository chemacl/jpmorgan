package com.jpm.test.jml;

import org.junit.Assert;
import org.junit.Test;

import com.jpm.test.jml.domain.Message;
import com.jpm.test.jml.domain.MessageType;

public class MessageControllerAdjustmentTest extends AbstractControllerTest {

	@Test
	public void testAdjustmentsOneDecimalPlace() {
		testMultiplyAdjustmentToDecimalPlaces("1", 10.1d, 20, 0.1d, 1, 20.2d);
	}

	@Test
	public void testAdjustmentsTwoDecimalPlaces() {
		testMultiplyAdjustmentToDecimalPlaces("1", 10.1d, 20, 0.01d, 2, 2.02d);
	}

	@Test
	public void testAdjustmentsThreeDecimalPlaces() {
		testMultiplyAdjustmentToDecimalPlaces("1", 10.1d, 20, 0.001d, 3, 0.202d);
	}

	@Test
	public void testAdjustmentsFourDecimalPlaces() {
		testMultiplyAdjustmentToDecimalPlaces("1", 10.1d, 20, 0.0001d, 4, 0.0202d);
	}

	@Test
	public void testAdjustmentsFiveDecimalPlaces() {
		testMultiplyAdjustmentToDecimalPlaces("1", 10.1d, 20, 0.00001d, 5, 0.00202d);
	}

	@Test
	public void testAdjustmentsSixDecimalPlaces() {
		testMultiplyAdjustmentToDecimalPlaces("1", 10.1d, 20, 0.000001d, 6, 0.000202d);
	}

	@Test
	public void testAdjustmentsSevenDecimalPlaces() {
		testMultiplyAdjustmentToDecimalPlaces("1", 10.1d, 20, 0.0000001d, 7, 0.0000202d);
	}

	@Test
	public void testLogOfAjustmentsRecords() {
		MessageController control = new MessageControllerImpl(MessageControllerTestUtils.createDoNothingSaleRecord(),
				MessageControllerTestUtils.createAlwaysValidSale()) {
			protected void logAdjustmentReport() {
				logAdjustmentRecordCount++;
			}
			protected void logSalesOfEachProductAndTotal() {
				
			}
		};

		for(int i = 0  ; i < 101; i++) {
			Message message = MessageControllerTestUtils.createMessage(MessageType.MULTIPLE_SALES, "1", 1d, 1);
			Assert.assertTrue(control.acceptMessage(message));
		}
		Assert.assertTrue(logAdjustmentRecordCount == 2);
	}
	
	@Test
	public void test50() {
		for (int i = 0; i < 49; i++) {
			Message message = MessageControllerTestUtils.createMessage(MessageType.MULTIPLE_SALES, "1", 1d,
					1);
			Assert.assertTrue(controller.acceptMessage(message));
		}
		controller.acceptMessage(MessageControllerTestUtils.createAdjustmentMessage(MessageType.ADJUSTMENT, "1", 0.01, "-"));
		testAddSingleSale("1",1d);
	}
}
