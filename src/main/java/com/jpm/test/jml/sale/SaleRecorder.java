package com.jpm.test.jml.sale;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.jpm.test.jml.domain.AdjustmentOperator;
import com.jpm.test.jml.domain.ProductType;
import com.jpm.test.jml.domain.Sale;

public interface SaleRecorder {

	public static final Integer MAXIUM_TOTAL_SALES = 10000;
	
	boolean recordSale(Sale sale);

	boolean adjustSales(ProductType productType, AdjustmentOperator operator, Double amount);

	int getTotalsSalesCount();

	Double getTotalSalesByProductId(String productId);

	Optional<Set<ProductType>> getProductTypes();

	Integer getTotalSalesCountByProduct(ProductType productType);

	Double getTotalSalesValueByProduct(ProductType productType);

	Map<ProductType, List<Sale>> getAdjustedSales();
}
