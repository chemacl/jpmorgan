package com.jpm.test.jml.domain;

public class SaleFactory {

	public static Sale createSale(final String productTypeId, final Double value) {
		return new SaleImpl(productTypeId, value);
	}
}
