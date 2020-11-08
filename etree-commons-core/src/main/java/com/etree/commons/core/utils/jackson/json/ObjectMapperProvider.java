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

    @Override
    public ObjectMapper getContext(final Class<?> type) {
    	return objectMapper;
    }

    public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

    @Deprecated
    private ObjectMapper createObjectMapper(boolean isSerializeEmptyAlso, boolean isForJaxb) {
    	ObjectMapperContext objectMapperContext = new ObjectMapperContext(isSerializeEmptyAlso, isForJaxb);
		ObjectMapper objectMapper = ObjectMapperPrototype.createObjectMapper(objectMapperContext);
        return objectMapper;
    }
    
}