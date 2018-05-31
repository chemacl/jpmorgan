package com.jpm.test.jml;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.jpm.test.jml.domain.Adjustment;
import com.jpm.test.jml.domain.AdjustmentOperator;
import com.jpm.test.jml.domain.Message;
import com.jpm.test.jml.domain.MessageType;
import com.jpm.test.jml.domain.MultipleSale;
import com.jpm.test.jml.domain.ProductType;
import com.jpm.test.jml.domain.Sale;
import com.jpm.test.jml.domain.SaleFactory;
import com.jpm.test.jml.sale.SaleRecorder;
import com.jpm.test.jml.sale.SaleValidator;

public class MessageControllerTestUtils {

	public static SaleRecorder createDoNothingSaleRecord() {
		return new SaleRecorder() {

			public boolean recordSale(Sale sale) {
				return true;
			}

			@Override
			public boolean adjustSales(ProductType productType, AdjustmentOperator operator, Double amount) {
				return false;
			}

			@Override
			public int getTotalsSalesCount() {
				return 0;
			}

			@Override
			public Double getTotalSalesByProductId(String productId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Optional<Set<ProductType>> getProductTypes() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer getTotalSalesCountByProduct(ProductType productType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Double getTotalSalesValueByProduct(ProductType productType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<ProductType, List<Sale>> getAdjustedSales() {
				// TODO Auto-generated method stub
				return null;
			}

			
		};
	}
	public static Message createEmptySale() {
		return new Message() {

			public MessageType getMessageType() {
				return MessageType.ONE_SALE;
			}

			public Object getPayload() {
				return SaleFactory.createSale(null, null);
			}
			
		};
	}

	public static Message createMessage(final  MessageType messageType,final String productTypeId, final double value ) {
		return new Message() {

			public MessageType getMessageType() {
				return MessageType.ONE_SALE;
			}

			public Object getPayload() {
				return SaleFactory.createSale(productTypeId, value);
			}
			
		};
	}
	public static SaleValidator createAlwaysValidSale() {
		return new SaleValidator() {

			public boolean isValid(Sale sale) {
				return true;
			}
			
		};
	}
	public static SaleValidator createAlwaysInValidSale() {
		return new SaleValidator() {

			public boolean isValid(Sale sale) {
				return false;
			}
			
		};
	}
	
	public static Message createMessage(final MessageType messageType, final Object payload) {
		return new Message() {

			@Override
			public MessageType getMessageType() {
				return messageType;
			}

			@Override
			public Object getPayload() {
				return payload;
			}
			
		};
	}
	
	public static Message createMessage(final MessageType mt, final String pid, final double value, final int occurrences) {
		return new Message() {

			@Override
			public MessageType getMessageType() {
				return mt;
			}

			@Override
			public Object getPayload() {
				return new MultipleSale() {

					@Override
					public Sale getSale() {
						return SaleFactory.createSale(pid, value);
					}

					@Override
					public int getNumberOfOccurrences() {
						return occurrences;
					}
					
				};
			}
			
		};
	}
	
	public static Message createAdjustmentMessage(final MessageType mt, final String pid, final Double amount,
			final String operation) {
		return new Message() {

			@Override
			public MessageType getMessageType() {
				return mt;
			}

			@Override
			public Object getPayload() {
				return new Adjustment() {

					@Override
					public ProductType getProductType() {
						return new ProductType().setId(pid);
					}

					@Override
					public String getAdjustmentOperator() {
						return operation;
					}

					@Override
					public Double getAmount() {
						return amount;
					}

				
				};
			}
			
		};
	}
	
	public static Message createNullMessageType() {
		return new Message() {

			@Override
			public MessageType getMessageType() {
				return null;
			}

			@Override
			public Object getPayload() {
				return null;
			}

		};
	}
	
	public static double round(Double value, int places) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
