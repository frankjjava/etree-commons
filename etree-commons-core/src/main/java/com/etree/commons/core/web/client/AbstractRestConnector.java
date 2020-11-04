/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.core.web.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.etree.commons.core.AbstractBaseService;
import com.etree.commons.core.CommonsSupportConstants;
import com.etree.commons.core.dto.Errors;
import com.etree.commons.core.dto.MessageDto;
import com.etree.commons.core.dto.RequestWrapperDto;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.etree.commons.core.utils.CommonUtils;
import com.etree.commons.core.utils.ExceptionUtils;
import com.etree.commons.core.utils.jackson.json.ObjectMapperContext;
import com.etree.commons.core.utils.jackson.json.ObjectMapperPrototype;
import com.etree.commons.core.utils.jackson.json.ObjectMapperProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRestConnector extends AbstractBaseService implements RemoteConnector {
	
	private Logger LOGGER = LoggerFactory.getLogger(AbstractRestConnector.class);
	private Client client;
	private static final String KEY_CONNECT_TIMEOUT = "connectTimeOut";
	private static final String KEY_REQUEST_TIMEOUT = "requestTimeOut";
	private int connectTimeout = 60000;
	private int requestTimeout = 60000;
	private static final String KEY_DO_REMOTE_CALL = "doRemoteCall";
	private ObjectMapper objectMapper;
	
	@Override
	public <T> T process(RequestWrapperDto requestWrapper) {
		throw new EtreeCommonsException("", "Not implemented!");
	}	

	@Override
	public <T> T doRemoteCall(RequestWrapperDto requestWrapper) {
		throw new EtreeCommonsException("", "Not implemented!");		
	}
	
	protected <T> T doRemoteCall(RequestWrapperDto requestWrapper, String urlKey, HttpMethods httpMethod, boolean isForJaxb) {
		return doRemoteCall(requestWrapper, urlKey, httpMethod, isForJaxb, LOGGER);
	}
	
	protected <T> T doRemoteCall(RequestWrapperDto requestWrapper, String urlKey, HttpMethods httpMethod, boolean isForJaxb, Logger logger) {
		if (httpMethod == null) {
			throw new EtreeCommonsException("", "Unsupported HTTP method! 'actionType' cannot be null.");
		} else if (!HttpMethods.PUT.equals(httpMethod) && !HttpMethods.GET.equals(httpMethod) && !HttpMethods.POST.equals(httpMethod)) {
			throw new EtreeCommonsException("", "Unsupported HTTP method! " + httpMethod.name());
		}
		logger.info("Calling microservice : " + requestWrapper.getService());
		if (objectMapper == null) {
			ObjectMapperContext objectMapperContext = new ObjectMapperContext(false, true, CommonsSupportConstants.DATE_FORMAT.YYYY_MM_DDTHH_MM_SS_SSS);
			objectMapper = ObjectMapperPrototype.createObjectMapper(objectMapperContext);
		}
		T objResponse = null;
		String mediaType = requestWrapper.getContentType();
		Builder builder = createBuilderClient(urlKey);
		Response response = null;
		try {
			if (HttpMethods.PUT.equals(httpMethod)) {
				response = builder.put(Entity.entity(requestWrapper, mediaType));
			} else if (HttpMethods.GET.equals(httpMethod)) {
				response = builder.get();
			} else if (HttpMethods.POST.equals(httpMethod)) {
				response = builder.post(Entity.entity(requestWrapper, mediaType));
			}
			String responseAsString = response.readEntity(String.class);
			logger.trace("Response received: " + responseAsString);
			if (StringUtils.isEmpty(responseAsString)) {
				objResponse = (T) CommonUtils.createMessageDto(CommonUtils.createErrors("", "Error!. Received null / empty response!"));
			} else {
				ObjectMapper objectMapperCopy = objectMapper.copy();
				objectMapperCopy.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
				try {
			        objResponse = (T) objectMapperCopy.readValue(responseAsString, Errors.class);
				} catch (IOException e) {
					try {
				        objResponse = (T) objectMapperCopy.readValue(responseAsString, MessageDto.class);
					} catch (IOException ex) {
						objResponse = (T) convertJsonToPojo(responseAsString, requestWrapper.getResponseType(), false, isForJaxb, 
								requestWrapper.getCollectionType(), CommonsSupportConstants.DATE_FORMAT.YYYY_MM_DDTHH_MM_SS_SSS);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ExceptionUtils.getFullStackTrace(ex));
			return (T) CommonUtils.createMessageDto(ex);
		}
		logger.info("Completed microservice call : " + requestWrapper.getService());
		return objResponse;
	}

	@PostConstruct
	public void init() {
		if (configParams == null) {
			return;
		}
		doRemoteCall = getBoolean(KEY_DO_REMOTE_CALL);
		if (doRemoteCall == null) {
			doRemoteCall = true;
		}
		Object paramConnectTimeout = configParams.get(KEY_CONNECT_TIMEOUT);
		Object paramRequestTimeout = configParams.get(KEY_REQUEST_TIMEOUT);
		try {
			if (paramConnectTimeout != null) {
				connectTimeout = Integer.parseInt((String) paramConnectTimeout);
			}
			if (paramRequestTimeout != null) {
				requestTimeout = Integer.parseInt((String) paramRequestTimeout);
			}
		} catch (NumberFormatException ex) {
			LOGGER.warn(ex.getMessage());
		}
	}
	
	protected boolean isDoRemoteCall() {
		if (doRemoteCall == null) {
			init();
		}
		return doRemoteCall;
	}
	
	protected Builder createBuilderClient(String key, Map<String, Object> queryParams, boolean isSerializeEmptyAlso) {
		WebTarget webTarget = createWebTarget(key, isSerializeEmptyAlso, false);
		for (Entry<String, Object> entry : queryParams.entrySet()) {
			String paramKey = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Collection) {
				for (Object member : (Collection<?>) value) {
					webTarget = webTarget.queryParam(paramKey, member);
				}
			} else {
				webTarget = webTarget.queryParam(paramKey, value);
			}
		}
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		return builder;
	}

	protected Builder createBuilderClient(String key) {
		return createBuilderClient(key, false);
	}
	
	protected Builder createBuilderClient(String key, String mediaType) {
		return createBuilderClient(key, false, mediaType);
	}
	
	protected Builder createBuilderClient(RequestWrapperDto requestWrapper, String key) {
		return createBuilderClient(requestWrapper, key, false);
	}
	
	protected Builder createBuilderClient(String key, boolean isSerializeEmptyAlso) {
		return createBuilderClient(key, isSerializeEmptyAlso, MediaType.APPLICATION_JSON);
	}
	
	protected Builder createBuilderClient(String key, boolean isSerializeEmptyAlso, String mediaType) {
		WebTarget webTarget = createWebTarget(key, isSerializeEmptyAlso);
		Builder builder = webTarget.request(mediaType);
		return builder;
	}
	
	protected Builder createBuilderClient(URI uri, boolean isSerializeEmptyAlso) {
		WebTarget webTarget = createWebTarget(uri, isSerializeEmptyAlso, false);
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		return builder;
	}

	protected Builder createBuilderClient(RequestWrapperDto requestWrapper, URI uri, boolean isSerializeEmptyAlso) {
		Map<String, String> queryParams = convertQueryStringToMap(requestWrapper);
		WebTarget webTarget = createWebTarget(uri, queryParams, isSerializeEmptyAlso, false);
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		return builder;
	}
	
	protected Builder createBuilderClient(RequestWrapperDto requestWrapper, String key, boolean isSerializeEmptyAlso) {
		WebTarget webTarget = createWebTarget(key, isSerializeEmptyAlso);
		String resource = requestWrapper.getResource();
		if (resource != null && resource.contains("?")) {
			resource = resource.substring(resource.indexOf("?") + 1);
			String[] queryParams = resource.split("&");
			for (String queryParam : queryParams) {
				String[] keyValue = queryParam.split("=");
				webTarget = webTarget.queryParam(keyValue[0], keyValue[1]);
			}
		}
		Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
		return builder;
	}
	
	protected WebTarget createWebTarget(String key, boolean isSerializeEmptyAlso) { 
		return createWebTarget(key, isSerializeEmptyAlso, true);
	}

	protected WebTarget createWebTarget(String key, boolean isSerializeEmptyAlso, boolean isForJaxb) { 
		return createWebTarget(key, isSerializeEmptyAlso, isForJaxb, null);
	}
	
	protected WebTarget createWebTarget(URI uri, Map<String, String> queryParams, boolean isSerializeEmptyAlso, boolean isForJaxb) { 
		WebTarget webTarget = createWebTarget(uri, isSerializeEmptyAlso, isForJaxb, null);
		if (queryParams != null) {
			for (Entry<String, String> entry : queryParams.entrySet()) {
				webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
			}
		}
		return webTarget;
	}
	
	protected WebTarget createWebTarget(String key, boolean isSerializeEmptyAlso, boolean isForJaxb, CommonsSupportConstants.DATE_FORMAT dateFormat) { 
		if (!configParams.containsKey(key)) {
			throw new EtreeCommonsException("", "Cannot make remote call! URL is not set.");
		}
		String url = (String) configParams.get(key);
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			throw new EtreeCommonsException(e);
		}
		return createWebTarget(uri, isSerializeEmptyAlso, isForJaxb, dateFormat);
	}
	
	protected WebTarget createWebTarget(URI uri, boolean isSerializeEmptyAlso, boolean isForJaxb) { 
		return createWebTarget(uri, isSerializeEmptyAlso, isForJaxb, null);
	}	
	
	protected WebTarget createWebTarget(URI uri, boolean isSerializeEmptyAlso, boolean isForJaxb, CommonsSupportConstants.DATE_FORMAT dateFormat) {
		if (client == null) {
			client = ClientBuilder.newBuilder()
				.register(new ObjectMapperProvider(isSerializeEmptyAlso, isForJaxb, dateFormat))
				.register(JacksonFeature.class)
				.build();
		}
		final WebTarget target  = client.target(uri);
		return target;
	}

	protected void applyHttpHeaders(Builder builder, Map<String, String> httpHeaders) {
		if (httpHeaders == null || httpHeaders.isEmpty()) {
			return;
		}
		for (Entry<String, String> entry : httpHeaders.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}
	}
}
