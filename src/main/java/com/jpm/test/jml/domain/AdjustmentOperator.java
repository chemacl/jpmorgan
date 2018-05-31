package com.jpm.test.jml.domain;

public enum AdjustmentOperator {
	ADD("+"), SUBTRACT("-"), MULTIPLY("*");

	private final String tag;

	private AdjustmentOperator(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public static AdjustmentOperator fromString(String tag) {
		for (AdjustmentOperator operator : values()) {
			if (operator.tag.equals(tag)) {
				return operator;
			}
		}
		return null;
	}
}
