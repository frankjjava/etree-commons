package com.etree.commons.core.utils.jackson.json;

import com.etree.commons.core.CommonsSupportConstants.DATE_FORMAT;

public class ObjectMapperContext {

	private boolean isSerializeEmptyAlso; 
	private boolean isForJaxb; 
	private DATE_FORMAT dateFormatter;
	
	public ObjectMapperContext(boolean isSerializeEmptyAlso, boolean isForJaxb, DATE_FORMAT dateFormatter) {
		this.isSerializeEmptyAlso = isSerializeEmptyAlso;
		this.isForJaxb = isForJaxb;
		this.dateFormatter = dateFormatter;
	}
	public boolean isSerializeEmptyAlso() {
		return isSerializeEmptyAlso;
	}
	public boolean isForJaxb() {
		return isForJaxb;
	}
	public DATE_FORMAT getDateFormatter() {
		return dateFormatter;
	}

	@Override
	public boolean equals(Object obj) {
		ObjectMapperContext newObjectMapperContext = (ObjectMapperContext) obj;
		if (newObjectMapperContext.isSerializeEmptyAlso == this.isSerializeEmptyAlso
				&& newObjectMapperContext.isForJaxb == this.isForJaxb 
				&& newObjectMapperContext.dateFormatter == this.dateFormatter) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash += 31 * (hash + (isSerializeEmptyAlso ? 0 : 1));
		hash += 31 * (hash + (isForJaxb() ? 0 : 1));
		if (dateFormatter != null) {
			hash += dateFormatter.hashCode();
		}
		return hash;
	}	
}
