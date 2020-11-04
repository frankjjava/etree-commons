package com.etree.commons.core.utils.jackson.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.etree.commons.core.CommonsSupportConstants.DATE_FORMAT;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class ObjectMapperPrototype {

	private static Map<ObjectMapperContext, ObjectMapper> mapObjectMappers;
	
	public static ObjectMapper createObjectMapper(ObjectMapperContext objectMapperContext) {
		if (mapObjectMappers != null && mapObjectMappers.containsKey(objectMapperContext)) {
			return mapObjectMappers.get(objectMapperContext);
		}
    	ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.USE_ANNOTATIONS, true)
    		.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    	if (objectMapperContext.isForJaxb()) {
    		TypeFactory typeFactory = objectMapper.getTypeFactory();
    		AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector(typeFactory, false);
    		objectMapper.setAnnotationIntrospector(jaxbIntrospector);
    	}
        if (!objectMapperContext.isSerializeEmptyAlso()) {
        	objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        }
        DATE_FORMAT format = objectMapperContext.getDateFormatter();
        if (format != null) {
	        DateFormat dateFormat = new SimpleDateFormat(format.value());
	        objectMapper.setDateFormat(dateFormat);
        }
        if (mapObjectMappers == null) {
        	mapObjectMappers = new HashMap<>();
        }
        mapObjectMappers.put(objectMapperContext, objectMapper);
        return objectMapper;
	}
}
