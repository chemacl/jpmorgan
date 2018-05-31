package com.jpm.test.jml.domain;

public class AdjustmentImpl implements Adjustment{
	private final ProductType productType;
	private final String adjustmentOperator;
	private final Double amount;
	
	public AdjustmentImpl(ProductType productType,String adjustmentOperator,Double amount) {
		this.productType = productType;
		this.adjustmentOperator = adjustmentOperator;
		this.amount = amount;
	}

	public ProductType getProductType() {
		return productType;
	}

	public String getAdjustmentOperator() {
		return adjustmentOperator;
	}

	public Double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Adjustment [productId=" + productType.getId() + ", adjustmentOperator=" + adjustmentOperator + ", amount="
				+ amount + "]";
	}


}
