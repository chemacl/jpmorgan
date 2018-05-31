package com.jpm.test.jml.sale;

public class SalesManagerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String reason;
	
	public SalesManagerException() {
		
	}
	public SalesManagerException(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
}
