package com.jpm.test.jml;

import com.jpm.test.jml.domain.Message;

public interface MessageController {

	boolean acceptMessage(Message message);

	int getTotalSales();

	Double getTotalSales(String productId);

}
