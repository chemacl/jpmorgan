package com.jpm.test.jml.sale;

import com.jpm.test.jml.domain.Sale;

public class SaleValidatorFactory {

	public static SaleValidator createStandardValidator() {
		return new SaleValidator() {

			@Override
			public boolean isValid(Sale sale) {
				if(null == sale.getProductType() || null == sale.getValue()) {
					return false;
				}
				return true;
			}
			
		};
	}
}
