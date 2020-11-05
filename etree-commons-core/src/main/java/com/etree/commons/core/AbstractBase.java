/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.commons.core.CommonsSupportConstants.COLLECTION_TYPE;
import com.etree.commons.core.dto.Error;
import com.etree.commons.core.dto.Errors;
import com.etree.commons.core.dto.MessageDto;
import com.etree.commons.core.dto.RequestDto;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.etree.commons.core.utils.CommonUtils;
import com.etree.commons.core.utils.jackson.json.ObjectMapperContext;
import com.etree.commons.core.utils.jackson.json.ObjectMapperPrototype;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class AbstractBase extends AbstractConfigParams implements BaseService {

	private Logger LOGGER = LoggerFactory.getLogger(AbstractBase.class);

	@Override
	public <T> T fetchData(RequestDto requestWrapper) {
		throw new EtreeCommonsException("", "Not implemented!");
	}
		
	protected Object convertJsonToPojo(RequestDto requestWrapper) {
		Object request = convertJsonToPojo(requestWrapper.getRequest(), requestWrapper.getRequestType(), false);
		return request;
	}
	
	protected Object convertJsonToPojo(RequestDto requestWrapper, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
		Object request = convertJsonToPojo(requestWrapper.getRequest(), requestWrapper.getRequestType(), false, dateFormatter);
		return request;
	}
	
	protected Object convertJsonToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso) {
		return convertJsonToPojo(value, type, isSerializeEmptyAlso, true);
	}

	protected Object convertJsonToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
		return convertJsonToPojo(value, type, isSerializeEmptyAlso, true, dateFormatter);
	}

	protected Object convertJsonToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso, boolean isForJaxb) {
		return convertJsonToPojo(value, type, isSerializeEmptyAlso, isForJaxb, false);
	}
	
	protected Object convertJsonToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso, boolean isForJaxb, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
		return convertJsonToPojo(value, type, isSerializeEmptyAlso, isForJaxb, false, dateFormatter);
	}
	
	protected Object convertJsonToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso, boolean isForJaxb, boolean isForCollection) {
		return convertJsonToPojo(value, type, isSerializeEmptyAlso, isForJaxb, isForCollection, 
				CommonsSupportConstants.DATE_FORMAT.YYYY_MM_DDTHH_MM_SS_SSS);
	}

	protected Object convertJsonToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso, boolean isForJaxb, 
			boolean isForCollection, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
		if (isForCollection) {
			return convertJsonToPojo(value, type, isSerializeEmptyAlso, isForJaxb, CommonsSupportConstants.COLLECTION_TYPE.LIST, dateFormatter);
		} else {
			return convertJsonToPojo(value, type, isSerializeEmptyAlso, isForJaxb, null, dateFormatter);
		}
	}
	protected Object convertJsonToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso, boolean isForJaxb, 
			COLLECTION_TYPE collectionType, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
		return convertToPojo(value, type, isSerializeEmptyAlso, isForJaxb, collectionType, dateFormatter);
	}	
	
	protected Object convertToPojo(Object value, Class<?> type, boolean isSerializeEmptyAlso, boolean isForJaxb, 
			COLLECTION_TYPE collectionType, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
		ObjectMapperContext objectMapperContext = new ObjectMapperContext(isSerializeEmptyAlso, isForJaxb, dateFormatter);
		ObjectMapper objectMapper = ObjectMapperPrototype.createObjectMapper(objectMapperContext);
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
	
	protected MessageDto createMessageDto(List<com.etree.commons.core.dto.Error> error, Object key, String msg, Boolean success) {
		if(error == null && msg == null && success == null){
			return null;
		}
		MessageDto messageDto = new MessageDto();
		messageDto.setSuccess(success);
		messageDto.setPrimaryKey(key);
		if (success) {
			messageDto.setMessage(msg);
		} else {
			Errors errors = new Errors();
			messageDto.setErrors(errors);
			if (error == null) {
				error = errors.getError();
			}
			com.etree.commons.core.dto.Error err = new com.etree.commons.core.dto.Error();
			error.add(err);
			err.setShortText(msg);
		}
		return messageDto;
	}
	
	protected String getErrorMessage(MessageDto messageDto) {
		if(messageDto == null){
			return null;
		}
		Errors errors = messageDto.getErrors();
		if (errors == null) {
			return null;
		}
		List<com.etree.commons.core.dto.Error> lstError = errors.getError();
		if (lstError == null || lstError.isEmpty()) {
			return null;
		}
		StringBuilder msg = null;
		for (com.etree.commons.core.dto.Error err : lstError) {
			if (msg == null) {
				msg = new StringBuilder(err.getValue());
			} else {
				msg.append(err.getValue());
			}
		}
		if (msg != null) {
			return msg.toString();
		}
		return null;
	}
	
	@Deprecated
	protected MessageDto createMessageDto(Throwable ex) {
		return CommonUtils.createMessageDto(ex);
	}
	
	@Deprecated
	protected MessageDto createMessageDto(Errors errors) {
		return CommonUtils.createMessageDto(errors);
	}
	
	@Deprecated
	protected Errors createErrors(Throwable ex) {
		return CommonUtils.createErrors(ex);
	}

	protected void throwException(Errors errors) {
		List<Error> lstError = errors.getError();
		StringBuilder msg = null;
		for (Error error : lstError) {
			if (msg == null) {
				msg = new StringBuilder(error.getCode()).append(" - ").append(error.getValue());
			} else {
				msg.append(", ").append(error.getCode()).append(" - ").append(error.getValue());
			}
		}
		if (msg != null) {
			throw new EtreeCommonsException("", msg.toString());
		}
		return;
	}
	
}

 