package com.jpm.test.jml.sale;

public class SaleRecorderFactory {

	public static SaleRecorder createStandardSaleRecorder() {
		return new SaleRecorderImpl();
	}

}
