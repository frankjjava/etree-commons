/**
* Copyright © 2020 Franklinton IT Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.core.utils.jackson.json;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.etree.commons.core.CommonsSupportConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
	
	final ObjectMapper objectMapper;

    public ObjectMapperProvider() {
    	this(false, true);
    }
    
    public ObjectMapperProvider(boolean isSerializeEmptyAlso, boolean isForJaxb) {
        objectMapper = createObjectMapper(isSerializeEmptyAlso, isForJaxb);
    }
    
    public ObjectMapperProvider(boolean isSerializeEmptyAlso, boolean isForJaxb, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
        objectMapper = createObjectMapper(isSerializeEmptyAlso, isForJaxb, dateFormatter);
    }
    
    @Override
    public ObjectMapper getContext(final Class<?> type) {
    	return objectMapper;
    }

    public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

    private ObjectMapper createObjectMapper(boolean isSerializeEmptyAlso, boolean isForJaxb) {
        return createObjectMapper(isSerializeEmptyAlso, isForJaxb, CommonsSupportConstants.DATE_FORMAT.YYYY_MM_DDTHH_MM_SS_SSS);
    }
    
    private ObjectMapper createObjectMapper(boolean isSerializeEmptyAlso, boolean isForJaxb, CommonsSupportConstants.DATE_FORMAT dateFormatter) {
    	ObjectMapperContext objectMapperContext = new ObjectMapperContext(isSerializeEmptyAlso, isForJaxb, dateFormatter);
		ObjectMapper objectMapper = ObjectMapperPrototype.createObjectMapper(objectMapperContext);
        return objectMapper;
    }
    
}