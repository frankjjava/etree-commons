/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.etree.commons.core.CommonsSupportConstants.COLLECTION_TYPE;
import com.etree.commons.core.dto.EtreeRequestContext;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class AbstractBase extends AbstractConfigParams implements BaseService {

	@Override
	public <T> T fetchData(EtreeRequestContext requestDto) {
		throw new EtreeCommonsException("", "Not implemented!");
	}
		
	protected Object convertJsonToPojo(EtreeRequestContext requestDto) {
		Object request = convertJsonToPojo(requestDto.getRequest(), requestDto.getRequestType(), requestDto.getCollectionType());
		return request;
	}
	
	protected Object convertJsonToPojo(Object value, Class<?> type, COLLECTION_TYPE collectionType) {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType javaType = null;
		if (COLLECTION_TYPE.LIST == collectionType) {
			javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);
		} else if (COLLECTION_TYPE.MAP == collectionType) {
			if (value instanceof String) {
				javaType = objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, type);
			} else if (value instanceof LinkedHashMap) {
				javaType = objectMapper.getTypeFactory().constructMapType(HashMap.class, LinkedHashMap.class, type);
			}		
		}
		Object result = null;
		if (value instanceof String) {
			try {
				if (javaType != null) {
					result = objectMapper.readValue((String) value, javaType);
				} else {
					result = objectMapper.readValue((String) value, type);
				}
			} catch (IOException e) {
				throw new EtreeCommonsException("", e);
			}
		} else {
			if (javaType != null) {
				result = objectMapper.convertValue(value, javaType);
			} else {
				result = objectMapper.convertValue(value, type);
			}
		}
		return result;
	}

	
}

 