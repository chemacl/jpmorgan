package com.jpm.test.jml.domain;

public interface Adjustment {
	ProductType getProductType();
	String getAdjustmentOperator();
	Double getAmount();
}
