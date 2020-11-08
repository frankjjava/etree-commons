package com.etree.commons.core.utils.jackson.json;

@Deprecated
public class ObjectMapperContext {

	private boolean isSerializeEmptyAlso; 
	private boolean isForJaxb; 
	
	public ObjectMapperContext(boolean isSerializeEmptyAlso, boolean isForJaxb) {
		this.isSerializeEmptyAlso = isSerializeEmptyAlso;
		this.isForJaxb = isForJaxb;
	}
	public boolean isSerializeEmptyAlso() {
		return isSerializeEmptyAlso;
	}
	public boolean isForJaxb() {
		return isForJaxb;
	}

	@Override
	public boolean equals(Object obj) {
		ObjectMapperContext newObjectMapperContext = (ObjectMapperContext) obj;
		if (newObjectMapperContext.isSerializeEmptyAlso == this.isSerializeEmptyAlso
				&& newObjectMapperContext.isForJaxb == this.isForJaxb) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash += 31 * (hash + (isSerializeEmptyAlso ? 0 : 1));
		hash += 31 * (hash + (isForJaxb() ? 0 : 1));
		return hash;
	}	
}
