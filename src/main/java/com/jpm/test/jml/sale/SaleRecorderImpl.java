package com.jpm.test.jml.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.jpm.test.jml.domain.Adjustment;
import com.jpm.test.jml.domain.AdjustmentImpl;
import com.jpm.test.jml.domain.AdjustmentOperator;
import com.jpm.test.jml.domain.ProductType;
import com.jpm.test.jml.domain.Sale;

public class SaleRecorderImpl implements SaleRecorder {

	private final SalesCache salesCache;

	public SaleRecorderImpl() {
		salesCache = new SalesCache();
	}

	static final class SalesCache {
		private int totalSalesCount = 0;
		private Map<ProductType, List<Sale>> sales = new HashMap<>();

		public boolean add(Sale sale) {
			List<Sale> list = sales.get(sale.getProductType());
			if (list == null) {
				sales.put(sale.getProductType(), list = new ArrayList<>());
			}
			if ((totalSalesCount + 1) > MAXIUM_TOTAL_SALES) {
				return false;
			}
			list.add(sale);
			totalSalesCount++;
			return true;
		}

		public Optional<Set<ProductType>> getProductTypes() {
			Set<ProductType> set = sales.keySet();
			return Optional.ofNullable(set);
		}

		public Optional<List<Sale>> getSalesByProductType(ProductType productType) {
			List<Sale> list = sales.get(productType);
			return Optional.ofNullable(list);
		}

		public int getSalesCount() {

			return totalSalesCount;
		}

		public boolean updateSalesByProduct(ProductType productType, List<Sale> adjustedSales) {
			if (productType != null && sales != null) {
				sales.put(productType, adjustedSales);
				return true;
			}
			return false;
		}

	}

	public boolean recordSale(Sale sale) {
		if (!salesCache.add(sale)) {
			System.out.println("Total cache limit of " + getTotalSalesCount() + " reached");
			return false;
		}
		// System.out.println(sale);
		return true;
	}

	public int getTotalSalesCount() {
		return salesCache.getSalesCount();
	}

	public Map<ProductType, BigDecimal> getProductTypeSummaryValues() {
		final Optional<Set<ProductType>> productTypes = salesCache.getProductTypes();
		final Map<ProductType, BigDecimal> totals = new HashMap<>();

		if (!productTypes.isPresent()) {
			return totals;
		}
		for (ProductType productType : productTypes.get()) {
			Optional<List<Sale>> sales = salesCache.getSalesByProductType(productType);
			Double total = 0d;
			if (sales.isPresent()) {
				for (Sale sale : sales.get()) {
					total += sale.getValue();
				}
			}
		}
		return totals;
	}

	@Override
	public boolean adjustSales(ProductType productType, AdjustmentOperator operator, Double amount) {
		Optional<List<Sale>> sales = salesCache.getSalesByProductType(productType);
		if (sales.isPresent() && operator != null && amount != null) {
			Adjustment adjustment = new AdjustmentImpl(productType, operator.getTag(), amount);
			List<Sale> adjustedSales = new ArrayList<>();
			for (int idx = 0; idx < sales.get().size(); idx++) {
				Sale sale = sales.get().get(idx);
				sale.addAdjustment(adjustment);
				Sale adjustedSale = null;
				switch (operator) {
				case ADD:
					adjustedSale = addValue(sale, adjustment);
					break;
				case MULTIPLY:
					adjustedSale = multiplyValue(sale, adjustment);
					break;
				case SUBTRACT:
					adjustedSale = subtractValue(sale, adjustment);
					break;
				}
				adjustedSales.add(idx, adjustedSale);
			}
			if (adjustedSales.size() > 0) {
				// Wipes out originals
				return salesCache.updateSalesByProduct(productType, adjustedSales);
			}
		}
		return true;
	}

	private Sale subtractValue(final Sale sale, final Adjustment adjustment) {
		Adjustment adj = new Adjustment() {

			@Override
			public ProductType getProductType() {
				return adjustment.getProductType();
			}

			@Override
			public String getAdjustmentOperator() {
				return adjustment.getAdjustmentOperator();
			}

			@Override
			public Double getAmount() {
				return sale.getValue() - adjustment.getAmount();
			}

		};
		return sale.adjust(adj);
	}

	private Sale multiplyValue(final Sale sale, final Adjustment adjustment) {
		Adjustment adj = new Adjustment() {

			@Override
			public ProductType getProductType() {
				return adjustment.getProductType();
			}

			@Override
			public String getAdjustmentOperator() {
				return adjustment.getAdjustmentOperator();
			}

			@Override
			public Double getAmount() {
				return sale.getValue() * adjustment.getAmount();
			}

		};
		return sale.adjust(adj);
	}

	private Sale addValue(final Sale sale, final Adjustment adjustment) {
		Adjustment adj = new Adjustment() {

			@Override
			public ProductType getProductType() {
				return adjustment.getProductType();
			}

			@Override
			public String getAdjustmentOperator() {
				return adjustment.getAdjustmentOperator();
			}

			@Override
			public Double getAmount() {
				return sale.getValue() + adjustment.getAmount();
			}

		};
		return sale.adjust(adj);
	}

	@Override
	public int getTotalsSalesCount() {
		return salesCache.getSalesCount();
	}

	@Override
	public Double getTotalSalesByProductId(String productId) {
		ProductType productType = new ProductType().setId(productId);
		Optional<List<Sale>> sales = salesCache.getSalesByProductType(productType);
		Double total = 0d;
		if (sales.isPresent()) {
			for (Sale sale : sales.get()) {
				total += sale.getValue();
			}
		}
		return total;
	}

	@Override
	public Optional<Set<ProductType>> getProductTypes() {
		return salesCache.getProductTypes();
	}

	@Override
	public Integer getTotalSalesCountByProduct(ProductType productType) {
		Optional<List<Sale>> sales = salesCache.getSalesByProductType(productType);
		if (sales.isPresent()) {
			return sales.get().size();
		}
		return 0;
	}

	@Override
	public Double getTotalSalesValueByProduct(ProductType productType) {
		Optional<List<Sale>> sales = salesCache.getSalesByProductType(productType);
		if (sales.isPresent()) {
			Double total = 0d;
			for (Sale sale : sales.get()) {
				total += sale.getValue();
			}
			return total;
		}
		return 0d;
	}

	@Override
	public Map<ProductType, List<Sale>> getAdjustedSales() {
		Map<ProductType, List<Sale>> salesBreakdown = new HashMap<>();
		Optional<Set<ProductType>> productTypes = getProductTypes();
		if (productTypes.isPresent()) {
			for (ProductType productType : productTypes.get()) {
				Optional<List<Sale>> productSales = getSalesByProductType(productType);
				if (productSales.isPresent()) {
					List<Sale> sales = new ArrayList<>();
					for (Sale sale : productSales.get()) {
						if (sale.getAdjustments() != null) {
							sales.add(sale);
						}
					}
					// Try and make tamper proof
					salesBreakdown.put(productType, Collections.unmodifiableList(sales));
				}
			}
		}
		return salesBreakdown;
	}

	private Optional<List<Sale>> getSalesByProductType(ProductType productType) {
		return salesCache.getSalesByProductType(productType);
	}
}
