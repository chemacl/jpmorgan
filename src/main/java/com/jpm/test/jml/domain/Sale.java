package com.jpm.test.jml.domain;

import java.util.List;

public interface Sale extends Comparable<Sale>{
	Long getTimeOfSale();
	ProductType getProductType();
	Double getValue();
	Sale adjust(Adjustment adjustment);
	List<Adjustment> getAdjustments();
	void addAdjustment(Adjustment adjustment);
}
