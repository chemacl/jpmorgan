package com.jpm.test.jml.domain;

public class ProductType implements Comparable<ProductType>{
	private String id;

	public String getId() {
		return id;
	}

	public ProductType setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProductType other = (ProductType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProductType o) {
		if(id == null && o.getId() == null) {
			return 0;
		}
		if(id == null) {
			return 1;
		}
		if(o.getId() == null) {
			return -1;
		}
		return id.compareTo(o.getId());
	}
	
	
}
