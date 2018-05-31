package com.jpm.test.jml.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SaleImpl implements Sale, Cloneable {
	private final ProductType productType;
	private final Double value;
	private final Double initialAmount;
	private Long timeOfSale;
	private List<Adjustment> adjustments;
	
	public SaleImpl(String productId, Double value) {
		this.timeOfSale = System.currentTimeMillis();
		productType = new ProductType().setId(productId);
		this.value = value;
		this.initialAmount = value;
	}
	
	public ProductType getProductType() {
		return productType;
	}

	public Double getValue() {
		return value;
	}
	public Long getTimeOfSale() {
		return timeOfSale;
	}
	
	public int compareTo(Sale o) {
		if(getTimeOfSale() < o.getTimeOfSale()) {
			return -1;
		}
		if(getTimeOfSale() > o.getTimeOfSale()) {
			return 1;
		}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productType == null) ? 0 : productType.hashCode());
		result = prime * result + ((timeOfSale == null) ? 0 : timeOfSale.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaleImpl other = (SaleImpl) obj;
		if (productType == null) {
			if (other.productType != null)
				return false;
		} else if (!productType.equals(other.productType))
			return false;
		if (timeOfSale == null) {
			if (other.timeOfSale != null)
				return false;
		} else if (!timeOfSale.equals(other.timeOfSale))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public void addAdjustment(Adjustment adjustment) {
		if(adjustments == null) {
			adjustments = new ArrayList<>();
		}
		adjustments.add(adjustment);
	}
	
	public SaleImpl adjust(Adjustment adjustment) {
		SaleImpl clone = new SaleImpl(adjustment.getProductType().getId(), adjustment.getAmount());
		
		clone.timeOfSale = timeOfSale;
		clone.adjustments = new ArrayList<>(adjustments);
		return clone;
	}

	@Override
	public List<Adjustment> getAdjustments() {
		if(adjustments == null) {
			adjustments = new ArrayList<>();
		}
		return Collections.unmodifiableList(adjustments);
	}

	
}
