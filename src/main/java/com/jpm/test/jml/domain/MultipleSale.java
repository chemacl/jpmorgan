package com.jpm.test.jml.domain;

public interface MultipleSale {
	public static final Integer MAX_SALES = 1000;
	
	Sale getSale();
	int getNumberOfOccurrences();

}
