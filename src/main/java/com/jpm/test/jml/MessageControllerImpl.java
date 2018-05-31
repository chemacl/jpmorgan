package com.jpm.test.jml;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.jpm.test.jml.domain.Adjustment;
import com.jpm.test.jml.domain.AdjustmentOperator;
import com.jpm.test.jml.domain.Message;
import com.jpm.test.jml.domain.MultipleSale;
import com.jpm.test.jml.domain.ProductType;
import com.jpm.test.jml.domain.Sale;
import com.jpm.test.jml.sale.SaleRecorder;
import com.jpm.test.jml.sale.SaleValidator;

class MessageControllerImpl implements MessageController {
	private final SaleRecorder saleRecorder;
	private final SaleValidator saleValidator;
	private long messageCount = 0;

	public MessageControllerImpl(SaleRecorder saleRecorder, SaleValidator saleValidator) {
		this.saleRecorder = saleRecorder;
		this.saleValidator = saleValidator;

		assert this.saleRecorder != null;
	}

	public boolean acceptMessage(Message message) {
		boolean ret = false;
		switch (message.getMessageType()) {
		case ADJUSTMENT:
			ret = handleAdjustment((Adjustment) message.getPayload());
			break;
		case MULTIPLE_SALES:
			ret = handleMultipleSales((MultipleSale) message.getPayload());
			break;
		case ONE_SALE:
			ret = handleSingleSale((Sale) message.getPayload());
			break;
		}
		if (ret) {
			messageCount++;
		}
		if (messageCount != 0 && messageCount % 10 == 0) {
			logSalesOfEachProductAndTotal();
		}
		if (messageCount != 0 && messageCount % 50 == 0) {
			// We are single threaded and therefore will block here
			// So we are unable to process anything else until we have
			// finished logging our adjustment report
			logAdjustmentReport();
			messageCount = 0; // reset to continue forever
		}
		return ret;
	}

	protected void logAdjustmentReport() {
		System.out.println("System paused - Generating Adjustment report");
		Map<ProductType, List<Sale>> adjustedSales = saleRecorder.getAdjustedSales();
		if (adjustedSales != null) {
			for (ProductType productType : adjustedSales.keySet()) {
				List<Sale> sales = adjustedSales.get(productType);
				int total = sales.size();
				System.out.println("Product :" + productType.getId() + " - Total " + total);
				for (Sale adjustedSale : sales) {
					for (Adjustment adjustment : adjustedSale.getAdjustments()) {
						System.out.println("\t"+adjustment);
					}
				}
			}
		}
	}

	protected void logSalesOfEachProductAndTotal() {
		Optional<Set<ProductType>> productTypes = saleRecorder.getProductTypes();
		if (productTypes.isPresent()) {
			for (ProductType productType : productTypes.get()) {
				int salesCount = saleRecorder.getTotalSalesCountByProduct(productType);
				Double totalValue = saleRecorder.getTotalSalesValueByProduct(productType);
				StringBuilder sb = new StringBuilder();
				sb.append("Product: ").append(productType.getId()).append(',').append("Total Sales:");
				sb.append(salesCount).append(", Total Value: ").append(totalValue);
				System.out.println(sb.toString());
			}
		} else {
			System.out.println("System error!!!!");
		}
	}

	private boolean handleSingleSale(Sale sale) {
		if (saleValidator != null) {
			if (!saleValidator.isValid(sale)) {
				return false;
			}
		}
		return saleRecorder.recordSale(sale);
	}

	private boolean handleMultipleSales(MultipleSale multipleSale) {
		int numberOfOccurrences = multipleSale.getNumberOfOccurrences();
		// Simple limiting here so we are not swamped
		if (numberOfOccurrences < 0 || numberOfOccurrences > MultipleSale.MAX_SALES) {
			return false;
		}
		for (int i = 0; i < numberOfOccurrences; i++) {
			handleSingleSale(multipleSale.getSale());
		}
		return true;
	}

	private boolean handleAdjustment(Adjustment adjustment) {
		ProductType productType = adjustment.getProductType();
		if (productType == null) {
			return false;
		}
		AdjustmentOperator operator = AdjustmentOperator.fromString(adjustment.getAdjustmentOperator());
		if (operator == null) {
			return false;
		}
		return saleRecorder.adjustSales(productType, operator, adjustment.getAmount());
	}

	@Override
	public int getTotalSales() {
		return saleRecorder.getTotalsSalesCount();
	}

	@Override
	public Double getTotalSales(String productId) {
		return saleRecorder.getTotalSalesByProductId(productId);
	}

}
