package com.jpm.test.jml;

import com.jpm.test.jml.sale.SaleRecorder;
import com.jpm.test.jml.sale.SaleRecorderFactory;
import com.jpm.test.jml.sale.SaleValidator;
import com.jpm.test.jml.sale.SaleValidatorFactory;

public class MessageControllerFactory {

	public static MessageController create(SaleRecorder saleRecorder, SaleValidator saleValidator) {
		return new MessageControllerImpl(saleRecorder, saleValidator);
	}

	public static MessageController create() {
		return create(SaleRecorderFactory.createStandardSaleRecorder(), SaleValidatorFactory.createStandardValidator());
	}
}
